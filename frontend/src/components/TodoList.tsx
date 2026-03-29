import { useState, useEffect } from 'react'
import { fetchTodos, completeTodo, deleteTodo } from '../api/todos'
import type { Todo } from '../types/api'
import { TodoForm } from './TodoForm'

export function TodoList() {
  const [todos, setTodos] = useState<Todo[]>([])
  const [error, setError] = useState<string | null>(null)

  const load = async () => {
    try {
      setError(null)
      const data = await fetchTodos()
      setTodos(data)
    } catch (e) {
      setError(String(e))
    }
  }

  const handleComplete = async (id: number) => {
    try {
      setError(null)
      await completeTodo(id)
      await load()
    } catch (e) {
      setError(String(e))
    }
  }

  const handleDelete = async (id: number) => {
    try {
      setError(null)
      await deleteTodo(id)
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
      <h2>Todo API</h2>
      <TodoForm onCreated={load} />
      <button onClick={load} style={{ marginBottom: '1rem' }}>再読み込み</button>
      {error && <p className="error">{error}</p>}
      {todos.length === 0 ? (
        <p>Todoがありません</p>
      ) : (
        <table className="user-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>タイトル</th>
              <th>ステータス</th>
              <th>作成日時</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {todos.map((t) => (
              <tr key={t.id}>
                <td>{t.id}</td>
                <td>
                  {t.completed ? (
                    <span style={{ textDecoration: 'line-through', color: '#666' }}>{t.title}</span>
                  ) : (
                    t.title
                  )}
                </td>
                <td>{t.completed ? '完了' : '未完了'}</td>
                <td>{t.createdAt}</td>
                <td>
                  {!t.completed && (
                    <button onClick={() => handleComplete(t.id)} style={{ marginRight: '0.5rem' }}>
                      完了
                    </button>
                  )}
                  <button className="delete-btn" onClick={() => handleDelete(t.id)}>
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
