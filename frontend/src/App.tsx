import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BooksGallery from "./pages/BooksGallery";
import useBooks from "./hooks/useBooks";

function App() {
  const {books} = useBooks()
  return (
      <div className="App">
        <Routes>
          <Route path={"/Books"} element={<BooksGallery books={books}/>}/>
        </Routes>
      </div>
  );
}

export default App;
