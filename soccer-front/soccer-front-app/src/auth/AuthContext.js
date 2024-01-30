import { createContext, useContext, useEffect, useState } from 'react';
import { axiosInstance, getAccessToken } from '../service/ApiService';

const AuthContext = createContext(null);

export const useAuth = () => useContext(AuthContext);

export const AuthProvider = ({ children }) => {
    const [isLogin, setIsLogin] = useState(false);
    const [loginId, setLoginId] = useState('');

    useEffect(() => {
        if (!getAccessToken()) {
            axiosInstance
                .post('/auth/refresh', {})
                .then(() => {
                    if (getAccessToken()) {
                        setIsLogin(true);
                    } else {
                        setIsLogin(false);
                    }
                })
                .catch((e) => {
                    console.log(e);
                });
        } else {
            setIsLogin(true);
        }
    }, [isLogin]);

    return (
        <AuthContext.Provider value={{ isLogin, setIsLogin, loginId, setLoginId }}>
            {children}
        </AuthContext.Provider>
    );
};
