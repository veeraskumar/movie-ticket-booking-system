import { theaterShows } from "@/api/show";
import Loading from "@/components/Loading";
import Navbar from "@/components/Navbar";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import type { ShowType } from "@/types/Show";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import type { TheaterType } from "@/types/Theater";
import { getTheaterDetail } from "@/api/theater";
import CreateShow from "@/components/shows/CreateShow";
import EditShow from "@/components/shows/EditShow";
import CancelShow from "@/components/shows/CancelShow";

export default function ShowDashboard() {
  const { id } = useParams();
  const [shows, setShows] = useState<ShowType[] | null>(null);
  const [theater, setTheater] = useState<TheaterType | null>(null);
  const [create, setCreate] = useState<boolean>(false);
  const [editShow, setEditShow] = useState<ShowType | null>(null);
  const [cancelShow, setCancelShow] = useState<number | null>(null);

  const load = async (id: number) => {
    const res = await theaterShows(id);
    const resTheater = await getTheaterDetail(id);
    setTheater(resTheater.data);
    setShows(res.data);
  };

  useEffect(() => {
    const lo = async () => {
      load(Number(id));
    };
    lo();
  }, [id]);

  if (shows === null) return <Loading />;

  if (shows.length === 0) {
    return (
      <>
        <Navbar />
        <div className="flex h-80 items-center justify-center">
          <p className="text-muted-foreground">
            No shows have been created for this theater yet.
          </p>
        </div>
      </>
    );
  }

  return (
    <div className="w-full">
      <Navbar />
      <div className="flex items-center justify-between p-3">
        <h2 className="text-2xl font-bold m-5"> My Theaters</h2>
        <Button type="button" onClick={() => setCreate(true)}>
          Create Show
        </Button>
      </div>
      <div className="w-full p-5 gap-3 grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4">
        {shows.map((show) => (
          <Card key={show.id}>
            <CardHeader>
              <p>
                Show Name -{" "}
                <span className="font-semibold text-lg">{show.movieName}</span>
              </p>
              <CardDescription>
                Timing - {new Date(show.startTime).toLocaleString()}
              </CardDescription>
            </CardHeader>
            <CardContent className="space-y-2">
              <p className="text-blue-500">
                Front seat - {0} to {show.economySeatTo}{" "}
                <span className="font-medium text-white">|</span> ₹
                {show.economySeatPrice}
              </p>
              <p className="text-yellow-500">
                Premium seat - {show.economySeatTo + 1} to {show.premiumSeatTo}{" "}
                <span className="font-medium text-white">|</span> ₹
                {show.premiumSeatPrice}
              </p>
              <p className="text-red-500">
                Recliner seat - {show.premiumSeatTo + 1} to
                {show.reclinerSeatTo}{" "}
                <span className="font-medium text-white">|</span> ₹
                {show.reclinerSeatPrice}
              </p>
              <p>
                Status:
                <span
                  className={
                    show.status === "RUNNING"
                      ? "ml-2 text-green-500"
                      : "ml-2 text-red-500"
                  }
                >
                  {show.status}
                </span>
              </p>
            </CardContent>
            <CardFooter className="w-full flex items-center justify-evenly">
              <Button
                type="button"
                variant="outline"
                onClick={() => setEditShow(show)}
              >
                Edit Show
              </Button>
              <Separator orientation="vertical" />
              <Button
                type="button"
                variant="outline"
                disabled={show.status === "CANCELLED"}
                onClick={() => setCancelShow(show.id)}
              >
                Cancel Show
              </Button>
            </CardFooter>
          </Card>
        ))}
      </div>
      {create && theater && (
        <CreateShow
          theater={theater}
          theaterId={Number(id)}
          open={true}
          onRefresh={load}
          onChangeOpen={(open) => {
            if (!open) setCreate(false);
          }}
        />
      )}
      {editShow && theater && (
        <EditShow
          show={editShow}
          theater={theater}
          theaterId={Number(id)}
          open={true}
          onRefresh={load}
          onChangeOpen={(open) => {
            if (!open) setEditShow(null);
          }}
        />
      )}
      {cancelShow && (
        <CancelShow
          theaterId={Number(id)}
          showId={cancelShow}
          open={true}
          onRefresh={load}
          onOpenChange={(open) => {
            if (!open) setCancelShow(null);
          }}
        />
      )}
    </div>
  );
}
