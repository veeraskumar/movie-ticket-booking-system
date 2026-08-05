import type { ShowType } from "@/types/Show";
import { Separator } from "../components/ui/separator";
import SeatGrid from "../components/SeatGrid";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { show as getShow } from "@/api/show";
import Loading from "../components/Loading";
import Navbar from "@/components/Navbar";

export default function ShowCard() {
  const { id } = useParams();

  const [show, setShow] = useState<ShowType | null>(null);

  useEffect(() => {
    const loadShow = async () => {
      const res = await getShow(Number(id));
      setShow(res.data);
    };
    loadShow();
  }, [id]);

  if (!show) return <Loading />;

  return (
    <div className="w-full">
      <Navbar />
      <div className="w-full p-5 space-y-3">
        <p className="text-lg md:text-2xl">{show.movieName}</p>
        <p>Theater Name : {show.theaterName}</p>
        <p>Date & Time - {new Date(show.startTime).toLocaleString()}</p>
        <div className="w-full flex items-center justify-around">
          <p>Economy ₹{show.economySeatPrice}</p>
          <Separator orientation="vertical" />
          <p>Economy ₹{show.premiumSeatPrice}</p>
          <Separator orientation="vertical" />
          <p>Economy ₹{show.reclinerSeatPrice}</p>
        </div>
      </div>
      <SeatGrid show={show} />
    </div>
  );
}
