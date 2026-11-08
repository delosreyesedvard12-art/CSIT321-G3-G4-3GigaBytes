import { useState } from 'react'
import CrudPanel from './CrudPanel.jsx'
import { api } from './api.js'

// Field lists mirror the ERD in the project documentation.
const SCREENS = {
  Users: {
    path: '/api/users',
    hint: 'USER entity. Role decides the teacher dashboard or student workspace.',
    fields: [
      { name: 'fullName', label: 'Full name' },
      { name: 'email', label: 'Email', type: 'email' },
      { name: 'passwordHash', label: 'Password', secret: true, placeholder: 'leave blank to keep' },
      { name: 'role', label: 'Role', options: ['TEACHER', 'STUDENT'] },
    ],
  },
  Classrooms: {
    path: '/api/classrooms',
    hint: 'CLASSROOM entity. Leave Join code blank and the server generates a 6-character invitation code.',
    fields: [
      { name: 'teacherId', label: 'Teacher (user ID)', type: 'number' },
      { name: 'name', label: 'Class name' },
      { name: 'description', label: 'Description' },
      { name: 'joinCode', label: 'Join code', placeholder: 'auto' },
    ],
  },
  'Task Milestones': {
    path: '/api/tasks',
    hint: 'TASK_MILESTONE entity. Each task has a teacher-set deadline.',
    fields: [
      { name: 'classroomId', label: 'Classroom ID', type: 'number' },
      { name: 'title', label: 'Title' },
      { name: 'description', label: 'Description' },
      { name: 'deadline', label: 'Deadline', type: 'datetime-local' },
      { name: 'maxScore', label: 'Max score', type: 'number' },
    ],
  },
  Submissions: {
    path: '/api/submissions',
    hint: 'SUBMISSION entity: score, preparation minutes and a 1-5 confidence rating per task.',
    fields: [
      { name: 'taskId', label: 'Task ID', type: 'number' },
      { name: 'studentId', label: 'Student (user ID)', type: 'number' },
      { name: 'score', label: 'Score', type: 'number' },
      { name: 'prepMinutes', label: 'Prep minutes', type: 'number' },
      { name: 'confidenceLevel', label: 'Confidence (1-5)', type: 'number' },
    ],
  },
  Enrollments: {
    path: '/api/enrollments',
    hint: 'CLASS_ENROLLMENT entity. Use the join box to enroll with an invitation code.',
    fields: [
      { name: 'classroomId', label: 'Classroom ID', type: 'number' },
      { name: 'studentId', label: 'Student (user ID)', type: 'number' },
      { name: 'status', label: 'Status', options: ['ACTIVE', 'INACTIVE'], default: 'ACTIVE' },
    ],
  },
}

function JoinBox({ onJoined }) {
  const [joinCode, setJoinCode] = useState('')
  const [studentId, setStudentId] = useState('')
  const [msg, setMsg] = useState(null)

  const join = async (e) => {
    e.preventDefault()
    try {
      await api.post('/api/enrollments/join', { joinCode, studentId: Number(studentId) })
      setMsg({ ok: true, text: 'Joined the classroom!' })
      onJoined()
    } catch (err) { setMsg({ ok: false, text: err.message }) }
  }

  return (
    <form onSubmit={join} className="joinbox">
      <strong>Join a classroom by invitation code</strong>
      <input placeholder="6-character code" value={joinCode} maxLength={6} onChange={(e) => setJoinCode(e.target.value)} />
      <input placeholder="Student user ID" type="number" value={studentId} onChange={(e) => setStudentId(e.target.value)} />
      <button type="submit">Join</button>
      {msg && <span className={msg.ok ? 'ok' : 'error inline'}>{msg.text}</span>}
    </form>
  )
}

export default function App() {
  const names = Object.keys(SCREENS)
  const [active, setActive] = useState(names[0])
  const [refresh, setRefresh] = useState(0)
  const screen = SCREENS[active]

  return (
    <div className="app">
      <header>
        <h1>GradeAhead</h1>
        <p>Formative evaluation and early-warning dashboard · Increment 1: core entity CRUD</p>
      </header>
      <nav>
        {names.map((n) => (
          <button key={n} className={n === active ? 'tab active' : 'tab'} onClick={() => setActive(n)}>{n}</button>
        ))}
      </nav>
      {active === 'Enrollments' && <JoinBox onJoined={() => setRefresh((r) => r + 1)} />}
      <CrudPanel key={`${active}-${refresh}`} title={active} path={screen.path} fields={screen.fields} hint={screen.hint} />
    </div>
  )
}
