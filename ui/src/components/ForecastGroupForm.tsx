import { useForm } from "react-hook-form";
import { Button } from "./ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card";
import { Input } from "./ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import {
	forecastGroupMutateSchema,
	type ForecastGroupMutation,
} from "@/types/ForecastGroup";
import { useNavigate } from "react-router";
import { useMutateForecastGroup } from "@/hooks/use-mutate-forecast-group";

export const ForecastGroupForm = () => {
	const navigate = useNavigate();
	const { register, handleSubmit } = useForm({
		resolver: zodResolver(forecastGroupMutateSchema),
	});
	const { mutate } = useMutateForecastGroup({
		onSuccess: (group) => navigate(`/${group.uuid}`),
	});

	const onSubmit = (data: ForecastGroupMutation) => {
		mutate(data);
	};

	return (
		<form onSubmit={handleSubmit(onSubmit)}>
			<Card className="flex flex-col gap-2 w-1/3 mx-auto my-5">
				<CardHeader>
					<CardTitle className="text-center">
						<h1>Create a Forecast Group</h1>
					</CardTitle>
				</CardHeader>
				<CardContent className="flex flex-col gap-2">
					<Input placeholder="Group Name" {...register("groupName")} />
					<div className="flex gap-2">
						<Input
							placeholder="Price per KWh"
							{...register("gasPricePerKwh", {
								valueAsNumber: true,
							})}
						/>
						<Input
							placeholder="KWh Factor per m³"
							{...register("kwhFactorPerQubicmeter", {
								valueAsNumber: true,
							})}
						/>
					</div>
					<Button type="submit">
						Create
					</Button>
				</CardContent>
			</Card>
		</form>
	);
};
