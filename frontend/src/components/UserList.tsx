import { useState, useEffect } from 'react'
import { fetchUsers, deleteUser } from '../api/users'
import type { User } from '../types/api'
import { UserForm } from './UserForm'

export function UserList() {
  const [users, setUsers] = useState<User[]>([])
  const [error, setError] = useState<string | null>(null)

  const load = async () => {
    try {
      setError(null)
      const data = await fetchUsers()
      setUsers(data)
    } catch (e) {
      setError(String(e))
    }
  }

  const handleDelete = async (id: number) => {
    try {
      setError(null)
      await deleteUser(id)
      await load()
    } catch (e) {
      setError(String(e))
    }
  }

  useEffect(() => {
    load()
  }, [])

  return (
    <section className="panel">
      <h2>Users API</h2>
      <UserForm onCreated={load} />
      <button onClick={load} style={{ marginBottom: '1rem' }}>再読み込み</button>
      {error && <p className="error">{error}</p>}
      {users.length === 0 ? (
        <p>ユーザーがいません</p>
      ) : (
        <table className="user-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>名前</th>
              <th>メール</th>
              <th>作成日時</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {users.map((u) => (
              <tr key={u.id}>
                <td>{u.id}</td>
                <td>{u.name}</td>
                <td>{u.email}</td>
                <td>{u.createdAt}</td>
                <td>
                  <button className="delete-btn" onClick={() => handleDelete(u.id)}>
                    削除
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </section>
  )
}
