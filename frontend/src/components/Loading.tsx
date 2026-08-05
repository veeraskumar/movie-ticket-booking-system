import { Spinner } from "@/components/ui/spinner";

export default function Loading() {
  return (
    <div className="w-full h-dvh flex items-center justify-center gap-4">
      <Spinner />
    </div>
  );
}
