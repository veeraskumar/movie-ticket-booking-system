import { api } from "./api";

export const updateUser = (id: number, data: { email: string; name: string }) =>
  api.put(`/users/${id}`, data);
