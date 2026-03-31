import { apiFetch } from './client'
import type { Todo, CreateTodoRequest } from '../types/api'

export const fetchTodos = () => apiFetch<Todo[]>('/todos')

export const createTodo = (data: CreateTodoRequest) =>
  apiFetch<Todo>('/todos', {
    method: 'POST',
    body: JSON.stringify(data),
  })

export const toggleTodo = (id: number) =>
  apiFetch<Todo>(`/todos/${id}/toggle`, { method: 'PUT' })

export const deleteTodo = (id: number) =>
  apiFetch<void>(`/todos/${id}`, { method: 'DELETE' })
