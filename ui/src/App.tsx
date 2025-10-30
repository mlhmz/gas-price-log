import { QueryClient, QueryClientProvider } from "@tanstack/react-query"
import { ForecastGroupEditor } from "./components/ForecastGroupEditor"

const queryClient = new QueryClient()

function App() {
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <div>
          <ForecastGroupEditor />
        </div>
      </QueryClientProvider>
    </>
  )
}

export default App
