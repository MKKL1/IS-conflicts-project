import {BrowserRouter, Route, Routes} from "react-router-dom";
import './App.css'
import NavigationBar from "./components/NavigationBar.tsx";
import Notification from "./components/Notification.tsx";
import GuestGuard from "./guards/GuestGuard.tsx";
import AdminGuard from "./guards/AdminGuard.tsx";
import AuthGuard from "./guards/AuthGuard.tsx";
import Register from "./components/Register.tsx";
import Login from "./components/Login.tsx";
import HomePage from "./components/HomePage.tsx";
import {useNotificationContext} from "./contexts/NotificationContext.tsx";
import ImportPage from "./components/ImportPage.tsx";

function App() {
    const {message, variant} = useNotificationContext();
  return (
      <div>
          <BrowserRouter>
              <NavigationBar/>
              <Routes>
                  <Route path='/' Component={HomePage}/>
                  <Route path='/login' element={<GuestGuard>
                      <Login/>
                  </GuestGuard>}/>
                  <Route path='/register' element={<GuestGuard>
                      <Register/>
                  </GuestGuard>}/>
                  <Route path='/import' element={<AdminGuard>
                      <ImportPage/>
                  </AdminGuard>}/>
                  <Route path='/profile' element={<AuthGuard>
                      {/*<Profile/>*/}
                      <></>
                  </AuthGuard>}/>
              </Routes>
          </BrowserRouter>
          <Notification message={message} variant={variant}/>
      </div>
  )
}

export default App
