import { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { USER_ACTION } from "../../redux/userReducer";
import { CART_ACTION } from "../../redux/cartReducer";
import Joi from "joi";

import styles from "./LoginPage.module.css";

import { backendApi } from "../../api/backendApi";

/*
Page Workflow/Feature:
If user is navigated from cart, alert(message);
If user logged in,navigate to home.
If user is log in successfully, set UserContext,navigate to home.
If user fail to login, alert wrong username/password.
Form Validation(input level/form level).
*/
const schema = Joi.object({
  // field name: Joi validation rules
  username: Joi.string().min(3).max(20).required().messages({
    "string.empty": "Username is required",
    "string.min": "Username must be at least 3 characters",
    "string.max": "Username must be at most 20 characters",
  }),
  password: Joi.string().min(8).required().messages({
    "string.empty": "Password is required",
    "string.min": "Password must be at least 8 characters",
  }),
});

const initialLoginState = {
  username: "",
  password: "",
};

function LoginPage() {
  const [form, setForm] = useState(initialLoginState);
  const [formErrors, setFormErrors] = useState({});
  const dispatch = useDispatch();
  const user = useSelector((state) => state.user);

  const navigate = useNavigate();
  const location = useLocation();

  if (location.state) alert(`${location.state.message}`);

  //navigate user to home if user is logged in
  if (user.isLoggedIn) navigate("/", { replace: true });

  useEffect(() => {
    //set title to login
    document.title = "Mart | Login";
  }, []);

  const handlerFormInput = (e) => {
    const { name, value } = e.target;

    setForm((prevState) => {
      return {
        ...prevState,
        [name]: value,
      };
    });

    validateField(name, value);
  };

  const validateField = (name, value) => {
    // Extract the schema for a specific field
    const fieldSchema = schema.extract(name);

    if (!fieldSchema) return;

    // Validate the field value against its schema
    // error only exists if the field value is invalid based on schema
    const { error } = fieldSchema.validate(value);

    setFormErrors((prevErrors) => {
      const updatedErrors = { ...prevErrors };

      if (error) {
        updatedErrors[name] = error.details[0].message;
      } else {
        delete updatedErrors[name];
      }

      return updatedErrors;
    });
  };

  const handlerLogin = (e) => {
    e.preventDefault();

    const { error } = schema.validate(form, { abortEarly: false });

    //check full form input for error and update FormError
    if (error) {
      const updatedErrors = error.details.reduce((acc, item) => {
        return {
          ...acc,
          [item.path[0]]: item.message,
        };
      }, {});

      setFormErrors(updatedErrors);
      alert("Form has errors.  Please check the form before submitting.");
    }

    // No error, try login
    login(form);
  };

  // const login = async (form) => {
  //   try {
  //     const res = await mockApi.get(
  //       `users/?username=${form.username.toLowerCase()}&password=${form.password.toLowerCase()}`
  //     );
  //     //set UserContext to loggedin
  //     dispatch(USER_ACTION.logIn({ username: res.data[0].username, id: res.data[0].id }));
  //     dispatch(CART_ACTION.getFromDB(res.data[0].cart));
  //     navigate("/", { replace: true });
  //   } catch (e) {
  //     alert("Invalid username or password.");
  //   }
  // };

  // const cartSample = {
  //   id: "1",
  //   title: "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
  //   price: 109.95,
  //   description:
  //     "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
  //   image: "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
  //   quantity: 1,
  //   total: 109.95,
  // };
  const login = async (form) => {
    try {
      const res = await backendApi.post("/auth/login", {
        username: form.username.toLowerCase(),
        password: form.password,
      });
      //set UserContext to loggedin
      dispatch(USER_ACTION.logIn({ username: res.data.username, id: res.data.id }));
      console.log(`Login Token: ${res.data.token}`);
      localStorage.setItem("minimartJwtToken", res.data.token);
      console.log(`LocalStorage token: ${localStorage.getItem("minimartJwtToken")}`);

      //get cartproducts from DB
      const res2 = await backendApi.get("users/cart", {
        headers: { Authorization: `Bearer ${localStorage.getItem("minimartJwtToken")}` },
      });
      dispatch(CART_ACTION.getFromDB(res2.data));
      navigate("/", { replace: true });
    } catch (e) {
      console.log(e);
      alert("Invalid username or password.");
    }
  };

  return (
    <div className={styles.loginContainer}>
      <form onSubmit={handlerLogin}>
        <h2>Login</h2>
        <div className={styles.inputContainer}>
          {/* <label htmlFor="username">Username</label> */}
          <input
            id="username"
            type="text"
            name="username"
            placeholder="Username"
            onChange={handlerFormInput}
            value={form.username}
            className={formErrors.username ? styles.inputError : styles.formInput}
          />
          {formErrors.username && <div className={styles.errorMessage}>{formErrors.username}</div>}
        </div>
        <div className={styles.inputContainer}>
          {/* <label htmlFor="password">Password</label> */}
          <input
            id="password"
            type="password"
            name="password"
            placeholder="Password"
            onChange={handlerFormInput}
            value={form.password}
            className={formErrors.username ? styles.inputError : styles.formInput}
          />
          {formErrors.password && <div className={styles.errorMessage}>{formErrors.password}</div>}
        </div>
        <button className={styles.button} type="submit">
          Login
        </button>
        <Link className={styles.link} to="/register">
          New here? Register here.
        </Link>
      </form>
    </div>
  );
}
export default LoginPage;
