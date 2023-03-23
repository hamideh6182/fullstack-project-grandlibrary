import {Book} from "../models/Book";
import BookCard from "../components/BookCard";
import "./BooksGallery.css"

type Props = {
    books: Book[]
}
export default function BooksGallery(props: Props) {
    const bookCards = props.books.map((book: Book) => {
        return <BookCard book={book} key={book.id}/>
    })

    return (
        <div className={"books-gallery"}>
            <div>
                {bookCards.length > 0 ? bookCards : "Can't find what you are looking for?"}
            </div>
        </div>
    )
}