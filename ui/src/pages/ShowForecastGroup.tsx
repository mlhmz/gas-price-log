import { EntriesTable } from "@/components/EntriesTable";
import { EntryForm } from "@/components/EntryForm";
import { SpanChart } from "@/components/SpanChart";
import { SpansTable } from "@/components/SpansTable";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { useMutateRecalculateForecastGroup } from "@/hooks/use-mutate-recalculate-forecast-group";
import { useQueryForecastGroup } from "@/hooks/use-query-forecast-group";
import { QueryClient } from "@tanstack/react-query";
import { useParams } from "react-router";
import { toast } from "sonner";

export const ShowForecastGroup = () => {
	const { uuid } = useParams();
	const { data, error } = useQueryForecastGroup({ uuid: uuid });
	const queryClient = new QueryClient();
	const { mutate } = useMutateRecalculateForecastGroup({ onSuccess: () => {
		toast.success(`The spans of the forecast group '${uuid}' were successfully updated.`)
		console.log("Invalidating queries", "forecastgroup", uuid)
		queryClient.refetchQueries({ queryKey: ["forecastgroup", uuid] })
	} })

	if (error) {
		return (
			<div className="flex flex-col w-screen h-screen items-center justify-center gap-3">
				<h1 className="text-5xl font-bold">An error occured. 😪</h1>
				<code className="bg-gray-200 p-4 text-sm rounded-md max-w-1/2 h-14 overflow-x-auto overflow-y-clip text-nowrap">
					{error.message}
				</code>
			</div>
		);
	}
	return (
		<div className="flex flex-col items-center my-5">
			<Tabs defaultValue="group">
				<TabsList className="flex w-full gap-3">
					<TabsTrigger value="group">Overview</TabsTrigger>
					<TabsTrigger value="entries">Entries</TabsTrigger>
					<TabsTrigger value="spans">Spans</TabsTrigger>
					<Button size="sm" variant="ghost" onClick={() => mutate(uuid)}>Recalculate</Button>
				</TabsList>
				<TabsContent value="group" className="w-screen md:w-[70vw]">
					<Card className="max-md:rounded-none max-md:border-x-0">
						<CardContent className="flex flex-col items-center">
							<h1 className="text-xl text-center">{data?.groupName}</h1>
							<div className="flex gap-5">
								<p className="text-gray-400 text-xs">
									Gas Price per KWh: {data?.gasPricePerKwh}
								</p>
								<p className="text-gray-400 text-xs">
									Factor per m³: {data?.kwhFactorPerQubicmeter}
								</p>
							</div>
							{data?.spans && <SpanChart spans={data?.spans} />}
						</CardContent>
					</Card>
				</TabsContent>
				<TabsContent value="entries" className="w-screen md:w-[70vw]">
					<Card className="max-md:rounded-none max-md:border-x-0">
						<CardContent className="flex flex-col flex-wrap items-center">
							<EntriesTable entries={data?.entries ?? []} />
							<EntryForm forecastGroupUuid={data?.uuid} />
						</CardContent>
					</Card>
				</TabsContent>
				<TabsContent value="spans" className="w-screen md:w-[70vw]">
					<Card className="max-md:rounded-none max-md:border-x-0">
						<CardContent className="flex flex-col flex-wrap items-center">
							<SpansTable spans={data?.spans ?? []} />
						</CardContent>
					</Card>
				</TabsContent>
			</Tabs>
		</div>
	);
};
