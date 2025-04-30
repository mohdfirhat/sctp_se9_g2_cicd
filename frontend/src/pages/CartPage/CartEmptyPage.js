import { Link } from "react-router-dom";
import styles from "./CartEmptyLoggedOutPage.module.css"

export const CartEmptyPage = () => {
    return (
        <div className={styles.cartEmptyLoggedOut}> 
            <p>
                Your cart appears to be empty...
                <Link to={"/"}> Shop now! </Link>
            </p>
        </div>
    );
};