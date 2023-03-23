import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BooksGallery from "./pages/BooksGallery";
import useBooks from "./hooks/useBooks";
import AddBook from "./pages/AddBook";
import BookDetails from "./pages/BookDetails";

function App() {
    const {books, postNewBook, deleteBook} = useBooks()
  return (
      <div className="App">
          <Routes>
              <Route path={"/Books"} element={<BooksGallery books={books}/>}/>
              <Route path={"/Books/add"} element={<AddBook onAddBook={postNewBook}/>}/>
              <Route path={"/Books/:id"} element={<BookDetails books={books} deleteBook={deleteBook}/>}/>
          </Routes>
      </div>
  );
}

export default App;
