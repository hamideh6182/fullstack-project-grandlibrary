import {NavLink} from "react-router-dom";
import "./Navigation.css"
export default function Navigation(){
    return(
        <div className={"navigation"}>
          <h5 className={"logo"}>grand <span>library</span></h5>
        <div>
            <nav>
                <ul>
                    <li>
                        <NavLink to={"/"}>Home</NavLink>
                        <NavLink to={"/books"}>Gallery</NavLink>
                        <NavLink to={"/books/add"}>Add Book</NavLink>
                    </li>
                </ul>
            </nav>
        </div>
            <div>
            <ul>
                <li>
                    <NavLink to={"#"}>Sign up</NavLink>
                    <NavLink to={"#"}>Sign in</NavLink>
                </li>
            </ul>
            </div>
        </div>
    )
}