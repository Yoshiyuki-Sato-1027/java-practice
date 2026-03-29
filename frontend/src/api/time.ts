import { apiFetch } from './client'
import type { TimeResponse } from '../types/api'

export const fetchTime = () => apiFetch<TimeResponse>('/time')
