import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.tsx'
import './index.css'
import {NotificationProvider} from "./contexts/NotificationContext.tsx";
import {AuthProvider} from "./contexts/AuthContext.tsx";
import 'bootstrap/dist/css/bootstrap.min.css';
import {LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs";

ReactDOM.createRoot(document.getElementById('root')!).render(
    <React.StrictMode>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <NotificationProvider>
                <AuthProvider>
                    <App/>
                </AuthProvider>
            </NotificationProvider>
        </LocalizationProvider>
    </React.StrictMode>,
)
