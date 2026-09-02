import { useState } from 'react'
import './App.css'

interface Task {
  id: number
  name: string
  subject: string
  deadline: string
  taskStatus: string
}

function App() {
  const [tasks, setTasks] = useState<Task[]>([])
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault()

    const response = await fetch('http://localhost:8080/auth/login', {

      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        email,
        password
      })
    })
    if (!response.ok) {
      console.log('Login failed')
      return
    }

    const data = await response.json()

    localStorage.setItem('token', data.token)
  }

  async function loadTasks() {
    const token = localStorage.getItem('token')

    const response = await fetch('http://localhost:8080/tasks', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })

    const data = await response.json()

    setTasks(data)
  }

  return (
    <main>
      <h1>StudyManager</h1>
      <p>Organize your studies.</p>

      <form onSubmit={handleSubmit}>
        <label>Email</label>
        <input
          type="email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
        />

        <label>Password</label>
        <input
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />

        <button type="submit">Login</button>
        <button type="button" onClick={loadTasks}>
          Load tasks
        </button>
      </form>

      <section>
        <h2>My Tasks</h2>

        {tasks.map((task) => (
          <div key={task.id}>
            <h3>{task.name}</h3>
            <p>Subject: {task.subject}</p>
            <p>Deadline: {task.deadline}</p>
            <p>Status: {task.taskStatus}</p>
          </div>
        ))}
      </section>
    </main>
  )
}

export default App