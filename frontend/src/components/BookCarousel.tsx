import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";
import useBooks from "../hooks/useBooks";
import "./BookCarousel.css"

export default function BookCarousel() {
    const {books} = useBooks();
    const responsive = {
        superLargeDesktop: {
            breakpoint: {max: 4000, min: 3000},
            items: 5
        },
        desktop: {
            breakpoint: {max: 3000, min: 1024},
            items: 3,
            slidesToSlide: 3
        },
        tablet: {
            breakpoint: {max: 1024, min: 464},
            items: 2,
            slidesToSlide: 2
        },
        mobile: {
            breakpoint: {max: 464, min: 0},
            items: 1,
            slidesToSlide: 1
        }
    };
    const book = books.map(book => (
        <img className={"book-carousel-img"} src={book.img} alt={"book"}/>
    ))
    return (
        <div className={"book-carousel-div"}>
            <Carousel swipeable={false}
                      draggable={false}
                      showDots={true}
                      responsive={responsive}
                      dotListClass="custom-dot-list-style">
                {book}
            </Carousel>
        </div>
    )
}