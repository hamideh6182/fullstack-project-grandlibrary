import Layout from "../components/Layout";
import BookCarousel from "../components/BookCarousel";
import "./Home.css"

export default function Home() {
    return (
        <Layout>
            <div>
                <BookCarousel/>
            </div>
        </Layout>
    )
}