import { useParams } from "react-router"

export const ShowForecastGroup = () => {
    const { uuid } = useParams();

    return <>{uuid}</>
}