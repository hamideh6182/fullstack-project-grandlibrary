import {Book} from "../models/Book";
import "./BookCard.css"
import {useNavigate} from "react-router-dom";

type BookCardProps = {
    book: Book
}
export default function BookCard(props: BookCardProps) {
    const navigate = useNavigate()

    function HandleOnDetailsButtonClick() {
        navigate("/Books/" + props.book.id)
    }

    return (
        <div className={"book-card"}>
            <div>
                {props.book.img ? <img src={props.book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}
                <text>Copies :</text>
                {props.book.copies}<br/>
                <text>Copies Available :</text>
                {props.book.copiesAvailable}
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
            <div>
                <button onClick={HandleOnDetailsButtonClick}>Details</button>
            </div>
        </div>
    )
}