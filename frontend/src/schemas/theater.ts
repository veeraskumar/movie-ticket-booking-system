import { CITIES, THEATER_STATUS } from "@/types/Theater";
import z from "zod";

export const TheaterSchema = z.object({
  name: z.string().min(1, "Fill Theater name"),
  noOfRoom: z.number().int().positive().min(1),
  address: z.string().min(1, "fill address of place"),
  city: z.enum(CITIES, "please enter valid city"),
  googleMapUrl: z.url("Please enter a valid Google Maps URL"),
  status: z.enum(THEATER_STATUS, "Current situation"),
});
export type Theater = z.infer<typeof TheaterSchema>;
