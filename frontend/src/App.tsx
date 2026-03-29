import './App.css'
import { HelloPanel } from './components/HelloPanel'
import { TimePanel } from './components/TimePanel'
import { StatusPanel } from './components/StatusPanel'
import { UserList } from './components/UserList'

function App() {
  return (
    <div className="app">
      <header>
        <h1>Java Practice API</h1>
        <p>Spring Boot + React TypeScript</p>
      </header>
      <main>
        <HelloPanel />
        <TimePanel />
        <StatusPanel />
        <UserList />
      </main>
    </div>
  )
}

export default App
