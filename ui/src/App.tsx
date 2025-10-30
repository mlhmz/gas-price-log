import { useEffect, useState } from 'react'
import './App.css'
import type { ForecastGroup } from './types/ForecastGroup';

async function fetchData() {
  const response = await fetch("/api/v1/forecastgroups");
  return await response.json() as ForecastGroup[]
}

function App() {
  const [data, setData] = useState<ForecastGroup[]>([])

  useEffect(() => {
    fetchData().then(res => setData(res))
  }, [])

  return (
    <>
      {
        data.map(value => <div>
          <p>{value.uuid}</p>
          <p>{value.groupName}</p>
          <p>{value.gasPricePerKwh}</p>
          <p>{value.kwhFactorPerQubicmeter}</p>
          <hr/>
          </div>)
      }
    </>
  )
}

export default App
