export const THEATER_STATUS = ["RUNNING", "SHUTDOWN"] as const;

export type TheaterStatus = (typeof THEATER_STATUS)[number];

export const CITIES = [
  "ARIYALUR",
  "CHENGALPATTU",
  "CHENNAI",
  "COIMBATORE",
  "CUDDALORE",
  "DHARMAPURI",
  "DINDIGUL",
  "ERODE",
  "KALLAKURICHI",
  "KANCHIPURAM",
  "KANYAKUMARI",
  "KARUR",
  "KRISHNAGIRI",
  "MADURAI",
  "MAYILADUTHURAI",
  "NAGAPATTINAM",
  "NAMAKKAL",
  "NILGIRIS",
  "PERAMBALUR",
  "PUDUKKOTTAI",
  "RAMANATHAPURAM",
  "RANIPET",
  "SALEM",
  "SIVAGANGA",
  "TENKASI",
  "THANJAVUR",
  "THENI",
  "THIRUVALLUR",
  "THIRUVARUR",
  "THOOTHUKUDI",
  "TIRUCHIRAPPALLI",
  "TIRUNELVELI",
  "TIRUPATTUR",
  "TIRUPPUR",
  "TIRUVANNAMALAI",
  "VELLORE",
  "VILLUPURAM",
  "VIRUDHUNAGAR",
] as const;

export type City = (typeof CITIES)[number];

export type TheaterType = {
  id: number;
  name: string;
  noOfRoom: number;
  city: City;
  address: string;
  googleMapUrl: string;
  status: TheaterStatus;
};
