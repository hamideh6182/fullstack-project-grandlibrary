import {Book} from "../models/Book";
import BookCard from "../components/BookCard";
import "./BooksGallery.css"
import Layout from "../components/Layout";
import {ChangeEvent, useState} from "react";

type Props = {
    books: Book[]
}
export default function BooksGallery(props: Props) {
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedCategory, setSelectedCategory] = useState("");

    const bookCards = props.books
        .filter(
            (book) =>
                book.title.toLowerCase().includes(searchTerm.toLowerCase()) &&
                (selectedCategory === "" || book.category === selectedCategory)
        )
        .map((book: Book) => {
            return <BookCard book={book} key={book.id}/>;
        });

    function handleSearchInput(event: ChangeEvent<HTMLInputElement>) {
        setSearchTerm(event.target.value);
    }

    function handleCategorySelect(event: ChangeEvent<HTMLSelectElement>) {
        setSelectedCategory(event.target.value);
    }

    return (
        <Layout>
            <div className={"books-gallery"}>
                <h1 className={"h1-book-gallery"}>
                    Book <span>Gallery</span>
                </h1>
                <div>
                    <input type="text" placeholder="Search by title" value={searchTerm} onChange={handleSearchInput}/>
                    <select value={selectedCategory} onChange={handleCategorySelect}>
                        <option value="">All Categories</option>
                        <option value={"sci-fiction"}>Sci-Fiction</option>
                        <option value={"Fantasy"}>Fantasy</option>
                        <option value={"health"}>Health</option>
                        <option value={"development"}>Development</option>
                        <option value={"programming"}>Programming</option>
                    </select>
                </div>
                <div>{bookCards.length > 0 ? bookCards : "Can't find what you are looking for?"}</div>
            </div>
        </Layout>
    );
}