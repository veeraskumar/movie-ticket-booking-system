import { isLogin } from "@/utils/login";
import { Avatar, AvatarFallback } from "./ui/avatar";
import SideBar from "./SideBar";
import { useState } from "react";

export default function AvatarMenu() {
  const user = isLogin();
  const [openSidebar, setOpenSidebar] = useState(false);
  if (!user) return null;

  return (
    <Avatar
      className="cursor-pointer"
      onClick={() => setOpenSidebar(!openSidebar)}
    >
      <AvatarFallback>{user.sub[0].toUpperCase()}</AvatarFallback>
      {openSidebar && <SideBar onClose={() => setOpenSidebar(false)} />}
    </Avatar>
  );
}
