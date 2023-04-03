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
        <div>
            <div className={"book-card"}>
                <div className={"book-card-div-two"}>
                    {props.book.img ? <img src={props.book.img} alt="Book"/> : <img src={"/book.jpg"} alt="Book"/>}
                    <div className={"book-card-div-one"}>
                        <h5>
                            {props.book.author}
                        </h5>
                        <h4>
                            {props.book.title}
                        </h4>
                        <p>
                            {props.book.description}
                        </p>
                        <text>
                            Copies :{props.book.copies}
                        </text>
                        <text>
                            Copies Available :{props.book.copiesAvailable}
                        </text>
                    </div>
                </div>
                <div className={"book-card-div-b"}>
                    <button onClick={HandleOnDetailsButtonClick}>Details</button>
                </div>
            </div>
        </div>
    )
}