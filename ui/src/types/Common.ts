import z from "zod";

export const serverErrorSchema = z.object({
    status: z.number(),
    message: z.string()
})

export type ServerError = z.infer<typeof serverErrorSchema>;

export const isServerError = (error: unknown): error is ServerError => {
    return serverErrorSchema.safeParse(error).success;
}