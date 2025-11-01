import z from "zod";

export interface Entry {
	uuid?: string;
}

const forecastReferenceQuerySchema = z.object({
	uuid: z.uuid()
});

export const entryQuerySchema = z.object({
	uuid: z.uuid(),
	value: z.number(),
	date: z.string(),
	createdAt: z.string(),
	forecastGroup: forecastReferenceQuerySchema,
});

export type EntryQuery = z.infer<typeof entryQuerySchema>
