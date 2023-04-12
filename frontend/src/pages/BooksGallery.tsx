import {Book} from "../models/Book";
import BookCard from "../components/BookCard";
import "./BooksGallery.css"
import Layout from "../components/Layout";
import React, {ChangeEvent, useState} from "react";

type Props = {
    books: Book[]
}
export default function BooksGallery(props: Props) {
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("");
    const [currentPage, setCurrentPage] = useState(0);
    const booksPerPage = 2;

    const filteredBooks = props.books
        .filter(
            (book) =>
                book.title.toLowerCase().includes(searchTerm.toLowerCase()) &&
                (selectedCategory === "" || book.category === selectedCategory)
        );

    const totalPages = Math.ceil(filteredBooks.length / booksPerPage);

    const bookCards = filteredBooks
        .slice(currentPage * booksPerPage, (currentPage + 1) * booksPerPage)
        .map((book: Book) => {
            return <BookCard book={book} key={book.id}/>;
        });

    function handleSearchInput(event: ChangeEvent<HTMLInputElement>) {
        setSearchTerm(event.target.value);
        setCurrentPage(0);
    }

    function handleCategorySelect(event: ChangeEvent<HTMLSelectElement>) {
        setSelectedCategory(event.target.value);
        setCurrentPage(0);
    }

    function handlePrevPage() {
        if (currentPage > 0) {
            setCurrentPage(currentPage - 1);
        }
    }

    function handleNextPage() {
        if (currentPage < totalPages - 1) {
            setCurrentPage(currentPage + 1);
        }
    }

    return (
        <Layout>
            <div className={"books-gallery"}>
                <h1 className={"h1-book-gallery"}>
                    Book <span>Gallery</span>
                </h1>
                <div>
                    <input className={"book-gallery-input"} type="text" placeholder="Search by title" value={searchTerm}
                           onChange={handleSearchInput}/>
                    <select className={"book-gallery-select"} value={selectedCategory} onChange={handleCategorySelect}>
                        <option value="">All Categories</option>
                        <option value={"sci-fiction"}>Sci-Fiction</option>
                        <option value={"Fantasy"}>Fantasy</option>
                        <option value={"health"}>Health</option>
                        <option value={"development"}>Development</option>
                        <option value={"programming"}>Programming</option>
                    </select>
                </div>
                <div>{bookCards.length > 0
                    ? bookCards
                    : "Can't find what you are looking for?"}
                </div>
                <div>
                    <span className={"book-gallery-span"}>
                        Page {currentPage + 1} of {totalPages}
                    </span>
                </div>
                <div className={"pagination"}>
                    <button onClick={handlePrevPage} disabled={currentPage === 0}>
                        Prev
                    </button>
                    <button onClick={handleNextPage} disabled={currentPage === totalPages - 1}>
                        Next
                    </button>
                </div>
            </div>
        </Layout>
    );
}