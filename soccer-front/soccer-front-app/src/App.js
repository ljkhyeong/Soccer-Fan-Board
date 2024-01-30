import React, { Suspense } from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Main from './components/main/Main';
import Team from './components/main/Team';
import './App.css';
import { AuthProvider } from './auth/AuthContext';

const App = () => {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <AuthProvider>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Main />} />
                        <Route path="team/:teamCode/*" element={<Team />} />
                    </Routes>
                </BrowserRouter>
            </AuthProvider>
        </Suspense>
    );
};

export default App;
