export type Role = "ADMIN" | "MANAGER" | "USER" | "OWNER";

export type UserStatus = "ACTIVE" | "DEACTIVE" | "DELETED";

export type UserType = {
  id: number;
  name: string;
  email: string;
  role: Role;
  status: UserStatus;
  createdAt: string;
};
