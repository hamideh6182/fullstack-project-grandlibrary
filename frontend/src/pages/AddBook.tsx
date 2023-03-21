import {BookRequest} from "../models/BookRequest";
import {ChangeEvent, FormEvent, useState} from "react";
import axios from "axios";
import "./AddBook.css"

type AddBookProps = {
    onAddBook: (newBook: BookRequest) => Promise<void>
}

export default function AddBook(props: AddBookProps) {
    const [title, setTitle] = useState<string>("")
    const [author, setAuthor] = useState<string>("")
    const [description, setDescription] = useState<string>("")
    const [copies, setCopies] = useState<number>(0)
    const [category, setCategory] = useState<string>("")
    const [file, setFile] = useState<File | null>(null);
    const [url, setUrl] = useState<string>("");

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

    function handleCategoryChange(event: ChangeEvent<HTMLInputElement>) {
        setCategory(event.target.value)
    }

    function handleImgChange() {
        const payload = new FormData();
        if (!file) {
            return;
        }
        payload.set('file', file);
        axios.post("/api/photos", payload)
            .then(res => {
                setUrl(res.data)
                console.log(res);
            })
            .catch(err => {
                console.error(err);
            })
    }

    const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (event.target.files && event.target.files.length > 0) {
            setFile(event.target.files[0]);
        }
    }

    function formSubmitHandler(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const book: BookRequest = {title, author, description, copies, category, img: url}
        props.onAddBook(book)
            .then(() => {
                setTitle("")
                setAuthor("")
                setDescription("")
                setCopies(0)
                setCategory("")
                setUrl("")
            })
    }

    return (
        <div className={"add-book-bg"}>
            <h1 className={"h1-add-book"}>
                Add a new book
            </h1>
            <div>
                <form className={"add-book"} onSubmit={formSubmitHandler}>
                    <div>
                        <label>Title</label><br/>
                        <input type={"text"} name={"title"} value={title} required={true} onChange={handleTitleChange}/>
                    </div>
                    <div>
                        <label>Author</label><br/>
                        <input type={"text"} name={"author"} value={author} required={true}
                               onChange={handleAuthorChange}/>
                    </div>
                    <div>
                        <label>Category</label><br/>
                        <input type={"text"} name={"category"} value={category} required={true}
                               onChange={handleCategoryChange}/>
                    </div>
                    <div>
                        <label>Description</label><br/>
                        <textarea value={description} onChange={handleDescriptionChange}/>
                    </div>
                    <div>
                        <label>Copies</label><br/>
                        <input type={"number"} value={copies} onChange={handleCopiesChange}/>
                    </div>
                    <label>
                        Upload image:<br/>
                        <input type={"file"} onChange={handleFileChange} accept={"image/jpeg, image/png"}/>
                    </label>
                    <button onClick={handleImgChange}>Upload!</button>
                    <div>
                        <button type={"submit"}>Add Book</button>
                    </div>
                </form>
            </div>
        </div>
    )
}