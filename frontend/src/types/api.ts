export interface HelloResponse {
  message: string
  status: string
}

export interface TimeResponse {
  timestamp: string
  date: string
  time: string
}

export interface StatusResponse {
  status: string
  memory: {
    total: number
    used: number
    free: number
  }
}

export interface User {
  id: number
  name: string
  email: string
  createdAt: string
  updatedAt: string
}

export interface CreateUserRequest {
  name: string
  email: string
}

export interface Todo {
  id: number
  title: string
  completed: boolean
  createdAt: string
  updatedAt: string
}

export interface CreateTodoRequest {
  title: string
}
