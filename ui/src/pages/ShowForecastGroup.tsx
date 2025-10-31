import { useQueryForecastGroup } from "@/hooks/use-query-forecast-group";
import { useParams } from "react-router";

export const ShowForecastGroup = () => {
	const { uuid } = useParams();
	const { data } = useQueryForecastGroup({ uuid: uuid });

	return (
		<div>
			<p>{data?.uuid}</p>
			<p>{data?.groupName}</p>
			<p>{data?.gasPricePerKwh}</p>
			<p>{data?.kwhFactorPerQubicmeter}</p>
		</div>
	);
};
