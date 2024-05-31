import {useAuthContext} from "../contexts/AuthContext.tsx";
import {Navigate} from "react-router-dom";
import {Role} from "../models/Role.ts";

// @ts-ignore
export default function AdminGuard({children}){
    const {isAuth, user} = useAuthContext();
    const isAdmin = user.role == Role.ADMIN

    return isAuth && isAdmin ? children : <Navigate to="/login"/>
}