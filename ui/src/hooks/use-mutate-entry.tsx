import { isServerError } from "@/types/Common";
import { entryQuerySchema, type EntryMutation, type EntryQuery } from "@/types/Entry";
import { useMutation } from "@tanstack/react-query";
import type { Dispatch } from "react";

const postEntry = async (
	entry: EntryMutation,
): Promise<EntryQuery> => {
	const response = await fetch("/api/v1/entries", {
		method: "POST",
		body: JSON.stringify(entry),
		headers: {
			"Content-Type": "application/json",
		},
	});
	const json = await response.json();
	const parseResult = entryQuerySchema.safeParse(json);
	if (parseResult.success) {
			return parseResult.data;
		}
		if (isServerError(json)) {
			throw new Error(json.message);
		}
		throw parseResult.error;
};

export const useMutateEntry = ({
	onSuccess,
}: {
	onSuccess: Dispatch<EntryQuery>;
}) => {
	return useMutation({
		mutationKey: ["entry"],
		mutationFn: postEntry,
		onSuccess: onSuccess,
	});
};
