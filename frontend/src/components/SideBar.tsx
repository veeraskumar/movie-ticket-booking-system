import { isLogin } from "@/utils/login";
import {
  Building2,
  User,
  LogOut,
  ListIcon,
  type LucideProps,
} from "lucide-react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { Button } from "./ui/button";
import { Separator } from "./ui/separator";

type userLinkListType = {
  name: string;
  href: string;
  icon: React.ForwardRefExoticComponent<
    Omit<LucideProps, "ref"> & React.RefAttributes<SVGSVGElement>
  >;
};

export default function SideBar({ onClose }: { onClose: () => void }) {
  const user = isLogin();
  const location = useLocation();
  const navigate = useNavigate();

  const userLinkList: userLinkListType[] = [
    { name: "Tickets", href: "/booking", icon: ListIcon },
    { name: "Profile", href: "/profile", icon: User },
  ];

  const OwnerLinkList: userLinkListType[] = [
    { name: "Theater", href: "/owner", icon: Building2 },
  ];

  const submit = () => {
    localStorage.removeItem("token");
    onClose();
    navigate("/", { replace: true });
  };

  return (
    <div
      className="fixed top-16 inset-0 z-50 bg-black/40 backdrop-blur-sm"
      onClick={onClose}
    >
      <aside
        className="absolute right-0 h-full w-1/2 bg-background p-4 md:w-75"
        onClick={(e) => e.stopPropagation()}
      >
        <div className="w-full m-auto h-full bg-background p-4 absolute left-0">
          <p className="text-lg font-semibold py-4">{user.sub}</p>
          {userLinkList.map((link) => (
            <Link
              key={link.href}
              to={link.href}
              onClick={onClose}
              className={`flex items-center gap-2 rounded p-2 ${
                location.pathname === link.href
                  ? "bg-primary text-accent"
                  : "hover:bg-muted"
              }`}
            >
              {<link.icon />} {link.name}
            </Link>
          ))}
          <Separator />
          {user.role[0] === "ROLE_OWNER" && (
            <>
              <p className="text-lg font-semibold py-4">My Theater's</p>
              {OwnerLinkList.map((link) => (
                <Link
                  key={link.href}
                  to={link.href}
                  onClick={onClose}
                  className={`flex items-center gap-2 rounded p-2  ${
                    location.pathname === link.href
                      ? "bg-primary text-accent"
                      : "hover:bg-muted"
                  }`}
                >
                  {<link.icon />} {link.name}
                </Link>
              ))}
            </>
          )}
          {user.role[0] === "ROLE_ADMIN" && (
            <>
              <p className="text-lg font-semibold py-4">My Theater's</p>
              {OwnerLinkList.map((link) => (
                <Link
                  key={link.href}
                  to={link.href}
                  onClick={onClose}
                  className={`flex items-center gap-2 rounded p-2  ${
                    location.pathname === link.href
                      ? "bg-primary text-accent"
                      : "hover:bg-muted"
                  }`}
                >
                  {<link.icon />} {link.name}
                </Link>
              ))}
            </>
          )}
          <Separator />
          <Button
            type="button"
            variant="link"
            onClick={submit}
            className="py-4"
          >
            <LogOut />
            Logout
          </Button>
        </div>
      </aside>
    </div>
  );
}
