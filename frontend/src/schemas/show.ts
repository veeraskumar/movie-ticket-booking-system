import z from "zod";

export const ShowSchema = z
  .object({
    movieName: z.string().min(1),
    roomNumber: z.number().min(1),
    startTime: z.string().min(1, "Please select a start time"),
    durationMinutes: z.number().int().positive("Fill the Duration of show"),
    totalSeats: z.number().min(1, "please fill theater total seats"),
    economySeatTo: z.number().min(1, "Enter how seat for Economy"),
    economySeatPrice: z.number().min(1, "Enter Price for Economy seat"),
    premiumSeatTo: z.number().min(1, "Enter how seat for premium"),
    premiumSeatPrice: z.number().min(1, "Enter prices seat for premium"),
    reclinerSeatTo: z.number().min(1, "Enter how seat for recliner"),
    reclinerSeatPrice: z.number().min(1, "Enter prices seat for recliner"),
  })
  .refine((data) => data.totalSeats === data.reclinerSeatTo, {
    message: "Recliner seats must end at the total number of seats.",
    path: ["reclinerSeatTo"],
  })
  .refine((data) => data.economySeatTo < data.premiumSeatTo, {
    message: "Economy seats must end before Premium seats.",
    path: ["economySeatTo"],
  })
  .refine((data) => data.premiumSeatTo < data.reclinerSeatTo, {
    message: "Premium seats must end before Recliner seats.",
    path: ["premiumSeatTo"],
  });
export type Show = z.infer<typeof ShowSchema>;
