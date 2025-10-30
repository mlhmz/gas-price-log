import { useForm } from "react-hook-form"
import { Button } from "./ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { Input } from "./ui/input"
import { zodResolver } from "@hookform/resolvers/zod"
import { forecastGroupMutateSchema, type ForecastGroupMutation, type ForecastGroupQuery } from "@/types/ForecastGroup"
import { useMutation } from "@tanstack/react-query"

const postForecastGroup = async (forecastGroup: ForecastGroupMutation): Promise<ForecastGroupQuery> => {
    const response = await fetch("/api/v1/forecastgroups", {
        method: "POST",
        body: JSON.stringify(forecastGroup),
        headers: {
            "Content-Type": "application/json"
        }
    })
    if (!response.ok) {
        console.log(response);
    }
    return response.json()
}

export const ForecastGroupEditor = () => {
    const { register, handleSubmit, formState } = useForm({
        resolver: zodResolver(forecastGroupMutateSchema)
    })
    const { mutate } = useMutation({
        mutationKey: ["forecastgroup"],
        mutationFn: postForecastGroup
    })


    console.log(formState.errors)

    const onSubmit = (data: ForecastGroupMutation) => {
        mutate(data);
    }

    return (
        <Card className="flex flex-col gap-2 w-1/3 mx-auto my-5">
            <CardHeader>
                <CardTitle className="text-center"><h1>Create a Forecast Group</h1></CardTitle>
            </CardHeader>
            <CardContent className="flex flex-col gap-2">
                <Input placeholder="Group Name" {...register("groupName")} />
                <div className="flex gap-2">
                    <Input placeholder="Price per KWh" {...register("gasPricePerKwh", {
                        valueAsNumber: true
                    })} />
                    <Input placeholder="KWh Factor per m³" {...register("kwhFactorPerQubicmeter", {
                        valueAsNumber: true
                    })} />
                </div>
                <Button onClick={handleSubmit(onSubmit)}>Create</Button>
            </CardContent>
        </Card>
    )
}