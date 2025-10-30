import z from "zod";

export const forecastGroupMutateSchema = z.object({
    groupName: z.string(),
    gasPricePerKwh: z.number(),
    kwhFactorPerQubicmeter: z.number(),
})

export type ForecastGroupMutation = z.infer<typeof forecastGroupMutateSchema>

export const forecastGroupQuerySchema = z.object({
    groupName: z.string(),
    gasPricePerKwh: z.number(),
    kwhFactorPerQubicmeter: z.number(),
    entries: z.array(z.object({
        uuid: z.uuid()
    })),
    spans: z.array(z.object({
        uuid: z.uuid()
    }))
})

export type ForecastGroupQuery = z.infer<typeof forecastGroupQuerySchema>
