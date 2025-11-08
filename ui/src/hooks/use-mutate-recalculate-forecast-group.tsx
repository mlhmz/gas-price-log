import { isServerError } from "@/types/Common";
import { forecastGroupQuerySchema, type ForecastGroupQuery } from "@/types/ForecastGroup";
import { useMutation } from "@tanstack/react-query";
import type { Dispatch } from "react";

const postForecastGroup = async (uuid?: string) => {
	const response = await fetch(`/api/v1/forecastgroups/${uuid}/actions/recalculate`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
	});
	const json = await response.json();
	const parseResult = forecastGroupQuerySchema.safeParse(json);
	if (parseResult.success) {
		return parseResult.data;
	}
	if (isServerError(json)) {
		throw new Error(json.message);
	}
	throw parseResult.error;
};

export const useMutateRecalculateForecastGroup = ({
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
