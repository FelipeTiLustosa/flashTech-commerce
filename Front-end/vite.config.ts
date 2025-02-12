import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      '/oauth2': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/products': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/categories': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      },
      '/users': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      }
    }
  }
})
