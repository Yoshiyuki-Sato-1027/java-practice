import { useState } from 'react'
import { createTodo } from '../api/todos'

interface Props {
  onCreated: () => void
}

export function TodoForm({ onCreated }: Props) {
  const [title, setTitle] = useState('')
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      setError(null)
      await createTodo({ title })
      setTitle('')
      onCreated()
    } catch (e) {
      setError(String(e))
    }
  }

  return (
    <form className="user-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="タイトル"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <button type="submit">Todo追加</button>
      {error && <p className="error">{error}</p>}
    </form>
  )
}
