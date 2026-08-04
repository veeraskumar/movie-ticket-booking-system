import { login } from "@/api/auth";
import { Button } from "@/components/ui/button";
import {
  Field,
  FieldGroup,
  FieldLabel,
  FieldError,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/toast";
import { LoginRequestSchema, type LoginRequest } from "@/schemas/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { useForm } from "react-hook-form";
import { Link, useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const form = useForm<LoginRequest>({
    resolver: zodResolver(LoginRequestSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

  const submit = async (data: LoginRequest) => {
    try {
      const response = await login(data);
      localStorage.setItem("token", response.data.token);
      navigate("/");
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
        className="w-80 space-y-6 outline p-6 rounded-sm"
        onSubmit={form.handleSubmit(submit)}
      >
        <FieldGroup>
          <div className="flex flex-col items-center gap-1 text-center">
            <h1 className="text-2xl font-bold">Login to your account</h1>
            <p className="text-sm text-balance text-muted-foreground">
              Enter your email below to login to your account
            </p>
          </div>
          <Field>
            <FieldLabel htmlFor="email">Email</FieldLabel>
            <Input id="email" {...form.register("email")} />
            <FieldError>{form.formState.errors.email?.message}</FieldError>
          </Field>
          <Field>
            <FieldLabel htmlFor="password">Password</FieldLabel>
            <Input
              id="password"
              type="password"
              {...form.register("password")}
            />
            <FieldError>{form.formState.errors.password?.message}</FieldError>
          </Field>
          <Field>
            <Button type="submit" disabled={form.formState.isSubmitting}>
              {form.formState.isSubmitting ? "Logging in..." : "Login"}
            </Button>
          </Field>
          <Field>
            <div className="flex items-center justify-between">
              <Link
                to="/sign-up"
                className="text-sm underline-offset-4 hover:underline"
              >
                SignUp
              </Link>
              <Link
                to="/forgot-password"
                className="text-sm underline-offset-4 hover:underline"
              >
                Forgot your password?
              </Link>
            </div>
          </Field>
        </FieldGroup>
      </form>
    </div>
  );
}
