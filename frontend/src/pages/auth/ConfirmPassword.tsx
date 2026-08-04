import { confirmPassword } from "@/api/auth";
import { Button } from "@/components/ui/button";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/toast";
import { ConfirmPasswordSchema, type ConfirmPassword } from "@/schemas/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

export default function ConfirmPassword() {
  const navigate = useNavigate();
  const form = useForm<ConfirmPassword>({
    resolver: zodResolver(ConfirmPasswordSchema),
    defaultValues: {
      code: undefined,
      confirmPassword: "",
      newPassword: "",
    },
  });

  const submit = async (data: ConfirmPassword) => {
    try {
      const email = sessionStorage.getItem("resetEmail");
      if (!email) {
        navigate("/forgot-password");
        return;
      }
      await confirmPassword(data, email);
      sessionStorage.removeItem("resetEmail");
      form.reset();
      navigate("/login");
    } catch (error) {
      if (axios.isAxiosError(error)) {
        toast.add({
          type: "error",
          description: error.response?.data.message ?? "Something went wrong",
          priority: "high",
        });
      }
    }
  };

  return (
    <div className="w-full h-dvh flex items-center justify-center">
      <form
        onSubmit={form.handleSubmit(submit)}
        className="w-80 space-y-6 outline p-6"
      >
        <FieldGroup>
          <div className="flex flex-col items-center gap-1 text-center">
            <h1 className="text-2xl font-bold">Confirm password</h1>
            <p className="text-sm text-balance text-muted-foreground">
              Enter your received code below a forgot password to your account
            </p>
          </div>
          <Field>
            <FieldLabel htmlFor="code">Code</FieldLabel>
            <Input
              id="code"
              {...form.register("code", { valueAsNumber: true })}
            />
            <FieldError>{form.formState.errors.code?.message}</FieldError>
          </Field>
          <Field>
            <FieldLabel htmlFor="new_password">New Password</FieldLabel>
            <Input
              type="password"
              id="new_password"
              {...form.register("newPassword")}
            />
            <FieldError>
              {form.formState.errors.newPassword?.message}
            </FieldError>
          </Field>
          <Field>
            <FieldLabel htmlFor="confirm_password">Confirm Password</FieldLabel>
            <Input
              type="password"
              id="confirm_password"
              {...form.register("confirmPassword")}
            />
            <FieldError>
              {form.formState.errors.confirmPassword?.message}
            </FieldError>
          </Field>
          <Field>
            <Button type="submit" disabled={form.formState.isSubmitting}>
              {form.formState.isSubmitting
                ? "Confirming..."
                : "Confirm Password"}
            </Button>
          </Field>
        </FieldGroup>
      </form>
    </div>
  );
}
