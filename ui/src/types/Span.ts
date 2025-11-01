import z from "zod";

export interface Span {
	uuid?: string;
}

const forecastReferenceQuerySchema = z.object({
	uuid: z.uuid()
})

const entryReferenceQuerySchema = z.object({
	uuid: z.uuid()
})

export const spanSchema = z.object({
	uuid: z.uuid(),
	fromEntry: entryReferenceQuerySchema,
	toEntry: entryReferenceQuerySchema,
	days: z.number(),
	difference: z.number(),
	gasPerDay: z.number(),
	priceOfSpan: z.number(),
	pricePerMonthOnSpanBasis: z.number(),
	pricePerDay: z.number(),
	forecastGroup: forecastReferenceQuerySchema
})

