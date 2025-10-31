import { createBrowserRouter } from "react-router";
import { EditForecastGroup } from "./pages/EditForecastGroup";
import { ShowForecastGroup } from "./pages/ShowForecastGroup";
import { NotFound } from "./pages/NotFound";

export const router = createBrowserRouter([
    {
        path: "/",
        element: <EditForecastGroup />
    },
    {
        path: "/:uuid",
        element: <ShowForecastGroup />
    },
    {
        path: "*",
        element: <NotFound />
    }
])