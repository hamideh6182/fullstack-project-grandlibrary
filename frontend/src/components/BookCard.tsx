import {Book} from "../models/Book";
import "./BookCard.css"

type BookCardProps = {
    book: Book
}
export default function BookCard(props: BookCardProps) {
    return (
        <div className={"book-card"}>
            <div>
                {props.book.img ? <img src={props.book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}
            </div>
            <div>
                <h5>
                    {props.book.author}
                </h5>
                <h4>
                    {props.book.title}
                </h4>
                <p>
                    {props.book.description}
                </p>
            </div>

        </div>
    )
}