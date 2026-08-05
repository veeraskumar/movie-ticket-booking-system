import { getTheaterByowner } from "@/api/theater";
import Loading from "@/components/Loading";
import Navbar from "@/components/Navbar";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Separator } from "@/components/ui/separator";
import type { TheaterType } from "@/types/Theater";
import { useEffect, useState } from "react";
import Edit from "@/components/owner/EditTheater";
import ShutDownTheater from "@/components/owner/ShutDownTheater";
import CreateTheater from "@/components/owner/CreateTheater";
import { Link } from "react-router-dom";

export default function TheaterDashboard() {
  const [theaters, setTheaters] = useState<TheaterType[] | null>(null);
  const [edit, setEdit] = useState<TheaterType | null>(null);
  const [shutdownId, setShutdownId] = useState<number | null>(null);
  const [create, setCreate] = useState<boolean>(false);

  const load = async () => {
    const res = await getTheaterByowner();
    setTheaters(res.data);
  };

  useEffect(() => {
    const lo = () => load();
    lo();
  }, []);

  if (theaters === null) return <Loading />;

  if (theaters.length === 0) {
    return (
      <>
        <Navbar />
        <div className="flex items-center justify-between p-3">
          <h2 className="text-2xl font-bold m-5"> My Theaters</h2>
          <Button type="button" onClick={() => setCreate(true)}>
            Create Theater
          </Button>
        </div>
        <div className="flex h-80 items-center justify-center">
          <p className="text-muted-foreground">
            You haven't added any theaters yet.
          </p>
          {create && (
            <CreateTheater
              open={true}
              onRefresh={load}
              onOpenChange={(open) => {
                if (!open) setCreate(false);
              }}
            />
          )}
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
          Create Theater
        </Button>
      </div>
      <div className="w-full p-5 gap-3 grid grid-cols-1 md:grid-cols-3 lg:grid-cols-4">
        {theaters.map((theater) => (
          <Card key={theater.id}>
            <CardHeader>
              <CardTitle>Theater Name - {theater.name}</CardTitle>
              <CardDescription>City - {theater.city}</CardDescription>
            </CardHeader>
            <CardContent>
              <p>No Of Rooms - {theater.noOfRoom}</p>
              <div className="flex items-center text-nowrap ">
                <p>Theater Location</p>
                <Button variant="link">
                  <a
                    href={theater.googleMapUrl}
                    target="_blank"
                    rel="noopener noreferrer"
                  >
                    {theater.googleMapUrl}
                  </a>
                </Button>
              </div>
              <p>
                Current Status of the Theater -{" "}
                <span
                  className={`${theater.status === "RUNNING" ? "text-green-500" : "text-red-500"}`}
                >
                  {theater.status}
                </span>
              </p>
            </CardContent>
            <CardFooter className="w-full flex items-center justify-evenly">
              <Button
                type="button"
                variant="outline"
                onClick={() => setEdit(theater)}
              >
                Edit
              </Button>
              <Separator orientation="vertical" />
              <Button
                type="button"
                variant="outline"
                disabled={theater.status === "SHUTDOWN"}
                onClick={() => setShutdownId(theater.id)}
              >
                ShutDown
              </Button>
              <Separator orientation="vertical" />
              <Link to={`/owner/shows/${theater.id}`}>
                <Button type="button" variant="outline">
                  Shows
                </Button>
              </Link>
            </CardFooter>
          </Card>
        ))}
      </div>
      {create && (
        <CreateTheater
          open={true}
          onRefresh={load}
          onOpenChange={(open) => {
            if (!open) setCreate(false);
          }}
        />
      )}
      {edit && (
        <Edit
          theater={edit}
          open={true}
          onRefresh={load}
          onOpenChange={(open) => {
            if (!open) setEdit(null);
          }}
        />
      )}
      {shutdownId !== null && (
        <ShutDownTheater
          theaterId={shutdownId}
          open={true}
          onRefresh={load}
          onOpenChange={(open) => {
            if (!open) setShutdownId(null);
          }}
        />
      )}
    </div>
  );
}
