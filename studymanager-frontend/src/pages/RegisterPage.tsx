import { useState, type FormEvent } from 'react'
import '../App.css'

type RegisterPageProps = { onLogin: () => void }

function RegisterPage({ onLogin }: RegisterPageProps) {
  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [error, setError] = useState('')
  const [registered, setRegistered] = useState(false)

  async function handleSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
    if (isSubmitting) return
    setError('')
    if (!name.trim() || !password.trim()) {
      setError('Name and password cannot be blank.')
      return
    }
    setIsSubmitting(true)
    try {
      const response = await fetch('http://localhost:8080/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name.trim(), email: email.trim(), password }),
      })
      if (!response.ok) {
        setError(response.status === 409
          ? 'This email is already registered.'
          : 'Unable to create your account. Check your details and try again.')
        return
      }
      setPassword('')
      setRegistered(true)
    } catch {
      setError('Unable to connect to the server. Please try again.')
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <main>
      <h1>StudyManager</h1>
      <h2>Create an account</h2>
      {registered ? (
        <p role="status">Account created successfully. You can now log in.</p>
      ) : (
        <form onSubmit={handleSubmit}>
          <label htmlFor="register-name">Name</label>
          <input id="register-name" name="name" autoComplete="name" required maxLength={150}
            value={name} onChange={(event) => setName(event.target.value)} disabled={isSubmitting} />
          <label htmlFor="register-email">Email</label>
          <input id="register-email" name="email" type="email" autoComplete="email" required
            value={email} onChange={(event) => setEmail(event.target.value)} disabled={isSubmitting} />
          <label htmlFor="register-password">Password</label>
          <input id="register-password" name="password" type="password" autoComplete="new-password"
            required minLength={8} maxLength={72} aria-describedby="password-help"
            value={password} onChange={(event) => setPassword(event.target.value)} disabled={isSubmitting} />
          <p id="password-help">Use between 8 and 72 characters.</p>
          {error && <p role="alert">{error}</p>}
          <button type="submit" disabled={isSubmitting}>
            {isSubmitting ? 'Creating account…' : 'Register'}
          </button>
        </form>
      )}
      <button type="button" onClick={onLogin} disabled={isSubmitting}>Back to login</button>
    </main>
  )
}

export default RegisterPage
