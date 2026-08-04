import { cancelTicket, getTicketById } from "@/api/booking";
import { show } from "@/api/show";
import Loading from "@/components/Loading";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Dialog, DialogContent } from "@/components/ui/dialog";
import { toast } from "@/components/ui/toast";
import type { BookingType } from "@/types/Booking";
import type { ShowType } from "@/types/Show";
import { useEffect, useState } from "react";
import { Spinner } from "@/components/ui/spinner";

export default function TicketDetail({
  bookingId,
  open,
  onOpenChange,
  onRefresh,
}: {
  bookingId: number;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onRefresh: () => Promise<void>;
}) {
  const [ticket, setTickets] = useState<BookingType | null>(null);
  const [shows, setShows] = useState<ShowType | null>(null);
  const [selected, setSelectSeats] = useState<number[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const load = async () => {
      const res = await getTicketById(Number(bookingId));
      setTickets(res.data);
      const showRes = await show(res.data.showId);
      setShows(showRes.data);
    };
    load();
  }, [bookingId]);

  const toggleSeat = (seat: number) => {
    setSelectSeats((prev) =>
      prev.includes(seat) ? prev.filter((s) => s !== seat) : [...prev, seat],
    );
  };

  const submit = async () => {
    setLoading(true);
    await cancelTicket(ticket.id, selected);
    toast.add({
      type: "info",
      description: `Seat Canceled ${selected.join(", ")}`,
      priority: "high",
    });
    await onRefresh();
    onOpenChange(false);
    setLoading(false);
  };

  if (!ticket || !shows) return <Loading />;

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="max-w-lg">
        <Card className="space-y-2">
          <CardHeader>
            <CardTitle>Movies - {ticket.movieName}</CardTitle>
            <CardDescription className="space-y-2">
              <p>Theater - {shows.theaterName}</p>
              <p>Room - {shows.roomNumber}</p>
              <p>Show Timing - {new Date(shows.startTime).toLocaleString()}</p>
            </CardDescription>
          </CardHeader>
          <CardContent className="space-y-6">
            <p>Seats</p>
            <ol className="w-full space-x-2">
              {ticket.seatNumbers.map((seat) => (
                <Button
                  key={seat}
                  type="button"
                  variant={selected.includes(seat) ? "default" : "outline"}
                  onClick={() => toggleSeat(seat)}
                >
                  {seat}
                </Button>
              ))}
            </ol>
            <p>Total ₹{ticket.totalPrice}</p>
            <p>
              Status -{" "}
              <span
                className={` font-semibold ${ticket.status === "CONFIRMED" ? "text-green-500" : "text-red-600"}`}
              >
                {ticket.status}
              </span>
            </p>
          </CardContent>
          <CardFooter>
            <Button
              onClick={submit}
              variant="destructive"
              className={`w-full text-center`}
              disabled={
                selected.length === 0 ||
                ticket.status === "CANCELLED" ||
                loading
              }
            >
              {loading ? (
                <>
                  {" "}
                  <Spinner data-icon="inline-start" />
                  Cancelling
                </>
              ) : (
                "Cancel Selected Seats"
              )}
            </Button>
          </CardFooter>
        </Card>
      </DialogContent>
    </Dialog>
  );
}
