import {Book} from "../models/Book";

type Props = {
    book: Book
}
export default function BookCard(props: Props) {
    return (
        <div>
            <div className={"book-card"}>
                {props.book.img ? <img src={props.book.img} alt="Book"/> : <img alt="Book"/>}
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