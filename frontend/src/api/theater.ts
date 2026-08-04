import type { Theater } from "@/schemas/theater";
import { api } from "./api";
import type { City } from "@/types/Theater";

export const createTheater = (data: Theater) => api.post("/theaters", data);

export const allTheater = () => api.get("/theaters");

export const getTheaters = (city: City) => api.get(`/theaters/city/${city}`);

export const getTheaterByowner = async () => await api.get("/theaters/owner");

export const updateTheater = (id: number, data: Theater) =>
  api.put(`/theaters/${id}`, { ...data, id });

export const getTheaterDetail = (id: number) => api.get(`/theaters/${id}`);

export const shutDown = (id: number) => api.put(`/theaters/${id}/shutdown`);
