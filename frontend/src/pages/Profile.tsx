import { me } from "@/api/auth";
import Loading from "@/components/Loading";
import Navbar from "@/components/Navbar";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import type { UserType } from "@/types/User";
import { useEffect, useState } from "react";
import UpdateUser from "@/components/UpdateUser";

export default function Profile() {
  const [user, setUser] = useState<UserType | null>(null);
  const [update, setUpdate] = useState<boolean>(false);

  const load = async () => {
    const res = await me();
    setUser(res.data);
  };

  useEffect(() => {
    const lo = () => load();
    lo();
  }, []);

  if (user === null) return <Loading />;

  return (
    <div className="w-full">
      <Navbar />
      <main className="w-full h-dvh flex items-center justify-center">
        <div className="md:w-[50%] space-y-4 p-5 rounded-lg border flex flex-col items-center gap-2">
          <Avatar className="h-16 w-16">
            <AvatarFallback className="text-xl">
              {user.name[0].toUpperCase()}
            </AvatarFallback>
          </Avatar>
          <dl className="space-y-3 w-full">
            <div className="flex justify-between">
              <dt>Name</dt>
              <dd>{user.name}</dd>
            </div>

            <div className="flex justify-between">
              <dt>Email</dt>
              <dd>{user.email}</dd>
            </div>

            {user.role !== "USER" && (
              <div className="flex justify-between">
                <dt>Role</dt>
                <dd>{user.role}</dd>
              </div>
            )}

            <div className="flex justify-between">
              <dt>Created</dt>
              <dd>{new Date(user.createdAt).toLocaleString()}</dd>
            </div>
          </dl>

          <Button
            type="button"
            className="w-full"
            onClick={() => setUpdate(true)}
          >
            Update
          </Button>
        </div>
      </main>
      {update && (
        <UpdateUser
          user={user}
          open={true}
          onRefresh={load}
          onOpenChange={(open) => {
            if (!open) setUpdate(false);
          }}
        />
      )}
    </div>
  );
}
