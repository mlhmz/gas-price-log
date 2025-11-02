import { entryMutationSchema, type EntryMutation } from "@/types/Entry";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { Input } from "./ui/input";
import { Button } from "./ui/button";

export const EntryEditor = ({ forecastGroupUuid }: { forecastGroupUuid?: string }) => {
    const { register, handleSubmit } = useForm({
        resolver: zodResolver(entryMutationSchema),
        defaultValues: {
            forecastGroup: {
                uuid: forecastGroupUuid,
            }
        }
    })
    
    const onSubmit = (data: EntryMutation) => {
        console.log(data)
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)}>
            <Input {...register("value")} />
            <Input {...register("date")} />
            <Button type="submit">Submit</Button>
        </form>
    );
}