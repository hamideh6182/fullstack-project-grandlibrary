import Layout from "../components/Layout";
import SignForm from "../components/SignForm";
import "./SignUp.css"

export default function SignUp() {
    return (
        <Layout>
            <h1 className={"h1-sign-up"}>Sign <span>Up</span></h1>
            <div style={{padding: "5rem 0"}}>
                <SignForm action={"sign-up"}/>
            </div>
        </Layout>
    );
}