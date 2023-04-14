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
        <div className={"book-card-container"}>
            <div className={"book-card"}>
                <div className={"book-card-details"}>
                    {props.book.img ? <img src={props.book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}
                    <div>
                        <h6>
                            {props.book.author}
                        </h6>
                        <h4>
                            {props.book.title}
                        </h4>
                    </div>
                    <div>
                        <button onClick={HandleOnDetailsButtonClick}>Details</button>
                    </div>
                </div>
            </div>
        </div>
    )
}