import {Book} from "../models/Book";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import Layout from "../components/Layout";
import useAuth from "../hooks/useAuth";
import "./BooksGallery.css"
import {toast} from "react-toastify";

type BookDetailsProps = {
    books: Book[]
    deleteBook: (id: string) => Promise<void>
    updateBookIncrease: (id: string) => Promise<void>
    updateBookDecrease: (id: string) => Promise<void>
    checkoutBook: (uid: string, bid: string) => Promise<void>
    checkoutBookByUser: (uid: string, bid: string) => Promise<void>
}

export default function BookDetails(props: BookDetailsProps) {
    const params = useParams()
    const id = params.id
    const navigate = useNavigate()
    const {isAdmin} = useAuth(false)
    const {user} = useAuth(false)
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
            .catch(console.error)
    }

    function handleDecreaseBookQuantity() {
        props.updateBookDecrease(id || "undefined")
            .catch(console.error)
    }

    function handleCheckoutBook() {

        props.checkoutBook(user?.id || "undefined", id || "undefined")
            .then(() => {
                toast.success('📖 Book is checkout.Enjoy!', {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
            })
            .catch((error) => {
                toast.error('📚 Sorry.You can not loan the Book!', {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
                console.error(error)
            })
    }

    return (
        <Layout>
            <h1 className={"h1-book-gallery"}>Book <span>Details</span></h1>
            <div className={"book-card"}>
                <div className={"book-card-div-two"}>
                    {book.img ? <img src={book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}

                    <div className={"book-card-div-one"}>
                        <h5>
                        {book.author}
                    </h5>
                    <h4>
                        {book.title}
                    </h4>
                    <p>
                        {book.description}
                    </p>
                    <text>
                        Copies : {book.copies}
                    </text>
                    <text>
                        Copies Available : {book.copiesAvailable}
                    </text>
                </div>
            </div>
            <div className={"book-card-div-b"}>
                <button onClick={handleOnBackGalleryButtonClick}>Back To Gallery</button>
                {user ? <button onClick={handleCheckoutBook}>Checkout</button> : null}
                {isAdmin ?
                    <>
                        <button onClick={handleDeleteButton}>Delete</button>
                        <button onClick={handleIncreaseBookQuantity}>Increase Quantity</button>
                        <button onClick={handleDecreaseBookQuantity}>Decrease Quantity</button>
                    </>
                    :
                    null
                }
            </div>
        </div>
        </Layout>
    )
}

