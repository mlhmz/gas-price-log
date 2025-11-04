import { entryMutationSchema, type EntryMutation } from "@/types/Entry";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { Label } from "./ui/label";
import { toast } from "sonner";
import { useState } from "react";
import { Popover, PopoverContent, PopoverTrigger } from "./ui/popover";
import { ChevronDownIcon } from "lucide-react";
import { Calendar } from "./ui/calendar";
import { useQueryClient } from "@tanstack/react-query";
import { useMutateEntry } from "@/hooks/use-mutate-entry";

export const EntryForm = ({
	forecastGroupUuid,
}: {
	forecastGroupUuid?: string;
}) => {
	const { register, handleSubmit, formState, getValues, setValue } = useForm({
		resolver: zodResolver(entryMutationSchema),
		defaultValues: {
			forecastGroup: {
				uuid: forecastGroupUuid,
			},
		},
	});
	const queryClient = useQueryClient();
	const { mutate } = useMutateEntry({
		onSuccess: (entry) => {
			queryClient.invalidateQueries({
				queryKey: ["forecastgroup", entry.forecastGroup.uuid],
			});
		},
	});
	const [open, setOpen] = useState(false);

	const formatDateToISO = (date?: Date): string => {
		if (!date) {
			return "";
		}
		console.log(date.toISOString());
		const isoString = date.toISOString();
		return isoString.substring(0, 10);
	};

	const onSubmit = (data: EntryMutation) =>
		mutate(data, {
			onSuccess: (entry) =>
				toast.success(
					`The entry with the uuid '${entry.uuid}' was successfully created.`,
				),
		});

	if (!forecastGroupUuid) {
		return null;
	}
	return (
		<form
			className="flex gap-3 p-5 items-end"
			onSubmit={handleSubmit(onSubmit)}
		>
			<input
				type="hidden"
				{...register("forecastGroup.uuid", {
					value: forecastGroupUuid,
				})}
			/>
			<div className="flex flex-col gap-1">
				<Label htmlFor="value">Value</Label>
				<Input
					{...register("value", {
						valueAsNumber: true,
					})}
					placeholder="123.456"
				/>
				<p>{formState?.errors?.value?.message}</p>
			</div>
			<div className="flex flex-col gap-1">
				<Label htmlFor="date">Date</Label>
				<Popover open={open} onOpenChange={setOpen}>
					<PopoverTrigger asChild>
						<Button
							variant="outline"
							id="date"
							className="w-48 justify-between font-normal"
						>
							{getValues("date") ? getValues("date") : "Select date"}
							<ChevronDownIcon />
						</Button>
					</PopoverTrigger>
					<PopoverContent className="w-auto overflow-hidden p-0" align="start">
						<Calendar
							mode="single"
							selected={new Date(getValues("date"))}
							captionLayout="dropdown"
							onSelect={(date) => {
								console.log(date);
								setValue("date", formatDateToISO(date), {
									shouldDirty: true,
									shouldTouch: true,
									shouldValidate: true,
								});
								setOpen(false);
							}}
						/>
					</PopoverContent>
				</Popover>
				<p>{formState?.errors?.date?.message}</p>
			</div>
			<div className="py-1">
				<Button type="submit">Submit</Button>
			</div>
		</form>
	);
};
