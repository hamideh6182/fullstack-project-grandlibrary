import {useEffect, useState} from "react";
import {Book} from "../models/Book";
import axios from "axios";
import {BookRequest} from "../models/BookRequest";

export default function useBooks() {
    const [books, setBooks] = useState<Book[]>([])

    function loadAllBooks() {
        axios.get("/api/books")
            .then(response => {
                setBooks(response.data)
            })
            .catch(console.error)
    }

    function postNewBook(newBook: BookRequest) {
        return axios.post("/api/books", newBook)
            .then(response => response.data)
            .then(data => setBooks(prevState => [...prevState, data]))
    }

    useEffect(() => {
        loadAllBooks()
    }, [])
    return {books, postNewBook}
}

