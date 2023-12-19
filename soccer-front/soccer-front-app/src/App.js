import React, {Suspense} from 'react';
import {BrowserRouter,Routes, Route} from 'react-router-dom';
import Main from './components/main/Main';
import Team from './components/main/Team';
import './App.css';

const App = () => {
  return (
    <Suspense fallback={<div>Loading...</div>}>
    <BrowserRouter>
    <Routes>
      <Route path='/' element={<Main/>}></Route>
      <Route path='team' element={<Team/>}></Route>
    </Routes>
    </BrowserRouter>
    </Suspense>
  );
}

export default App;
