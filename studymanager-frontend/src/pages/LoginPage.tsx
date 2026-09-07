import { useState } from 'react'
import '../App.css'

type LoginPageProps = { onRegister: () => void }

function LoginPage({ onRegister }: LoginPageProps) {
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

    </main>
  )
}

export default LoginPage
