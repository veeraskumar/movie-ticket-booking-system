import { UpdateUserSchema, type UpdateUser } from "@/schemas/user";
import type { UserType } from "@/types/User";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { Spinner } from "@/components/ui/spinner";
import { updateUser } from "@/api/user";
import { toast } from "@/components/ui/toast";

export default function UpdateUser({
  user,
  open,
  onRefresh,
  onOpenChange,
}: {
  user: UserType;
  open: boolean;
  onRefresh: () => Promise<void>;
  onOpenChange: (open: boolean) => void;
}) {
  const form = useForm<UpdateUser>({
    resolver: zodResolver(UpdateUserSchema),
    defaultValues: { email: user.email, name: user.name },
  });

  const submit = async (data: { email: string; name: string }) => {
    try {
      await updateUser(user.id, data);
      toast.add({ type: "sucess", description: "sucessfully updated" });
      await onRefresh();
      form.reset({ email: "", name: "" });
      onOpenChange(false);
    } catch {
      toast.add({
        type: "error",
        description: "something went wrong try again!",
      });
    }
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Update Profile</DialogTitle>
        </DialogHeader>
        <form
          onSubmit={form.handleSubmit(submit)}
          className="space-y-2 no-scrollbar max-h-[60vh] overflow-y-auto"
        >
          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="name">Name</FieldLabel>
              <Input id="name" {...form.register("name")} />
              <FieldError>{form.formState.errors.name?.message}</FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="email">Email</FieldLabel>
              <Input id="email" type="email" {...form.register("email")} />
              <FieldError>{form.formState.errors.email?.message}</FieldError>
            </Field>
            <Field>
              <Button type="submit" disabled={form.formState.isSubmitting}>
                {form.formState.isSubmitting ? (
                  <>
                    <Spinner data-icon="inline-start" />
                    Updating
                  </>
                ) : (
                  "Update"
                )}
              </Button>
            </Field>
          </FieldGroup>
        </form>
      </DialogContent>
    </Dialog>
  );
}
