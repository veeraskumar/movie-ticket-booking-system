import { updateShow } from "@/api/show";
import { Button } from "@/components/ui/button";
import { Dialog, DialogContent } from "@/components/ui/dialog";
import {
  Field,
  FieldError,
  FieldGroup,
  FieldLabel,
} from "@/components/ui/field";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/toast";
import { ShowSchema, type Show } from "@/schemas/show";
import { type ShowType } from "@/types/Show";
import type { TheaterType } from "@/types/Theater";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Spinner } from "@/components/ui/spinner";

export default function EditShow({
  show,
  theater,
  theaterId,
  open,
  onChangeOpen,
  onRefresh,
}: {
  show: ShowType;
  theater: TheaterType;
  theaterId: number;
  open: boolean;
  onChangeOpen: (open: boolean) => void;
  onRefresh: (id: number) => Promise<void>;
}) {
  const form = useForm<Show>({
    resolver: zodResolver(ShowSchema),
    defaultValues: {
      movieName: show.movieName,
      roomNumber: show.roomNumber,
      startTime: show.startTime,
      durationMinutes: show.durationMinutes,
      totalSeats: show.totalSeats,
      economySeatTo: show.economySeatTo,
      economySeatPrice: show.economySeatPrice,
      premiumSeatTo: show.premiumSeatTo,
      premiumSeatPrice: show.premiumSeatPrice,
      reclinerSeatTo: show.reclinerSeatTo,
      reclinerSeatPrice: show.reclinerSeatPrice,
    },
  });

  const submit = async (data: Show) => {
    try {
      if (data.roomNumber > theater.noOfRoom) {
        form.setError("roomNumber", {
          type: "manual",
          message: `Only ${theater.noOfRoom} screens are available.`,
        });
        return;
      }
      await updateShow(data, theaterId, show.id);

      toast.add({
        type: "success",
        description: "Show created successfully.",
      });

      await onRefresh(theaterId);

      form.reset();
      onChangeOpen(false);
      await onRefresh(theaterId);
    } catch {
      toast.add({
        type: "error",
        description: "Something went wrong try again !",
      });
    }
  };

  return (
    <Dialog open={open} onOpenChange={onChangeOpen}>
      <DialogContent>
        <form
          onSubmit={form.handleSubmit(submit)}
          className="no-scrollbar max-h-[60vh] overflow-y-auto"
        >
          <FieldGroup>
            <Field>
              <FieldLabel htmlFor="movieName">Movie Name</FieldLabel>
              <Input id="movieName" {...form.register("movieName")} />
              <FieldError>
                {form.formState.errors.movieName?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="roomNumber">Screen Number</FieldLabel>
              <Input
                id="roomNumber"
                type="number"
                {...form.register("roomNumber", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.roomNumber?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="startTime">Date & Time</FieldLabel>
              <Input
                id="startTime"
                type="datetime-local"
                {...form.register("startTime")}
              />
              <FieldError>
                {form.formState.errors.startTime?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="durationMinutes">Duration</FieldLabel>
              <Input
                id="durationMinutes"
                type="number"
                {...form.register("durationMinutes", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.durationMinutes?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="totalSeats">Total Seat</FieldLabel>
              <Input
                id="totalSeats"
                type="number"
                {...form.register("totalSeats", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.totalSeats?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="economySeatTo">Economy Seat To</FieldLabel>
              <Input
                id="economySeatTo"
                type="number"
                {...form.register("economySeatTo", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.economySeatTo?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="economySeatPrice">
                Economy Seat Price
              </FieldLabel>
              <Input
                id="economySeatPrice"
                type="number"
                {...form.register("economySeatPrice", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.economySeatPrice?.message}
              </FieldError>
              <Field>
                <FieldLabel htmlFor="premiumSeatTo">Premium Seat To</FieldLabel>
                <Input
                  id="premiumSeatTo"
                  type="number"
                  {...form.register("premiumSeatTo", { valueAsNumber: true })}
                />
                <FieldError>
                  {form.formState.errors.premiumSeatTo?.message}
                </FieldError>
              </Field>
            </Field>
            <Field>
              <FieldLabel htmlFor="premiumSeatPrice">
                Premium Seat Price
              </FieldLabel>
              <Input
                id="premiumSeatPrice"
                type="number"
                {...form.register("premiumSeatPrice", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.premiumSeatPrice?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="reclinerSeatTo">Recliner Seat To</FieldLabel>
              <Input
                id="reclinerSeatTo"
                type="number"
                {...form.register("reclinerSeatTo", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.reclinerSeatTo?.message}
              </FieldError>
            </Field>
            <Field>
              <FieldLabel htmlFor="reclinerSeatPrice">
                Recliner Seat Price
              </FieldLabel>
              <Input
                id="reclinerSeatPrice"
                type="number"
                {...form.register("reclinerSeatPrice", { valueAsNumber: true })}
              />
              <FieldError>
                {form.formState.errors.reclinerSeatPrice?.message}
              </FieldError>
            </Field>
            <Field>
              <Button
                type="submit"
                variant="default"
                disabled={form.formState.isSubmitting}
              >
                {form.formState.isSubmitting ? (
                  <>
                    {" "}
                    <Spinner data-icon="inline-start" />
                    Saving
                  </>
                ) : (
                  "Save Show"
                )}
              </Button>
            </Field>
          </FieldGroup>
        </form>
      </DialogContent>
    </Dialog>
  );
}
