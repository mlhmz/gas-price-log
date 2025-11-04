import { EntryForm } from "@/components/EntryForm";
import {
	Table,
	TableBody,
	TableCell,
	TableHead,
	TableHeader,
	TableRow,
} from "@/components/ui/table";
import { useQueryForecastGroup } from "@/hooks/use-query-forecast-group";
import { useParams } from "react-router";

export const ShowForecastGroup = () => {
	const { uuid } = useParams();
	const { data, error } = useQueryForecastGroup({ uuid: uuid });

	if (error) {
		return (
			<div className="flex flex-col w-screen h-screen items-center justify-center gap-3">
				<h1 className="text-5xl font-bold">An error occured. 😪</h1>
				<code className="bg-gray-200 p-4 text-sm rounded-md max-w-1/2 h-14 overflow-x-auto overflow-y-clip text-nowrap">
					{error.message}
				</code>
			</div>
		);
	}
	return (
		<div>
			<p>{data?.uuid}</p>
			<p>{data?.groupName}</p>
			<p>{data?.gasPricePerKwh}</p>
			<p>{data?.kwhFactorPerQubicmeter}</p>
			<hr />
			<Table>
				<TableHeader>
					<TableRow>
						<TableHead>Value</TableHead>
						<TableHead>Date</TableHead>
					</TableRow>
				</TableHeader>
				<TableBody>
					{data?.entries.map((entry) => (
						<TableRow key={entry.uuid}>
							<TableCell id="value">{entry.value}</TableCell>
							<TableCell id="date">{entry.date}</TableCell>
						</TableRow>
					))}
				</TableBody>
			</Table>
			<EntryForm forecastGroupUuid={data?.uuid} />
			{/*data?.spans.map((span) => (
				<div key={span.uuid}>
					<p>Span</p>
					<p>UUID: {span.uuid}</p>
					<p>From: {span.fromEntry?.uuid}</p>
					<p>To: {span.toEntry?.uuid}</p>
					<p>{span.days}</p>
					<p>{span.difference}</p>
					<p>{span.gasPerDay}</p>
					<p>{span.priceOfSpan}</p>
					<p>{span.pricePerMonthOnSpanBasis}</p>
					<p>{span.pricePerDay}</p>
				</div>
			))*/}
			<hr />
		</div>
	);
};
