import {useEffect, useState} from "react";
import {Book} from "../models/Book";
import axios from "axios";

export default function useBooks() {
    const [books, setBooks] = useState<Book[]>([])

    function loadAllBooks() {
        axios.get("/api/books")
            .then(response => {
                setBooks(response.data)
            })
            .catch(console.error)
    }

    function postNewBook(newBook: Book, file: File) {
        const payload = new FormData();
        if (!file) {
            return;
        }
        payload.append('file', file)
        payload.append("bookRequest", new Blob([JSON.stringify(newBook)], {type: "application/json"}));
        return axios.post("/api/books", payload)
            .then(response => response.data)
            .then(data => setBooks(prevState => [...prevState, data]))
            .catch(console.error)
    }

    function deleteBook(id: string) {
        return axios.delete("/api/books/" + id)
            .then(() => {
                loadAllBooks()
            })
            .catch(console.error)
    }

    function updateBookIncrease(id: string) {
        return axios.put("/api/books/quantity/increase/" + id)
            .then(response => response.data)
            .then(data => setBooks(prevState => {
                return prevState.map(currentBook => {
                    if (currentBook.id === id) {
                        return data
                    }
                    return currentBook
                })
            }))
    }

    function updateBookDecrease(id: string) {
        return axios.put("/api/books/quantity/decrease/" + id)
            .then(response => response.data)
            .then(data => setBooks(prevState => {
                return prevState.map(currentBook => {
                    if (currentBook.id === id) {
                        return data
                    }
                    return currentBook
                })
            }))
    }

    useEffect(() => {
        loadAllBooks()
    }, [])
    return {books, postNewBook, deleteBook, updateBookIncrease, updateBookDecrease}
}

