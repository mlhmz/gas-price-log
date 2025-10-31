import { forecastGroupQuerySchema } from "@/types/ForecastGroup";
import { useQuery } from "@tanstack/react-query";
import { toast } from "sonner";

const getForecastGroup = async (uuid?: string) => {
	const response = await fetch(`/api/v1/forecastgroups/${uuid}`, {
		method: "GET",
	});
	const json = await response.json();
	const parseResult = forecastGroupQuerySchema.safeParse(json);
	if (!parseResult.success) {
		throw parseResult.error;
	}
	return parseResult.data;
};

export const useQueryForecastGroup = ({ uuid }: { uuid?: string }) => {
	const query = useQuery({
		queryKey: ["forecastgroup", uuid],
		queryFn: () => getForecastGroup(uuid),
		enabled: !!uuid,
	});
	if (query.error) {
		toast.error(query.error.message);
		console.error(query.error);
	}
	return query;
};
