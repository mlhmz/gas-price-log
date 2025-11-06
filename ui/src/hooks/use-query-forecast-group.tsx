import {
	isServerError,
} from "@/types/Common";
import {
	forecastGroupQuerySchema,
	type ForecastGroupQuery,
} from "@/types/ForecastGroup";
import { useQuery } from "@tanstack/react-query";
import { toast } from "sonner";

const getForecastGroup = async (
	uuid?: string,
): Promise<ForecastGroupQuery> => {
	const response = await fetch(`/api/v1/forecastgroups/${uuid}`, {
		method: "GET",
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

export const useQueryForecastGroup = ({ uuid }: { uuid?: string }) => {
	const query = useQuery({
		queryKey: ["forecastgroup", uuid],
		queryFn: () => getForecastGroup(uuid),
		enabled: !!uuid,
		retry: false
	});
	if (query.error) {
		toast.error(query.error.message);
		console.error(query.error);
	}
	return query;
};
