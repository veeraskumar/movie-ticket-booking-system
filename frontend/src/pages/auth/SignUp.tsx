import { create } from "@/api/auth";
import { Button } from "@/components/ui/button";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/toast";
import { CreateUserSchema, type CreateUser } from "@/schemas/auth";
import { zodResolver } from "@hookform/resolvers/zod";
import axios from "axios";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";

export default function SignUp() {
  const navigate = useNavigate();
  const form = useForm<CreateUser>({
    resolver: zodResolver(CreateUserSchema),
    defaultValues: {
      email: "",
      name: "",
      password: "",
    },
  });

  const submit = async (data: CreateUser) => {
    try {
      await create(data);
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
        className="w-80 space-y-6 outline p-6 rounded-sm"
        onSubmit={form.handleSubmit(submit)}
      >
        <FieldGroup>
          <div className="flex flex-col items-center gap-1 text-center">
            <h1 className="text-2xl font-bold">Create Account</h1>
            <p className="text-sm text-balance text-muted-foreground">
              Enter your email, name & password below to form to create account
            </p>
          </div>
          <Field>
            <FieldLabel htmlFor="name">Name</FieldLabel>
            <Input id="name" {...form.register("name")} />
            <FieldError>{form.formState.errors.name?.message}</FieldError>
          </Field>
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
              {form.formState.isSubmitting ? "creating..." : "create"}
            </Button>
          </Field>
        </FieldGroup>
      </form>
    </div>
  );
}
