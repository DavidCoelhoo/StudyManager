import { useState } from 'react'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'

function App() {
  const [page, setPage] = useState<'login' | 'register'>('login')

  return page === 'login'
    ? <LoginPage onRegister={() => setPage('register')} />
    : <RegisterPage onLogin={() => setPage('login')} />
}

export default App
