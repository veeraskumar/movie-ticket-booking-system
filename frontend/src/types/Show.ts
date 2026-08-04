export const SHOWSTATUS = ["UPCOMING", "RUNNING", "DONE", "CANCELLED"] as const;

export type ShowStatus = (typeof SHOWSTATUS)[number];

export type ShowType = {
  id: number;
  movieName: string;
  roomNumber: number;
  startTime: string;
  durationMinutes: number;
  totalSeats: number;
  economySeatTo: number;
  economySeatPrice: number;
  premiumSeatTo: number;
  premiumSeatPrice: number;
  reclinerSeatTo: number;
  reclinerSeatPrice: number;
  status: ShowStatus;
  theaterId: number;
  theaterName: string;
};
