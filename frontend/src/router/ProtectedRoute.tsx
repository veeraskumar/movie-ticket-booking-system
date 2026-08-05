import { isLogin, type Role } from "@/utils/login";
import { Navigate } from "react-router-dom";

export default function ProtectedRoute({
  children,
  roles,
}: {
  children: React.ReactNode;
  roles?: Role[];
}) {
  const user = isLogin();

  if (!user) return null;

  if (!user) return <Navigate to={"/login"} replace />;

  if (roles && !roles.some((role) => user.role.includes(role)))
    return <Navigate to="/" replace />;

  return children;
}
