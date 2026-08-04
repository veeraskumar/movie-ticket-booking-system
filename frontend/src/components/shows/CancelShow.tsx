import { cancelShow } from "@/api/show";
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

export default function CancelShow({
  theaterId,
  showId,
  open,
  onOpenChange,
  onRefresh,
}: {
  theaterId: number;
  showId: number;
  open: boolean;
  onOpenChange: (open: boolean) => void;
  onRefresh: (id: number) => Promise<void>;
}) {
  const [loading, setLoading] = useState(false);

  const submit = async (id: number) => {
    try {
      setLoading(true);
      await cancelShow(id);
      toast.add({
        type: "success",
        description: "show Canceled successfully.",
      });
      await onRefresh(theaterId);
      onOpenChange(false);
    } catch {
      toast.add({
        type: "error",
        description: "Failed to cancel show try again.",
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
            onClick={() => submit(showId)}
            disabled={loading}
          >
            {loading ? (
              <>
                {" "}
                <Spinner data-icon="inline-start" />
                Cancelling
              </>
            ) : (
              "Cancel Show"
            )}
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
