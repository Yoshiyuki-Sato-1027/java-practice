import { useState } from 'react'
import { fetchStatus } from '../api/status'
import type { StatusResponse } from '../types/api'

export function StatusPanel() {
  const [data, setData] = useState<StatusResponse | null>(null)
  const [error, setError] = useState<string | null>(null)

  const handleFetch = async () => {
    try {
      setError(null)
      const result = await fetchStatus()
      setData(result)
    } catch (e) {
      setError(String(e))
    }
  }

  return (
    <section className="panel">
      <h2>Status API</h2>
      <button onClick={handleFetch}>GET /api/status</button>
      {error && <p className="error">{error}</p>}
      {data && (
        <div className="result">
          <p>ステータス: {data.status}</p>
          <div className="memory-bar">
            <label>メモリ使用率</label>
            <div className="bar-track">
              <div
                className="bar-fill"
                style={{ width: `${(data.memory.used / data.memory.total) * 100}%` }}
              />
            </div>
            <p>使用: {data.memory.used}MB / 合計: {data.memory.total}MB</p>
          </div>
          <code>{JSON.stringify(data, null, 2)}</code>
        </div>
      )}
    </section>
  )
}
