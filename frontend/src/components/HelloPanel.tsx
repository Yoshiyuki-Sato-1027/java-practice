import { useState } from 'react'
import { fetchHello, fetchGreet } from '../api/hello'
import type { HelloResponse } from '../types/api'

export function HelloPanel() {
  const [response, setResponse] = useState<HelloResponse | null>(null)
  const [name, setName] = useState('')
  const [error, setError] = useState<string | null>(null)

  const handleHello = async () => {
    try {
      setError(null)
      const data = await fetchHello()
      setResponse(data)
    } catch (e) {
      setError(String(e))
    }
  }

  const handleGreet = async () => {
    try {
      setError(null)
      const data = await fetchGreet(name || 'Guest')
      setResponse(data)
    } catch (e) {
      setError(String(e))
    }
  }

  return (
    <section className="panel">
      <h2>Hello API</h2>
      <div className="controls">
        <button onClick={handleHello}>GET /api/hello</button>
        <span>or</span>
        <input
          type="text"
          placeholder="名前を入力"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button onClick={handleGreet}>GET /api/greet</button>
      </div>
      {error && <p className="error">{error}</p>}
      {response && (
        <div className="result">
          <p>{response.message}</p>
          <code>{JSON.stringify(response, null, 2)}</code>
        </div>
      )}
    </section>
  )
}
