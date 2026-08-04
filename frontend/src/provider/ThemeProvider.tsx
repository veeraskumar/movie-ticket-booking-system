import { isLogin } from "@/utils/login";
import { ThemeProvider as NextThemeProvider } from "next-themes";

export default function ThemeProvider({
  children,
  ...props
}: React.ComponentProps<typeof NextThemeProvider>) {
  isLogin();
  return <NextThemeProvider {...props}>{children}</NextThemeProvider>;
}
