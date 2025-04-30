import { Link } from "react-router-dom";
import styles from "./CartEmptyLoggedOutPage.module.css"

export const CartNotLoggedInPage = () => {
    return (
        <div className={styles.cartEmptyLoggedOut}> 
            <p>
                You appear to not be logged in to a user account. <Link to={"/login"}> Login </Link>
            </p>
            <p>Or</p>
            <p> 
                New here? <Link to={"/register"}>Register</Link> 
            </p>
        </div>
    );
};