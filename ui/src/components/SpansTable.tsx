import {
	Table,
	TableBody,
	TableCell,
	TableHead,
	TableHeader,
	TableRow,
} from "./ui/table";
import type { SpanQuery } from "@/types/Span";

export const SpansTable = ({ spans }: { spans: Array<SpanQuery> }) => {
	return (
		<div className="h-[80vh] w-full flex flex-col border rounded-md">
			<Table>
				<TableHeader className="sticky top-0 bg-background z-10">
					<TableRow>
						<TableHead>Days</TableHead>
						<TableHead>From</TableHead>
						<TableHead>To</TableHead>
						<TableHead>Difference (KWh)</TableHead>
						<TableHead>Gas per Day (KWh)</TableHead>
						<TableHead>Price of Span</TableHead>
						<TableHead>Price per Month on Span Basis</TableHead>
						<TableHead>Price per Day</TableHead>
					</TableRow>
				</TableHeader>
				<TableBody>
					{spans.map((span) => (
						<TableRow key={span.uuid}>
							<TableCell id="days">{span.days}</TableCell>
							<TableCell id="from">{span.fromEntry.date}</TableCell>
							<TableCell id="to">{span.toEntry.date}</TableCell>
							<TableCell id="difference">{span.difference}</TableCell>
							<TableCell id="gasPerDay">{span.gasPerDay}</TableCell>
							<TableCell id="priceOfSpan">{span.priceOfSpan}</TableCell>
							<TableCell id="pricePerMonthOnSpanBasis">
								{span.pricePerMonthOnSpanBasis}
							</TableCell>
							<TableCell id="pricePerDay">{span.pricePerDay}</TableCell>
						</TableRow>
					))}
				</TableBody>
			</Table>
		</div>
	);
};
