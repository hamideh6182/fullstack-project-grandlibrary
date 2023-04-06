import React from 'react';
import './App.css';
import {Route, Routes} from "react-router-dom";
import BooksGallery from "./pages/BooksGallery";
import useBooks from "./hooks/useBooks";
import AddBook from "./pages/AddBook";
import BookDetails from "./pages/BookDetails";
import Home from "./pages/Home";
import axios from "axios";
import Cookies from "js-cookie";
import SignUp from "./pages/SignUp";
import SignIn from "./pages/SignIn";
import {ToastContainer} from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

axios.interceptors.request.use(function (config) {
    return fetch("/api/csrf").then(() => {
        config.headers["X-XSRF-TOKEN"] = Cookies.get("XSRF-TOKEN");
        return config;
    });
}, function (error) {
    return Promise.reject(error);
});

function App() {
    const {
        books,
        postNewBook,
        deleteBook,
        updateBookIncrease,
        updateBookDecrease,
        checkoutBook,
        checkoutBookByUser
    } = useBooks()
    return (
        <div className="App">
            <Routes>
                <Route path={"/"} element={<Home/>}/>
                <Route path={"/sign-up"} element={<SignUp/>}/>
                <Route path={"/sign-in"} element={<SignIn/>}/>
                <Route path={"/books"} element={<BooksGallery books={books}/>}/>
                <Route path={"/books/add"} element={<AddBook onAddBook={postNewBook}/>}/>
                <Route path={"/books/:id"} element={<BookDetails books={books} deleteBook={deleteBook}
                                                                 updateBookIncrease={updateBookIncrease}
                                                                 updateBookDecrease={updateBookDecrease}
                                                                 checkoutBook={checkoutBook}
                                                                 checkoutBookByUser={checkoutBookByUser}/>}/>
            </Routes>
            <ToastContainer
                position="top-right"
                autoClose={5000}
                hideProgressBar={false}
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
                theme="light"
            />
            <ToastContainer/>
        </div>
  );
}

export default App;
