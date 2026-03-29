import { useState, useEffect } from 'react'
import { fetchTime } from '../api/time'
import type { TimeResponse } from '../types/api'

export function TimePanel() {
  const [data, setData] = useState<TimeResponse | null>(null)
  const [error, setError] = useState<string | null>(null)

  const refresh = async () => {
    try {
      setError(null)
      const result = await fetchTime()
      setData(result)
    } catch (e) {
      setError(String(e))
    }
  }

  useEffect(() => {
    refresh()
    const timer = setInterval(refresh, 5000)
    return () => clearInterval(timer)
  }, [])

  return (
    <section className="panel">
      <h2>Time API <span className="badge">5秒ごと自動更新</span></h2>
      {error && <p className="error">{error}</p>}
      {data && (
        <div className="result">
          <p>日付: {data.date}</p>
          <p>時刻: {data.time}</p>
          <code>{JSON.stringify(data, null, 2)}</code>
        </div>
      )}
    </section>
  )
}
