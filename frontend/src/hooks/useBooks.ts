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

    useEffect(() => {
        loadAllBooks()
    }, [])
}

