import {Link, NavLink, useLocation} from "react-router-dom";
import "./Navigation.css"
import axios from "axios";
import useAuth from "../hooks/useAuth";

export default function Navigation() {
    const location = useLocation()
    const user = useAuth(false)

    function handleLogOutClick() {
        axios.post("/api/users/logout").then(() => {
            window.sessionStorage.setItem(
                "signInRedirect",
                location.pathname || "/"
            );
            window.location.href = "/sign-in";
        });
    }

    return (
        <div className={"navigation"}>
            <div className={"navigation-block"}>
                <h5 className={"logo"}>grand <span>library</span></h5>
            </div>
            <div className={"navigation-block"}>
                <NavLink to={"/"}>Home</NavLink>
                <NavLink to={"/books"}>Gallery</NavLink>
                {user && <NavLink to={"/books/add"}>Add Book</NavLink>}
            </div>
            <div className={"navigation-block"}>
                {user ?
                    <Link to={"#"} onClick={handleLogOutClick}>Sign out</Link>
                    :
                    <>
                        <NavLink to={"/sign-up"}>Sign up</NavLink>
                        <NavLink to={"/sign-in"}>Sign in</NavLink>
                    </>
                }

            </div>
        </div>
    )
}