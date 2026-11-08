// Tiny fetch wrapper. Vite proxies /api to the Spring Boot backend (see vite.config.js).
async function request(method, url, body) {
  const res = await fetch(url, {
    method,
    headers: body ? { 'Content-Type': 'application/json' } : undefined,
    body: body ? JSON.stringify(body) : undefined,
  })
  if (res.status === 204) return null
  let data = null
  try {
    data = await res.json()
  } catch {
    if (res.ok) throw new Error('The server returned an invalid JSON response.')
  }
  if (!res.ok) {
    const details = Array.isArray(data?.errors)
      ? data.errors.map((e) => `${e.field}: ${e.defaultMessage}`).join(', ')
      : ''
    throw new Error(details || data?.message || `Request failed (${res.status})`)
  }
  return data
}

export const api = {
  list: (path) => request('GET', path),
  create: (path, body) => request('POST', path, body),
  update: (path, id, body) => request('PUT', `${path}/${id}`, body),
  remove: (path, id) => request('DELETE', `${path}/${id}`),
  post: (path, body) => request('POST', path, body),
}
