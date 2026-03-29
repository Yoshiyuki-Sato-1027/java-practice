import { apiFetch } from './client'
import type { StatusResponse } from '../types/api'

export const fetchStatus = () => apiFetch<StatusResponse>('/status')
