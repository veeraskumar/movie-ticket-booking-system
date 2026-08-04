import type {
  ConfirmPassword,
  CreateUser,
  ForgotPassword,
  LoginRequest,
} from "@/schemas/auth";
import { api } from "./api";

export const login = (data: LoginRequest) => api.post("/auth/login", data);

export const create = (data: CreateUser) => api.post("/auth/register", data);

export const forgotPassword = (data: ForgotPassword) =>
  api.post("/auth/forgot-password", data);

export const confirmPassword = (data: ConfirmPassword, email: string) =>
  api.post("/auth/confirm-password", { ...data, email });

export const me = () => api.get("/users/me");
