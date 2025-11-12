import type { EntryQuery } from "@/types/Entry";
import {
	Table,
	TableBody,
	TableCell,
	TableHead,
	TableHeader,
	TableRow,
} from "./ui/table";

export const EntriesTable = ({ entries }: { entries: Array<EntryQuery> }) => {
	return (
		<div className="h-[60vh] md:h-[70vh] w-full flex flex-col border rounded-md">
			<Table>
				<TableHeader className="sticky top-0 bg-background z-10">
					<TableRow>
						<TableHead>Value</TableHead>
						<TableHead>Date</TableHead>
					</TableRow>
				</TableHeader>
				<TableBody>
					{entries.map((entry) => (
						<TableRow key={entry.uuid}>
							<TableCell id="value">{entry.value}</TableCell>
							<TableCell id="date">{entry.date}</TableCell>
						</TableRow>
					))}
				</TableBody>
			</Table>
		</div>
	);
};
