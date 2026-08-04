import z from "zod";

export const UpdateUserSchema = z.object({
  name: z.string().min(3),
  email: z.email("Please enter a valid email"),
});
export type UpdateUser = z.infer<typeof UpdateUserSchema>;

export const UpdatePasswordSchema = z
  .object({
    oldPassword: z.string().min(8, "fill the old Password"),
    newPassword: z.string().min(8, "Password must be at least 8 characters"),
    confirmPassword: z
      .string()
      .min(8, "Password must be at Match new Password"),
  })
  .refine((data) => data.newPassword === data.confirmPassword, {
    message: "Passwords do not match",
    path: ["confirmPassword"],
  });
export type UpdatePassword = z.infer<typeof UpdatePasswordSchema>;
