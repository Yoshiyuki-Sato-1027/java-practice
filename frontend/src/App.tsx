import { BrowserRouter, Routes, Route, Link, NavLink } from 'react-router-dom'
import './App.css'
import { HelloPanel } from './components/HelloPanel'
import { TimePanel } from './components/TimePanel'
import { StatusPanel } from './components/StatusPanel'
import { UserList } from './components/UserList'
import { TodoList } from './components/TodoList'

function TopPage() {
  return (
    <main>
      <HelloPanel />
      <TimePanel />
      <StatusPanel />
      <TodoList />
    </main>
  )
}

function UsersPage() {
  return (
    <main>
      <UserList />
    </main>
  )
}

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <header>
          <h1>Java Practice API</h1>
          <p>Spring Boot + React TypeScript</p>
          <nav className="nav">
            <NavLink to="/" end className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
              ホーム
            </NavLink>
            <NavLink to="/users" className={({ isActive }) => isActive ? 'nav-link active' : 'nav-link'}>
              ユーザー一覧
            </NavLink>
          </nav>
        </header>
        <Routes>
          <Route path="/" element={<TopPage />} />
          <Route path="/users" element={<UsersPage />} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}

export default App
