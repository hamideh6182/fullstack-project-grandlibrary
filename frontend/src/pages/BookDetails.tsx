import {Book} from "../models/Book";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";

type BookDetailsProps = {
    books: Book[]
    deleteBook: (id: string) => Promise<void>
    updateBookIncrease: (id: string) => Promise<void>
    updateBookDecrease: (id: string) => Promise<void>
}

export default function BookDetails(props: BookDetailsProps) {
    const params = useParams()
    const id = params.id
    const navigate = useNavigate()
    const [book, setBook] = useState<Book | undefined>()
    useEffect(() => {
        const filteredBook = props.books.find(book => book.id === id);
        if (filteredBook)
            setBook(filteredBook)
    }, [id, props.books])
    if (!book) {
        return (
            <h3>Sorry, no book with id {id} found</h3>
        )
    }

    function handleOnBackGalleryButtonClick() {
        navigate("/Books")
    }

    function handleDeleteButton() {
        props.deleteBook(id || "undefined")
            .then(() => navigate("/Books"))
            .catch(console.error)
    }

    function handleIncreaseBookQuantity() {
        props.updateBookIncrease(id || "undefined")
            .then(() => navigate("/Books"))
            .catch(console.error)
    }

    function handleDecreaseBookQuantity() {
        props.updateBookDecrease(id || "undefined")
            .then(() => navigate("/Books"))
            .catch(console.error)
    }

    return (
        <div className={"book-card"}>
            <div>
                {book.img ? <img src={book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}
            </div>
            <div>
                <h5>
                    {book.author}
                </h5>
                <h4>
                    {book.title}
                </h4>
                <p>
                    {book.description}
                </p>
            </div>
            <div>
                <button onClick={handleOnBackGalleryButtonClick}>Back To Gallery</button>
            </div>
            <div>
                <button onClick={handleDeleteButton}>Delete</button>
            </div>
            <div>
                <button onClick={handleIncreaseBookQuantity}>Increase Quantity</button>
            </div>
            <div>
                <button onClick={handleDecreaseBookQuantity}>Decrease Quantity</button>
            </div>
        </div>
    )
}

