export type BookingStatus = "PENDING" | "CONFIRMED" | "CANCELLED" | "REFUNDED";

export type BookingType = {
  id: number;
  showId: number;
  movieName: string;
  seatNumbers: number[];
  totalPrice: number;
  status: BookingStatus;
  createdAt: string;
};
