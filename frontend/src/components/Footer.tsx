import "./Footer.css"

export default function Footer() {
    const today = new Date()
    return (
        <footer className={"footer"}>
            copyright &copy;{today.getFullYear()}
        </footer>
    )
}