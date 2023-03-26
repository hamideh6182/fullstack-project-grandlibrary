import {Book} from "../models/Book";
import BookCard from "../components/BookCard";
import "./BooksGallery.css"
import Layout from "../components/Layout";

type Props = {
    books: Book[]
}
export default function BooksGallery(props: Props) {
    const bookCards = props.books.map((book: Book) => {
        return <BookCard book={book} key={book.id}/>
    })

    return (
        <Layout>
        <div className={"books-gallery"}>
            <h1 className={"h1-book-gallery"}>Book <span>Gallery</span></h1>
            <div>
                {bookCards.length > 0 ? bookCards : "Can't find what you are looking for?"}
            </div>
        </div>
        </Layout>
    )
}