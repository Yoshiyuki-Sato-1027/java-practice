import { apiFetch } from './client'
import type { Todo, CreateTodoRequest } from '../types/api'

export const fetchTodos = () => apiFetch<Todo[]>('/todos')

export const createTodo = (data: CreateTodoRequest) =>
  apiFetch<Todo>('/todos', {
    method: 'POST',
    body: JSON.stringify(data),
  })

export const completeTodo = (id: number) =>
  apiFetch<Todo>(`/todos/${id}/complete`, { method: 'PUT' })

export const deleteTodo = (id: number) =>
  apiFetch<void>(`/todos/${id}`, { method: 'DELETE' })
