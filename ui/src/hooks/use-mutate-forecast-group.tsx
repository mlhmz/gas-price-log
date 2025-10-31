import type {
	ForecastGroupMutation,
	ForecastGroupQuery,
} from "@/types/ForecastGroup";
import { useMutation } from "@tanstack/react-query";
import type { Dispatch } from "react";

const postForecastGroup = async (
	forecastGroup: ForecastGroupMutation,
): Promise<ForecastGroupQuery> => {
	const response = await fetch("/api/v1/forecastgroups", {
		method: "POST",
		body: JSON.stringify(forecastGroup),
		headers: {
			"Content-Type": "application/json",
		},
	});
	if (!response.ok) {
		console.log(response);
	}
	return response.json();
};

export const useMutateForecastGroup = ({
	onSuccess,
}: {
	onSuccess: Dispatch<ForecastGroupQuery>;
}) => {
	return useMutation({
		mutationKey: ["forecastgroup"],
		mutationFn: postForecastGroup,
		onSuccess: onSuccess,
	});
};
