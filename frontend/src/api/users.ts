import { apiFetch } from './client'
import type { User, CreateUserRequest } from '../types/api'

export const fetchUsers = () => apiFetch<User[]>('/users')

export const createUser = (data: CreateUserRequest) =>
  apiFetch<User>('/users', {
    method: 'POST',
    body: JSON.stringify(data),
  })

export const deleteUser = (id: number) =>
  apiFetch<void>(`/users/${id}`, { method: 'DELETE' })
