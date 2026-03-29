import { apiFetch } from './client'
import type { HelloResponse } from '../types/api'

export const fetchHello = () => apiFetch<HelloResponse>('/hello')

export const fetchGreet = (name: string) =>
  apiFetch<HelloResponse>(`/greet?name=${encodeURIComponent(name)}`)
