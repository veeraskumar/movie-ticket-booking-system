import { useEffect, useState } from "react";
import { Button } from "./ui/button";
import type { ShowType } from "@/types/Show";
import { createTicket, getConfirmedSeats } from "@/api/booking";
import { useNavigate } from "react-router-dom";
import { isLogin } from "@/utils/login";
import { toast } from "./ui/toast";

export default function SeatGrid({ show }: { show: ShowType }) {
  const navigate = useNavigate();

  const seats = Array.from(
    { length: show.totalSeats },
    (_, index) => index + 1,
  );

  const [selectedSeats, setSelectSeats] = useState<number[]>([]);
  const [bookedSeats, setBookedSeats] = useState<number[]>([]);

  useEffect(() => {
    const loadConfirmedBooking = async () => {
      const res = await getConfirmedSeats(show.id);
      setBookedSeats(res.data);
    };
    loadConfirmedBooking();
  }, [show.id]);

  const toggleSeat = (seat: number) => {
    setSelectSeats((prev) =>
      prev.includes(seat) ? prev.filter((s) => s !== seat) : [...prev, seat],
    );
  };

  const getSeatType = (seat: number) => {
    if (seat <= show.economySeatTo) return "economy";
    if (seat <= show.premiumSeatTo) return "premium";
    return "recliner";
  };

  const getSeatClass = (seat: number) => {
    if (selectedSeats.includes(seat))
      return "bg-blue-500 text-white dark:bg-blue-700";

    if (bookedSeats.includes(seat)) return "bg-red-500 cursor-not-allowed";

    switch (getSeatType(seat)) {
      case "economy":
        return "bg-green-100 hover:bg-green-200 dark:bg-green-500 dark:hover:bg-green-700 ";
      case "premium":
        return "bg-yellow-100 hover:bg-yellow-200 dark:bg-yellow-500 dark:hover:bg-yellow-700";
      case "recliner":
        return "bg-purple-100 hover:bg-purple-200 dark:bg-purple-500 dark:hover:bg-purple-700";
    }
  };

  const calculate = selectedSeats.reduce((total, seat) => {
    if (seat <= show.economySeatTo) return total + show.economySeatPrice;
    if (seat <= show.premiumSeatTo) return total + show.premiumSeatPrice;
    return total + show.reclinerSeatPrice;
  }, 0);

  const confirmTicket = async () => {
    const user = isLogin();
    if (!user) {
      navigate("/login", { state: { redirectTo: `/shows/${show.id}` } });
      return;
    }
    try {
      await createTicket(show.id, selectedSeats);
      toast.add({
        type: "success",
        description: `Successfully booked "${show.movieName}" for seats ${selectedSeats.join(", ")}.`,
        priority: "high",
      });
      navigate("/booking");
    } catch {
      toast.add({
        type: "error",
        description: `Failed to book seats ${selectedSeats.join(", ")} for "${show.movieName}". Please try again.`,
        priority: "high",
      });
    }
  };

  return (
    <div className="lg:w-full space-y-6 p-5 md:w-3xl w-2xl">
      <div className="my-8">
        <div className="mx-auto w-3/4 rounded bg-gray-300 p-2 text-center font-bold shadow">
          SCREEN
        </div>
      </div>
      <div className="grid grid-cols-15 gap-2">
        {seats.map((seat) => (
          <Button
            key={seat}
            type="button"
            variant="outline"
            className={getSeatClass(seat)}
            onClick={() => toggleSeat(seat)}
            disabled={bookedSeats.includes(seat)}
          >
            {seat}
          </Button>
        ))}
      </div>
      <div
        className={`fixed bottom-0 left-0 w-full p-4 bg-background border-t flex items-center justify-around ${
          selectedSeats.length ? "block" : "hidden"
        }`}
      >
        <p>Selected: {selectedSeats.join(", ")}</p>
        <p>Total: ₹{calculate}</p>
        <Button onClick={confirmTicket}>Book Now</Button>
      </div>
    </div>
  );
}
