import { useState } from 'react'
import '../App.css'

interface Task {
  id: number
  name: string
  subject: string
  deadline: string
  taskStatus: string
}

type LoginPageProps = { onRegister: () => void }

function LoginPage({ onRegister }: LoginPageProps) {
  const [tasks, setTasks] = useState<Task[]>([])
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [error, setError] = useState('')
  const [loggedIn, setLoggedIn] = useState(false)
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault()

    if (isSubmitting) return
    setIsSubmitting(true)
    setError('')
    setLoggedIn(false)
    try {
      const response = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password }),
      })
      if (!response.ok) {
        setError('Unable to log in. Check your email and password and try again.')
        return
      }
      const data = await response.json()
      localStorage.setItem('token', data.token)
      setLoggedIn(true)
    } catch {
      setError('Unable to complete login. Please try again.')
    } finally {
      setIsSubmitting(false)
    }
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
        <label htmlFor="login-email">Email</label>
        <input
          id="login-email"
          name="email"
          autoComplete="email"
          required
          disabled={isSubmitting}
          type="email"
          value={email}
          onChange={(event) => setEmail(event.target.value)}
        />

        <label htmlFor="login-password">Password</label>
        <input
          id="login-password"
          name="password"
          autoComplete="current-password"
          required
          disabled={isSubmitting}
          type="password"
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />

        {error && <p role="alert">{error}</p>}
        {loggedIn && <p role="status">Logged in successfully.</p>}
        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? 'Logging in…' : 'Login'}
        </button>
      </form>

      <button type="button" onClick={onRegister} disabled={isSubmitting}>Create an account</button>

      <section>
        <button type="button" onClick={loadTasks}>Load tasks</button>
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

export default LoginPage
