import type { EntryQuery } from "@/types/Entry";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "./ui/table";

export const EntriesTable = ({ entries }: { entries: Array<EntryQuery> }) => {
    return (
        <Table>
				<TableHeader>
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
    );
}