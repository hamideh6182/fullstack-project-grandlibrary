import {Book} from "../models/Book";
import {useNavigate, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import Layout from "../components/Layout";
import useAuth from "../hooks/useAuth";
import "./BooksGallery.css"
import {toast} from "react-toastify";
import {confirmAlert} from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';


type BookDetailsProps = {
    books: Book[]
    deleteBook: (id: string) => Promise<void>
    updateBookIncrease: (id: string) => Promise<void>
    updateBookDecrease: (id: string) => Promise<void>
    checkoutBook: (uid: string, bid: string) => Promise<void>
    checkoutBookByUser: (uid: string, bid: string) => Promise<void>
    returnBook: (uid: string, bid: string) => Promise<void>
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
        confirmAlert({
            title: 'Confirm Delete',
            message: 'Are you sure you want to delete this book?',
            buttons: [
                {
                    label: 'Yes',
                    onClick: () => {
                        props.deleteBook(id || "undefined")
                            .then(() => {
                                navigate("/Books");
                                createToastNotification(true, "📕 Book deleted successfully!");
                            })
                            .catch(console.error)
                    }
                },
                {
                    label: 'No',
                    onClick: () => {
                    }
                }
            ]
        });
    }

    function handleIncreaseBookQuantity() {
        props.updateBookIncrease(id || "undefined")
            .catch(console.error)
    }

    function handleDecreaseBookQuantity() {
        props.updateBookDecrease(id || "undefined")
            .catch(console.error)
    }

    function createToastNotification(success: any, message: any) {
        toast[success ? "success" : "error"](message, {
            position: "top-right",
            autoClose: 5000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
        });
    }

    function handleCheckoutBook() {
        props.checkoutBook(user?.id || "undefined", id || "undefined")
            .then(() => {
                createToastNotification(true, '📖 Book is checkout.Enjoy!');
            })
            .catch((error) => {
                createToastNotification(false, '📚 Sorry.You can not loan the Book!');
                console.error(error)
            })
    }

    function handleReturnBook() {
        props.returnBook(user?.id || "undefined", id || "undefined")
            .then(() => {
                createToastNotification(true, '📕 The book has been returned.Thank you');
            })
            .catch((error) => {
                createToastNotification(false, '📚 Book does not exist or not checked out by user!');
                console.error(error)
            })
    }

    return (
        <Layout>
            <h1 className={"h1-book-gallery"}>Book <span>Details</span></h1>
            <div className={"book-card"}>
                <div className={"book-card-details"}>
                    {book.img ? <img src={book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}
                    <div>
                        <h6>
                            {book.author}
                        </h6>
                        <h4>
                            {book.title}
                        </h4>
                        <p>
                            {book.description}
                        </p>
                        <text>
                            Copies : {book.copies}
                        </text>
                        <br/>
                        <text>
                            Copies Available : {book.copiesAvailable}
                        </text>
                        <br/>
                        <br/>
                    </div>
                    <div>
                        <button onClick={handleOnBackGalleryButtonClick}>Back To Gallery</button>
                        {user ? <button onClick={handleCheckoutBook}>Checkout</button> : null}
                        {user ? <button onClick={handleReturnBook}>Return Book</button> : null}
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
        </div>
        </Layout>
    )
}