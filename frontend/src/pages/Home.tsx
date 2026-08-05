import { getTheaters } from "@/api/theater";
import Navbar from "@/components/Navbar";
import TheaterCard from "@/components/TheaterCard";
import type { City, TheaterType } from "@/types/Theater";
import { useEffect, useState } from "react";

export default function Home() {
  const [city] = useState<City>(
    (localStorage.getItem("city") as City) ?? "CHENNAI",
  );
  const [theaters, setTheaters] = useState<TheaterType[]>([]);

  useEffect(() => {
    const loadTheater = async () => {
      const theaterResponse = await getTheaters(city);
      setTheaters(theaterResponse.data);
    };
    loadTheater();
  }, [city]);

  return (
    <div>
      <Navbar />
      <main className="w-full p-4">
        {theaters.map((theater) => (
          <TheaterCard
            key={theater.id}
            theaterId={theater.id}
            theaterName={theater.name}
          />
        ))}
      </main>
    </div>
  );
}
