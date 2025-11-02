import { entryMutationSchema, type EntryMutation } from "@/types/Entry";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { Label } from "./ui/label";

export const EntryEditor = ({
	forecastGroupUuid,
}: {
	forecastGroupUuid?: string;
}) => {
	const { register, handleSubmit, formState, getValues } = useForm({
		resolver: zodResolver(entryMutationSchema),
		defaultValues: {
			forecastGroup: {
				uuid: forecastGroupUuid,
			},
		},
	});

	const onSubmit = (data: EntryMutation) => {
		console.log(data);
	};

	if (!forecastGroupUuid) {
		return null;
	}
	return (
		<form className="flex gap-3 p-5" onSubmit={handleSubmit(onSubmit)}>
			<input type="hidden" {...register("forecastGroup.uuid", {
				value: forecastGroupUuid
			})} />
			<div className="flex flex-col gap-1">
                <Label htmlFor="value">Value</Label>
				<Input {...register("value", {
					valueAsNumber: true
				})} placeholder="123.456" />
				<p>{formState?.errors?.value?.message}</p>
			</div>
			<div className="flex flex-col gap-1">
                <Label htmlFor="date">Date</Label>
				<Input {...register("date")} />
				<p>{formState?.errors?.date?.message}</p>
			</div>
			<Button type="submit">Submit</Button>
		</form>
	);
};
