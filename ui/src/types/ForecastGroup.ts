import z from "zod";
import { entryQuerySchema } from "./Entry";
import { spanQuerySchema } from "./Span";

export const forecastGroupMutateSchema = z.object({
	groupName: z.string(),
	gasPricePerKwh: z.number(),
	kwhFactorPerQubicmeter: z.number(),
});

export type ForecastGroupMutation = z.infer<typeof forecastGroupMutateSchema>;

export const forecastGroupQuerySchema = z.object({
	uuid: z.uuid(),
	groupName: z.string(),
	gasPricePerKwh: z.number(),
	kwhFactorPerQubicmeter: z.number(),
	entries: z.array(entryQuerySchema),
	spans: z.array(spanQuerySchema),
});

export type ForecastGroupQuery = z.infer<typeof forecastGroupQuerySchema>;
