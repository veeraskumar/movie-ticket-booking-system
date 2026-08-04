import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Login from "./pages/auth/Login";
import ForgotPassword from "./pages/auth/ForgotPassword";
import ConfirmPassword from "./pages/auth/ConfirmPassword";
import NotFound from "./pages/NotFound";
import Home from "./pages/Home";
import SignUp from "./pages/auth/SignUp";
import ShowCard from "./pages/ShowCard";
import ProtectedRoute from "./router/ProtectedRoute";
import Booking from "./pages/Booking";
import TheaterOwner from "./pages/TheaterDashboard";
import ShowDashboard from "./pages/ShowDashboard";
import Profile from "./pages/Profile";
import AdminDashboard from "./pages/AdminDashboard";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        {/* Auth */}
        <Route path="/login" element={<Login />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/confirm-password" element={<ConfirmPassword />} />
        <Route path="/sign-up" element={<SignUp />} />

        <Route path={"/"} element={<Home />} />
        <Route path={`/shows/:id`} element={<ShowCard />} />

        {/* Protect Routes Need login */}
        <Route
          path="/booking"
          element={
            <ProtectedRoute>
              <Booking />
            </ProtectedRoute>
          }
        />
        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />

        {/* Owner */}
        <Route
          path="/owner"
          element={
            <ProtectedRoute roles={["ROLE_OWNER", "ROLE_ADMIN"]}>
              <TheaterOwner />
            </ProtectedRoute>
          }
        />
        <Route
          path="/owner/shows/:id"
          element={
            <ProtectedRoute roles={["ROLE_OWNER", "ROLE_ADMIN"]}>
              <ShowDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin"
          element={
            <ProtectedRoute roles={["ROLE_ADMIN"]}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        {/* 404 Not Found Page */}
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
}
