import Layout from "../components/Layout";
import BookCarousel from "../components/BookCarousel";
import "./Home.css"
import {useNavigate} from "react-router-dom";

export default function Home() {
    const navigate = useNavigate()

    function handleExploreNowButtonClick() {
        navigate("/Books")
    }

    return (
        <Layout>
            <div className={"home-div"}>
                <h1 className={"home-div-h1"}>What Book Are<br/> You Looking For ?</h1>
                <p className={"home-div-p"}>Not Sure What To Read Next ?<br/> Explore Our Gallery</p>
                <button className={"home-div-button"} onClick={handleExploreNowButtonClick}>Explore Now</button>
            </div>
            <div>
                <BookCarousel/>
            </div>
        </Layout>
    )
}