import {useAuthContext} from "../contexts/AuthContext.tsx";
import {Navigate} from "react-router-dom";

// @ts-ignore
export default function GuestGuard({children}){
    const {isAuth} = useAuthContext();

    return !isAuth ? children : <Navigate to="/"/>
}