import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BooksGallery from "./pages/BooksGallery";
import useBooks from "./hooks/useBooks";
import AddBook from "./pages/AddBook";
import BookDetails from "./pages/BookDetails";

function App() {
    const {books, postNewBook, deleteBook, updateBookIncrease, updateBookDecrease} = useBooks()
  return (
      <div className="App">
          <Routes>
              <Route path={"/books"} element={<BooksGallery books={books}/>}/>
              <Route path={"/books/add"} element={<AddBook onAddBook={postNewBook}/>}/>
              <Route path={"/books/:id"} element={<BookDetails books={books} deleteBook={deleteBook}
                                                               updateBookIncrease={updateBookIncrease}
                                                               updateBookDecrease={updateBookDecrease}/>}/>
          </Routes>
      </div>
  );
}

export default App;
