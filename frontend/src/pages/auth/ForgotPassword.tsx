import { forgotPassword } from "@/api/auth";
import { Button } from "@/components/ui/button";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/toast";
import { ForgotPasswordSchema, type ForgotPassword } from "@/schemas/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

export default function ForgotPassword() {
  const navigate = useNavigate();

  const form = useForm<ForgotPassword>({
    resolver: zodResolver(ForgotPasswordSchema),
    defaultValues: { email: "" },
  });

  const submit = async (data: ForgotPassword) => {
    try {
      await forgotPassword(data);
      sessionStorage.setItem("resetEmail", data.email);
      form.reset();
      navigate("/confirm-password");
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
        className="w-80 space-y-6 outline p-6 rounded-sm"
      >
        <FieldGroup>
          <div className="flex flex-col items-center gap-1 text-center">
            <h1 className="text-2xl font-bold">Forgot password</h1>
            <p className="text-sm text-balance text-muted-foreground">
              Enter your email below to forgot password to your account
            </p>
          </div>
          <Field>
            <FieldLabel htmlFor="email">Email</FieldLabel>
            <Input
              id="email"
              className={
                form.formState.errors.email?.message ? "border-red-500" : ""
              }
              {...form.register("email")}
            />
            <FieldError>{form.formState.errors.email?.message}</FieldError>
          </Field>
          <Field>
            <Button
              type="submit"
              className=""
              disabled={form.formState.isSubmitting}
            >
              {form.formState.isSubmitting
                ? "Sending coding"
                : "Forgot Password"}
            </Button>
          </Field>
        </FieldGroup>
      </form>
    </div>
  );
}
