import { z } from "zod";

export const LoginRequestSchema = z.object({
  email: z.email("Please enter a valid email"),
  password: z.string().min(8, "Password must be at least 8 characters"),
});
export type LoginRequest = z.infer<typeof LoginRequestSchema>;

export const CreateUserSchema = z.object({
  name: z.string().min(3),
  email: z.email("Please enter a valid email"),
  password: z.string().min(8, "Password must be at least 8 characters"),
});
export type CreateUser = z.infer<typeof CreateUserSchema>;

export const ForgotPasswordSchema = z.object({
  email: z.email("Please enter a valid email"),
});
export type ForgotPassword = z.infer<typeof ForgotPasswordSchema>;

export const ConfirmPasswordSchema = z
  .object({
    code: z.number().min(1_00_000).max(9_99_999),
    newPassword: z.string().min(8, "Password must be at least 8 characters"),
    confirmPassword: z
      .string()
      .min(8, "Password must be at Match new Password"),
  })
  .refine((data) => data.newPassword === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });
export type ConfirmPassword = z.infer<typeof ConfirmPasswordSchema>;
