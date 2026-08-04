import ModeToggle from "@/components/ModeToggle";
import { Button } from "./ui/button";
import {
  Combobox,
  ComboboxContent,
  ComboboxEmpty,
  ComboboxInput,
  ComboboxItem,
  ComboboxList,
} from "@/components/ui/combobox";
import { Link, useNavigate } from "react-router-dom";
import { CITIES, type City } from "@/types/Theater";
import { useState } from "react";
import { getTheaters } from "@/api/theater";
import { isLogin } from "@/utils/login";
import AvatarMenu from "./AvatarMenu";

export default function Navbar() {
  const [city, setCity] = useState<City>(
    (localStorage.getItem("city") as City) ?? "CHENNAI",
  );
  const naviagte = useNavigate();
  const user = isLogin();

  const submit = async (city: City) => {
    setCity(city);
    localStorage.setItem("city", city);
    await getTheaters(city);
    naviagte(0);
  };

  return (
    <nav className="w-full h-16 flex border-b justify-between p-5 sticky top-0 bg-background z-10">
      <div className="flex items-center gap-4 flex-1">
        <Link to={"/"}>
          <img
            src="/favicon.svg"
            alt="Movie Ticket Booking"
            className="h-10 w-10 rounded"
          />
        </Link>
        <Combobox
          items={CITIES}
          defaultValue={city
            .toLowerCase()
            .replace(/_/g, " ")
            .replace(/\b\w/g, (c) => c.toUpperCase())}
        >
          <ComboboxInput
            placeholder="enter the city"
            className="w-35"
            showClear
          />
          <ComboboxContent>
            <ComboboxEmpty>No items found.</ComboboxEmpty>
            <ComboboxList>
              {(item: City) => (
                <ComboboxItem
                  key={item}
                  value={item
                    .toLowerCase()
                    .replace(/_/g, " ")
                    .replace(/\b\w/g, (c) => c.toUpperCase())}
                  onClick={() => submit(item)}
                >
                  {item
                    .toLowerCase()
                    .replace(/_/g, " ")
                    .replace(/\b\w/g, (c) => c.toUpperCase())}
                </ComboboxItem>
              )}
            </ComboboxList>
          </ComboboxContent>
        </Combobox>
        <h1 className="text-lg font-bold md:block hidden">
          Movie Ticket Booking
        </h1>
      </div>
      <div className="w-1/4 flex items-center justify-end gap-6">
        <ModeToggle />
        {!user ? (
          <Button type="button" onClick={() => naviagte("/login")}>
            Login
          </Button>
        ) : (
          <AvatarMenu />
        )}
      </div>
    </nav>
  );
}
