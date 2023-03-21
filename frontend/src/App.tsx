import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BooksGallery from "./pages/BooksGallery";
import useBooks from "./hooks/useBooks";
import AddBook from "./pages/AddBook";

function App() {
    const {books, postNewBook} = useBooks()
  return (
      <div className="App">
          <Routes>
              <Route path={"/Books"} element={<BooksGallery books={books}/>}/>
              <Route path={"/Books/add"} element={<AddBook onAddBook={postNewBook}/>}/>
          </Routes>
      </div>
  );
}

export default App;
