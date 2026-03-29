import { useState } from 'react'
import { createUser } from '../api/users'

interface Props {
  onCreated: () => void
}

export function UserForm({ onCreated }: Props) {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [error, setError] = useState<string | null>(null)

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      setError(null)
      await createUser({ name, email })
      setName('')
      setEmail('')
      onCreated()
    } catch (e) {
      setError(String(e))
    }
  }

  return (
    <form className="user-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="名前"
        value={name}
        onChange={(e) => setName(e.target.value)}
        required
      />
      <input
        type="email"
        placeholder="メールアドレス"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        required
      />
      <button type="submit">ユーザー追加</button>
      {error && <p className="error">{error}</p>}
    </form>
  )
}
