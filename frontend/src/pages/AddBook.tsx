import {BookRequest} from "../models/BookRequest";
import {ChangeEvent, FormEvent, useState} from "react";

type AddBookProps = {
    onAddBook: (newBook: BookRequest) => Promise<void>
}

export default function AddBook(props: AddBookProps) {
    const [title, setTitle] = useState<string>("")
    const [author, setAuthor] = useState<string>("")
    const [description, setDescription] = useState<string>("")
    const [copies, setCopies] = useState<number>(0)
    const [category, setCategory] = useState<string>("")
    const [img, setImg] = useState<string>("")

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

    function handleImgChange(event: ChangeEvent<HTMLInputElement>) {
        setImg(event.target.value)
    }

    function formSubmitHandler(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        const book: BookRequest = {title, author, description, copies, category, img}
        props.onAddBook(book)
            .then(() => {
                setTitle("")
                setAuthor("")
                setDescription("")
                setCopies(0)
                setCategory("")
                setImg("")
            })
    }

    return (
        <div>
            <div>
                Add a new book
            </div>
            <div>
                <form onSubmit={formSubmitHandler}>
                    <div>
                        <label>Title</label>
                        <input type={"text"} name={"title"} value={title} required={true} onChange={handleTitleChange}/>
                    </div>
                    <div>
                        <label>Author</label>
                        <input type={"text"} name={"author"} value={author} required={true}
                               onChange={handleAuthorChange}/>
                    </div>
                    <div>
                        <label>Category</label>
                        <input type={"text"} name={"category"} value={category} required={true}
                               onChange={handleCategoryChange}/>
                    </div>
                    <div>
                        <label>Description</label>
                        <textarea value={description} onChange={handleDescriptionChange} rows={3}/>
                    </div>
                    <div>
                        <label>Copies</label>
                        <input type={"number"} value={copies} onChange={handleCopiesChange}/>
                    </div>
                    <input value={img} onChange={handleImgChange}/>
                    <div>
                        <button type={"submit"}>Add Book</button>
                    </div>
                </form>
            </div>
        </div>
    )
}