import { api } from "./api";

export const createTicket = async (showId: number, seats: number[]) =>
  await api.post("/bookings", { showId, seats });

export const allTicket = async () => await api.get("/bookings");

export const getConfirmedSeats = async (showId: number) =>
  await api.get(`/shows/${showId}/seats`);

export const allTicketByUserId = async () => await api.get(`/bookings/user`);

export const getTicketById = async (bookingId: number) =>
  await api.get(`/bookings/${bookingId}`);

export const cancelTicket = async (id: number, seats: number[]) =>
  await api.put(`/bookings/${id}/cancel`, seats);
