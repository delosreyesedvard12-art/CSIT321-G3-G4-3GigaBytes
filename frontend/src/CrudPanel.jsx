import { useEffect, useState } from 'react'
import { api } from './api.js'

// One reusable Create / Read / Update / Delete screen, driven by a field list.
export default function CrudPanel({ title, path, fields, hint }) {
  const empty = Object.fromEntries(fields.map((f) => [f.name, f.default ?? '']))
  const [rows, setRows] = useState([])
  const [form, setForm] = useState(empty)
  const [editingId, setEditingId] = useState(null)
  const [error, setError] = useState('')

  const load = () => api.list(path).then(setRows).catch((e) => setError(e.message))
  useEffect(() => { setForm(empty); setEditingId(null); setError(''); load() }, [path])

  const change = (name, value) => setForm((f) => ({ ...f, [name]: value }))

  const toPayload = () => {
    const out = {}
    for (const f of fields) {
      const v = form[f.name]
      if (v === '' || v === undefined) { out[f.name] = null; continue }
      out[f.name] = f.type === 'number' ? Number(v) : v
    }
    return out
  }

  const submit = async (e) => {
    e.preventDefault()
    setError('')
    try {
      if (editingId) await api.update(path, editingId, toPayload())
      else await api.create(path, toPayload())
      setForm(empty); setEditingId(null); load()
    } catch (err) { setError(err.message) }
  }

  const edit = (row) => {
    setEditingId(row.id)
    setForm(Object.fromEntries(fields.map((f) => {
      let v = row[f.name] ?? ''
      if (f.type === 'datetime-local' && typeof v === 'string') v = v.slice(0, 16)
      if (f.secret) v = ''
      return [f.name, v]
    })))
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }

  const remove = async (row) => {
    if (!window.confirm(`Delete record #${row.id}?`)) return
    try { await api.remove(path, row.id); load() } catch (err) { setError(err.message) }
  }

  const show = (row, f) => {
    if (f.secret) return '••••'
    const v = row[f.name]
    if (typeof v === 'string' && f.type === 'datetime-local') return v.replace('T', ' ').slice(0, 16)
    return v ?? ''
  }

  return (
    <section className="panel">
      <h2>{title}</h2>
      {hint && <p className="hint">{hint}</p>}
      {error && <div className="error">{error}</div>}

      <form onSubmit={submit} className="form">
        {fields.map((f) => (
          <label key={f.name}>
            <span>{f.label}</span>
            {f.options ? (
              <select value={form[f.name]} onChange={(e) => change(f.name, e.target.value)}>
                <option value="">-- choose --</option>
                {f.options.map((o) => <option key={o} value={o}>{o}</option>)}
              </select>
            ) : (
              <input
                type={f.secret ? 'password' : f.type || 'text'}
                value={form[f.name]}
                placeholder={f.placeholder || ''}
                onChange={(e) => change(f.name, e.target.value)}
              />
            )}
          </label>
        ))}
        <div className="actions">
          <button type="submit">{editingId ? `Save #${editingId}` : 'Add'}</button>
          {editingId && (
            <button type="button" className="ghost" onClick={() => { setEditingId(null); setForm(empty) }}>Cancel</button>
          )}
        </div>
      </form>

      <div className="tablewrap">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              {fields.map((f) => <th key={f.name}>{f.label}</th>)}
              <th></th>
            </tr>
          </thead>
          <tbody>
            {rows.length === 0 && <tr><td colSpan={fields.length + 2} className="empty">No records yet.</td></tr>}
            {rows.map((r) => (
              <tr key={r.id}>
                <td>{r.id}</td>
                {fields.map((f) => <td key={f.name}>{String(show(r, f))}</td>)}
                <td className="rowactions">
                  <button className="ghost" onClick={() => edit(r)}>Edit</button>
                  <button className="danger" onClick={() => remove(r)}>Delete</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </section>
  )
}
