import { createBrowserRouter } from "react-router";
import { EditForecastGroup } from "./pages/EditForecastGroup";
import { ShowForecastGroup } from "./pages/ShowForecastGroup";
import { ErrorPage } from "./pages/NotFound";

export const router = createBrowserRouter([
	{
		path: "/",
		element: <EditForecastGroup />,
	},
	{
		path: "/:uuid",
		element: <ShowForecastGroup />,
	},
	{
		path: "*",
		element: <ErrorPage />,
	},
	{
		path: "/404",
		element: <ErrorPage />,
	}
]);
