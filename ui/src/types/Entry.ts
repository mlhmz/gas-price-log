import z from "zod";

const forecastReferenceSchema = z.object({
	uuid: z.uuid()
});

export const entryQuerySchema = z.object({
	uuid: z.uuid(),
	value: z.number(),
	date: z.string(),
	createdAt: z.string(),
	forecastGroup: forecastReferenceSchema,
});

export type EntryQuery = z.infer<typeof entryQuerySchema>

export const entryMutationSchema = z.object({
	value: z.number(),
	date: z.iso.date(),
	forecastGroup: forecastReferenceSchema
})

export type EntryMutation = z.infer<typeof entryMutationSchema>;