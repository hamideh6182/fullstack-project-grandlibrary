import Layout from "../components/Layout";
import SignForm from "../components/SignForm";

export default function SignUp() {
    return (
        <Layout>
            <div style={{padding: "5rem 0"}}>
                <h1>Sign Up</h1>
                <SignForm action={"sign-up"}/>
            </div>
        </Layout>
    );
}