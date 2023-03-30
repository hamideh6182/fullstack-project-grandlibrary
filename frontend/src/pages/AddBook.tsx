import {BookRequest} from "../models/BookRequest";
import {ChangeEvent, FormEvent, useState} from "react";
import "./AddBook.css"
import {Book} from "../models/Book";
import {useNavigate} from "react-router-dom";
import Layout from "../components/Layout";
import useAuth from "../hooks/useAuth";

type AddBookProps = {
    onAddBook: (newBook: Book, file: File) => void
}

export default function AddBook(props: AddBookProps) {
    const [title, setTitle] = useState<string>("")
    const [author, setAuthor] = useState<string>("")
    const [description, setDescription] = useState<string>("")
    const [copies, setCopies] = useState<number>(0)
    const [category, setCategory] = useState<string>("")
    const [file, setFile] = useState<File | null>(null);
    const navigate = useNavigate()
    const {isAdmin} = useAuth(false)

    function handleTitleChange(event: ChangeEvent<HTMLInputElement>) {
        setTitle(event.target.value)
    }

    function handleAuthorChange(event: ChangeEvent<HTMLInputElement>) {
        setAuthor(event.target.value)
    }

    function handleDescriptionChange(event: ChangeEvent<HTMLTextAreaElement>) {
        setDescription(event.target.value)
    }

    function handleCopiesChange(event: ChangeEvent<HTMLInputElement>) {
        setCopies(Number(event.target.value))
    }

    function handleCategoryChange(event: ChangeEvent<HTMLSelectElement>) {
        setCategory(event.target.value)
    }

    const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            setFile(event.target.files[0]);
        }
    }

    function formSubmitHandler(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const payload = new FormData();
        if (!file) {
            return;
        }
        payload.set('file', file);
        const book: BookRequest = {title, author, description, copies, category}
        if (file && book) {
            props.onAddBook(book, file)
            navigate("/books")
        }
    }

    return !isAdmin ? null : (
        <Layout>
            <div className={"add-book-bg"}>
                <h1 className={"h1-add-book"}>
                    Add a <span>new book</span>
                </h1>
                <div className={"add-book"}>
                    <form onSubmit={formSubmitHandler}>
                        <div>
                            <label>Title</label><br/>
                            <input type={"text"} name={"title"} value={title} required={true}
                                   onChange={handleTitleChange}/>
                    </div>
                    <div>
                        <label>Author</label><br/>
                        <input type={"text"} name={"author"} value={author} required={true}
                               onChange={handleAuthorChange}/>
                    </div>
                    <div>
                        <label>Category</label><br/>
                        <select name={"category"} value={category} required={true} onChange={handleCategoryChange}>
                            <option value={"sci-fiction"}>Sci-Fiction</option>
                            <option value={"Fantasy"}>Fantasy</option>
                            <option value={"health"}>Health</option>
                            <option value={"development"}>Development</option>
                            <option value={"programming"}>Programming</option>
                        </select>
                    </div>
                    <div>
                        <label>Description</label><br/>
                        <textarea value={description} onChange={handleDescriptionChange}/>
                    </div>
                    <div>
                        <label>Copies</label><br/>
                        <input type={"number"} value={copies} onChange={handleCopiesChange}/>
                    </div>
                    <div>
                    <label>
                        Upload image:<br/>
                        <input type={"file"} onChange={handleFileChange} accept={"image/jpeg, image/png"}/>
                    </label>
                    </div>
                        <button type={"submit"}>Add Book</button>
                </form>
            </div>
        </div>
        </Layout>
    )
}