import { shutDown } from "@/api/theater";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { toast } from "@/components/ui/toast";
import { useState } from "react";
import { Spinner } from "@/components/ui/spinner";

export default function ShutDownTheater({
  theaterId,
  open,
  onOpenChange,
  onRefresh,
}: {
  theaterId: number;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onRefresh: () => Promise<void>;
}) {
  const [loading, setLoading] = useState(false);

  const submit = async (id: number) => {
    try {
      setLoading(true);
      await shutDown(id);
      toast.add({
        type: "success",
        description: "Theater shut down successfully.",
      });
      await onRefresh();
      onOpenChange(false);
    } catch {
      toast.add({
        type: "error",
        description: "Failed to shut down theater.",
      });
    } finally {
      setLoading(false);
    }
  };
  return (
    <Dialog
      open={open}
      onOpenChange={(open) => {
        if (!open) onOpenChange(false);
      }}
    >
      <DialogContent className="sm:max-w-sm">
        <DialogHeader>
          <DialogTitle>ShutDown Theater</DialogTitle>
          <DialogDescription>
            Are you sure to ShutDown theater ?
          </DialogDescription>
        </DialogHeader>
        <DialogFooter>
          <DialogClose render={<Button variant="outline">Cancel</Button>} />
          <Button
            type="button"
            onClick={() => submit(theaterId)}
            disabled={loading}
          >
            {loading ? (
              <>
                {" "}
                <Spinner data-icon="inline-start" />
                ShutDowning
              </>
            ) : (
              "ShutDown"
            )}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
