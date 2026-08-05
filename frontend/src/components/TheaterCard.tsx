import { useEffect, useState } from "react";
import type { ShowType } from "@/types/Show";
import { theaterShows } from "@/api/show";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Link } from "react-router-dom";
import { Separator } from "@/components/ui/separator";
import { Button } from "@/components/ui/button";
import Loading from "./Loading";

export default function TheaterCard({
  theaterId,
  theaterName,
}: {
  theaterId: number;
  theaterName: string;
}) {
  const [shows, setShows] = useState<ShowType[] | null>(null);

  useEffect(() => {
    const loadShow = async () => {
      const res = await theaterShows(theaterId);
      setShows(res.data);
    };
    loadShow();
  }, [theaterId]);

  if (shows === null) return <Loading />;

  if (shows.length === 0) {
    return (
      <div className="m-auto md:w-[85%] w-[95%] space-y-4 p-5">
        <div className="typeset">
          <p className="text-xl md:text-3xl ">{theaterName}</p>
        </div>
        <Separator orientation="vertical" />

        <p className="text-center">There no show for today</p>
      </div>
    );
  }

  return (
    <div className="m-auto md:w-[85%] w-[95%] space-y-4 p-5">
      <div className="typeset">
        <p className="text-xl md:text-3xl ">{theaterName}</p>
      </div>
      <Separator orientation="vertical" />
      <Carousel
        opts={{
          align: "start",
        }}
      >
        <CarouselContent>
          {shows.map((show) => (
            <CarouselItem
              key={show.id}
              className="basis-1/1 md:basis-1/2 lg:basis-1/3 xl:basis-1/4 "
            >
              <Card className="h-full flex flex-col">
                <CardHeader>
                  <CardTitle>{show.movieName}</CardTitle>
                </CardHeader>
                <CardContent className="flex-1">
                  <div className="space-y-2">
                    <p>{new Date(show.startTime).toLocaleString()}</p>
                    <p>
                      ₹{show.economySeatPrice} - ₹{show.reclinerSeatPrice}
                    </p>
                  </div>
                </CardContent>
                <CardFooter>
                  <Link
                    to={`${show.status === "CANCELLED" ? "#" : "/shows/" + show.id}`}
                    className="w-full"
                  >
                    <Button
                      type="button"
                      className="w-full"
                      disabled={show.status === "CANCELLED"}
                    >
                      {show.status === "CANCELLED" ? "Cancelled" : "View Seats"}
                    </Button>
                  </Link>
                </CardFooter>
              </Card>
            </CarouselItem>
          ))}
        </CarouselContent>
        <CarouselPrevious className="hidden lg:flex" />
        <CarouselNext className="hidden lg:flex" />
      </Carousel>
    </div>
  );
}
