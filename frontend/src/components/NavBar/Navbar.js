import { Link, NavLink } from "react-router-dom";
import bluebag from "../../assets/bluebag.png";
import styles from "./NavBar.module.css";
import { useDispatch, useSelector } from "react-redux";
import { USER_ACTION } from "../../redux/userReducer";
import { CART_ACTION } from "../../redux/cartReducer";

function Navbar() {
  const user = useSelector((state) => state.user);
  const { cart } = useSelector((state) => state.cart);
  const dispatch = useDispatch();

  return (
    <header>
      <Link to="/" className={styles.link}>
        <img className={styles.bagImg} src={bluebag} alt="Mart logo" />
        <span className={styles.logoText}>Mart</span>
      </Link>

      <nav className="styles.navContainer">
        <ul>
          {user.isLoggedIn ? (
            <li>
              <Link
                className={styles.link}
                onClick={() => {
                  dispatch(USER_ACTION.logOut());
                  dispatch(CART_ACTION.cartReset());
                  localStorage.removeItem("minimartJwtToken");
                }}
              >
                Logout
              </Link>
            </li>
          ) : (
            <li>
              <NavLink className={({ isActive }) => (isActive ? styles.linkActive : styles.link)} to="/login">
                Login
              </NavLink>
            </li>
          )}
          <li>
            <NavLink className={({ isActive }) => (isActive ? styles.linkActive : styles.link)} to="/cart">
              Cart
            </NavLink>
            <span>{cart.length ? <span className={styles.badge}>{cart.length}</span> : ""}</span>
          </li>
        </ul>
      </nav>
    </header>
  );
}
export default Navbar;
