import type { Show } from "@/schemas/show";
import { api } from "./api";

export const createShow = (data: Show, theaterId: number) =>
  api.post("/shows", { ...data, theaterId });

export const allShow = () => api.get("/shows");

export const show = (id: number) => api.get(`/shows/${id}`);

export const updateShow = (data: Show, theaterId: number, showId: number) =>
  api.put(`/shows/${showId}`, { ...data, theaterId });

export const cancelShow = (showId: number) => api.delete(`/shows/${showId}`);

export const theaterShows = (theaterId: number) =>
  api.get(`/shows/theater/${theaterId}`);
