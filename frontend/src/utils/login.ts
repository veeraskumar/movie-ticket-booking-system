import { jwtDecode } from "jwt-decode";

export type Role = "ROLE_OWNER" | "ROLE_MANAGER" | "ROLE_USER" | "ROLE_ADMIN";

type PayLoad = {
  sub: string;
  iat: number;
  role: Role[];
  exp: number;
};

export function isLogin() {
  const token = localStorage.getItem("token");
  if (!token) return null;
  const payload: PayLoad = jwtDecode(token);
  if (payload.exp * 1000 <= Date.now()) {
    localStorage.removeItem("token");
    return;
  }
  return payload;
}
