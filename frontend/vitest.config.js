import { defineConfig } from 'vitest/config';

export default defineConfig({
    test: {
        environment: 'jsdom',
        coverage: {
            provider: 'v8',                     
            reporter: ['text', 'lcov'],          
            all: true,                           
            include: ['src/**/*.{js,jsx,ts,tsx}'], 
            exclude: [
                'node_modules',  
                '**/main.jsx', 
                '**/AccountSettings.jsx',
                '**/AuthWrapper.jsx',
                '**/Oauth2Redirect.jsx',
            ],
        },
    },
});
