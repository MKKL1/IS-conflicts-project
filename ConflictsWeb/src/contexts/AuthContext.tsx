import React, {createContext, useContext, useEffect, useState} from 'react';
import {User} from "../models/User.ts";
import {Role} from "../models/Role.ts";

type AuthContextType = {
    token: string;
    isAuth: boolean;
    user: User;
    updateToken: (accessToken: string) => void;
    resetToken: () => void;
};

const AuthContext = createContext<AuthContextType>({
    token: '',
    isAuth: false,
    user: new User("",Role.USER),
    updateToken: () => {},
    resetToken: () => {}
});

export const AuthProvider: React.FC<{children: React.ReactNode}> = ({ children }) => {
    const [token, setToken] = useState<string>('');
    const [isAuth, setIsAuth] = useState<boolean>(false);
    const [user, setUser] = useState<User>(new User("",Role.USER));

    // TODO check if token is expired before returning it to components
    // data provided in iat and exp field of accessToken

    // checking if token exists already in localstorage
    // while starting app
    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token !== null && token !== '') {
            updateToken(token);
        }
    }, []);

    const updateToken = (accessToken: string) => {
        console.log("updating token");
        setIsAuth(true);
        setToken(accessToken);
        const parts = accessToken.split('.');
        const decodedPayload = JSON.parse(atob(parts[1]));
        setUser(new User(
            decodedPayload.sub,
            Role[decodedPayload.role as keyof typeof Role]
        ));

        localStorage.setItem("token", accessToken);
    };

    const resetToken = () => {
        setIsAuth(false);
        setToken("");
        setUser(new User("",Role.USER));
        localStorage.setItem('token', '');
    }

    return (
        <AuthContext.Provider value={{ token, updateToken, isAuth, resetToken, user }}>
        {children}
        </AuthContext.Provider>
    );
};

export const useAuthContext = () => useContext(AuthContext);