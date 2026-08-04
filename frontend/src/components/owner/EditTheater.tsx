import { updateTheater } from "@/api/theater";
import { Button } from "@/components/ui/button";
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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Spinner } from "@/components/ui/spinner";
import { TheaterSchema, type Theater } from "@/schemas/theater";
import { CITIES, THEATER_STATUS, type TheaterType } from "@/types/Theater";
import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";

export default function EditTheater({
  theater,
  open,
  onOpenChange,
  onRefresh,
}: {
  theater: TheaterType;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onRefresh: () => Promise<void>;
}) {
  const form = useForm<Theater>({
    resolver: zodResolver(TheaterSchema),
    defaultValues: {
      name: theater.name,
      noOfRoom: theater.noOfRoom,
      address: theater.address,
      city: theater.city,
      googleMapUrl: theater.googleMapUrl,
      status: theater.status,
    },
  });

  const submit = async (data: Theater) => {
    await updateTheater(theater.id, data);
    form.reset();
    await onRefresh();
    onOpenChange(false);
  };

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Edit Theater</DialogTitle>
        </DialogHeader>
        <form
          action=""
          onSubmit={form.handleSubmit(submit)}
          className="space-y-2 no-scrollbar max-h-[60vh] overflow-y-auto"
        >
          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="name">Theater Name</FieldLabel>
              <Input id="name" {...form.register("name")} />
              <FieldError>{form.formState.errors.name?.message}</FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="noOfRoom">No Of Room</FieldLabel>
              <Input
                type="number"
                id="noOfRoom"
                {...form.register("noOfRoom", { valueAsNumber: true })}
              />
              <FieldError>{form.formState.errors.noOfRoom?.message}</FieldError>
            </Field>{" "}
            <Field>
              <FieldLabel htmlFor="address">Address</FieldLabel>
              <Input id="address" {...form.register("address")} />
              <FieldError>{form.formState.errors.address?.message}</FieldError>
            </Field>{" "}
            <Field>
              <FieldLabel htmlFor="city">City</FieldLabel>
              <Controller
                control={form.control}
                name="city"
                render={({ field }) => (
                  <Select value={field.value} onValueChange={field.onChange}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select city" />
                    </SelectTrigger>

                    <SelectContent>
                      {CITIES.map((city) => (
                        <SelectItem key={city} value={city}>
                          {city}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                )}
              />
              <FieldError>{form.formState.errors.city?.message}</FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="googleMapUrl">Google Map Url</FieldLabel>
              <Input id="googleMapUrl" {...form.register("googleMapUrl")} />
              <FieldError>
                {form.formState.errors.googleMapUrl?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="status">Status</FieldLabel>
              <Controller
                name="status"
                control={form.control}
                render={({ field }) => (
                  <Select value={field.value} onValueChange={field.onChange}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select Status" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem
                        key={THEATER_STATUS[0]}
                        value={THEATER_STATUS[0]}
                      >
                        {THEATER_STATUS[0]}
                      </SelectItem>{" "}
                      <SelectItem
                        key={THEATER_STATUS[1]}
                        value={THEATER_STATUS[1]}
                      >
                        {THEATER_STATUS[1]}
                      </SelectItem>
                    </SelectContent>
                  </Select>
                )}
              />
              <FieldError>{form.formState.errors.status?.message}</FieldError>
            </Field>
          </FieldGroup>
          <FieldGroup>
            <Field>
              <Button type="submit" disabled={form.formState.isSubmitting}>
                {form.formState.isSubmitting ? (
                  <>
                    {" "}
                    <Spinner data-icon="inline-start" />
                    Saving
                  </>
                ) : (
                  "Save"
                )}
              </Button>
            </Field>
          </FieldGroup>
        </form>
      </DialogContent>
    </Dialog>
  );
}
