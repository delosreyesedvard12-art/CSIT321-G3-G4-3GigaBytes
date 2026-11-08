# React + Vite

## Run locally

Start the Spring Boot backend from the repository root in one terminal:

```shell
cd backend
./mvnw spring-boot:run
```

On Windows, run `mvnw.cmd spring-boot:run`. The backend requires JDK 25 and
listens on `http://localhost:8080`.

In another terminal, start the frontend:

```shell
cd frontend
npm run dev
```

The Vite development server proxies `/api` requests to the backend. To run the
backend tests, use `./mvnw clean test` from `backend/` (or `mvnw.cmd clean test`
on Windows).

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Oxc](https://oxc.rs)
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/)

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.
