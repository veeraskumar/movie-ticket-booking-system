import { allTicketByUserId } from "@/api/booking";
import Navbar from "@/components/Navbar";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import type { BookingType } from "@/types/Booking";
import { useEffect, useState } from "react";
import TicketDetail from "@/components/TicketDetail";
import Loading from "@/components/Loading";

export default function Booking() {
  const [tickets, setTickets] = useState<BookingType[] | null>(null);
  const [selectedBookingId, setSelectedBookingId] = useState<number | null>(
    null,
  );

  const loadBooking = async () => {
    const res = await allTicketByUserId();
    setTickets(res.data);
  };

  useEffect(() => {
    const lo = () => loadBooking();
    lo();
  }, []);

  if (tickets === null) return <Loading />;

  if (tickets.length === 0) {
    return (
      <>
        <Navbar />
        <div className="flex h-80 items-center justify-center">
          <p className="text-muted-foreground">
            You haven't booked any tickets yet.
          </p>
        </div>
      </>
    );
  }

  return (
    <div className="w-full">
      <Navbar />
      <div className="w-full p-5 grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4 gap-6">
        {tickets.map((ticket) => (
          <Card key={ticket.id}>
            <CardHeader className="space-y-2">
              <CardTitle className="text-lg text-center font-semibold">
                {ticket.movieName}
              </CardTitle>
            </CardHeader>
            <CardContent className="space-y-2">
              <p className="text-center">
                {" "}
                Seats : {ticket.seatNumbers.join(", ")}
              </p>
              <p
                className={` font-semibold ${ticket.status === "CONFIRMED" ? "text-green-500" : "text-red-600"}`}
              >
                {ticket.status}
              </p>
            </CardContent>
            <CardFooter>
              <Button
                type="button"
                variant="default"
                className={`w-full text-center`}
                onClick={() => setSelectedBookingId(ticket.id)}
              >
                View Ticket
              </Button>
            </CardFooter>
          </Card>
        ))}
      </div>
      {selectedBookingId && (
        <TicketDetail
          bookingId={selectedBookingId}
          open={true}
          onRefresh={loadBooking}
          onOpenChange={(open) => {
            if (!open) setSelectedBookingId(null);
          }}
        />
      )}
    </div>
  );
}
