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
			<hr/>
			{data?.entries.map(entry => <div key={entry.uuid}>
				<p>{entry.uuid}</p>
				<p>{entry.value}</p>
				<p>{entry.date}</p>
			</div>
			)}
			<hr/>
			{data?.spans.map(span => <div key={span.uuid}>
				<p>{span.uuid}</p>
				<p>{span.fromEntry?.uuid}</p>
				<p>{span.toEntry?.uuid}</p>
				<p>{span.days}</p>
				<p>{span.difference}</p>
				<p>{span.gasPerDay}</p>
				<p>{span.priceOfSpan}</p>
				<p>{span.pricePerMonthOnSpanBasis}</p>
				<p>{span.pricePerDay}</p>
			</div>)}
			<hr/>
		</div>
	);
};
