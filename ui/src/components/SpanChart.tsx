import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import type { SpanQuery } from "@/types/Span";
import { useState } from "react";
import {
	CartesianGrid,
	Line,
	LineChart,
	Tooltip,
	XAxis,
	YAxis,
} from "recharts";

type SpanYAxis = {
	fieldName: string,
	displayName: string
};

const fields: Array<SpanYAxis> = [
	{
		fieldName: "pricePerMonthOnSpanBasis",
		displayName: "Price per Month on Span Basis",
	},
	{
		fieldName: "priceOfSpan",
		displayName: "Price of Span",
	},
	{
		fieldName: "pricePerDay",
		displayName: "Price per Day",
	},
	{
		fieldName: "difference",
		displayName: "Difference (KWh)",
	},
	{
		fieldName: "gasPerDay",
		displayName: "Gas per Day (KWh)",
	}
];

export const SpanChart = ({ spans }: { spans: Array<SpanQuery> }) => {
	const [selectedYAxis, setSelectedYAxis] = useState<SpanYAxis>(fields[0]);

	return (
		<div className="w-full h-full flex flex-col items-center my-5 gap-3">
			<Select value={selectedYAxis.fieldName} onValueChange={value => setSelectedYAxis(fields.filter(field => field.fieldName === value)[0])}>
				<SelectTrigger>
					<SelectValue />
				</SelectTrigger>
				<SelectContent>
					{fields.map(field => <SelectItem key={field.fieldName} value={field.fieldName}>{field.displayName}</SelectItem>)}
				</SelectContent>
			</Select>
			<LineChart
				style={{ width: "100%", aspectRatio: 1.618, maxWidth: 600 }}
				responsive
				data={spans}
			>
				<CartesianGrid />
				<Line
					type="monotone"
					name={selectedYAxis.displayName}
					dataKey={selectedYAxis.fieldName}
                    fill="#8d64f4"
                    stroke="#8d64f4"
				/>
				<XAxis dataKey="toEntry.date" />
				<YAxis />
				<Tooltip />
			</LineChart>
		</div>
	);
};