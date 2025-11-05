import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "./ui/table";
import type { SpanQuery } from "@/types/Span";

export const SpansTable = ({ spans }: { spans: Array<SpanQuery> }) => {
    return (
        <Table>
				<TableHeader>
					<TableRow>
						<TableHead>Days</TableHead>
						<TableHead>Difference (KWh)</TableHead>
                        <TableHead>Gas per Day (KWh)</TableHead>
                        <TableHead>Price of Span</TableHead>
                        <TableHead>Price per Day</TableHead>
					</TableRow>
				</TableHeader>
				<TableBody>
					{spans.map((span) => (
						<TableRow key={span.uuid}>
							<TableCell id="days">{span.days}</TableCell>
							<TableCell id="difference">{span.difference}</TableCell>
							<TableCell id="gasPerDay">{span.gasPerDay}</TableCell>
							<TableCell id="priceOfSpan">{span.priceOfSpan}</TableCell>
							<TableCell id="pricePerDay">{span.pricePerDay}</TableCell>
						</TableRow>
					))}
				</TableBody>
			</Table>
    );
}