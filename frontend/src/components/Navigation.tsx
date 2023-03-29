import {NavLink} from "react-router-dom";
import "./Navigation.css"

export default function Navigation(){
    return(
        <div className={"navigation"}>
            <div className={"navigation-block"}>
                <h5 className={"logo"}>grand <span>library</span></h5>
            </div>
            <div className={"navigation-block"}>
                <NavLink to={"/"}>Home</NavLink>
                <NavLink to={"/books"}>Gallery</NavLink>
                <NavLink to={"/books/add"}>Add Book</NavLink>
            </div>
            <div className={"navigation-block"}>
                <NavLink to={"/sign-up"}>Sign up</NavLink>
                <NavLink to={"#"}>Sign in</NavLink>
            </div>
        </div>
    )
}