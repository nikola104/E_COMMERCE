import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  css: {
    // Disable CSS file processing and automatic importing
    preprocessorOptions: {
      // Disable CSS Modules
      modules: {
        generateScopedName: false
      },
      // Exclude CSS files from being treated as modules
      localsConvention: 'noModule'
    }
  }
});
