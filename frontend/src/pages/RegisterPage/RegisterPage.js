import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import Joi from "joi";

import styles from "./RegisterPage.module.css";

import { backendApi } from "../../api/backendApi";

/*
Page Workflow/Feature:
if user is logged in, navigate to homepage.
if user successfully register, alert(successful) and navigate to LoginPage.
if user submit with formError, alert(edit form msg)
Form validation(input and form valication)
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

const initialRegisterState = {
  username: "",
  password: "",
};

function RegisterPage() {
  const [form, setForm] = useState(initialRegisterState);
  const [formErrors, setFormErrors] = useState({});

  const user = useSelector((state) => state.user);
  const navigate = useNavigate();

  //navigate user to home if user is logged in
  if (user.isLoggedIn) navigate("/", { replace: true });

  useEffect(() => {
    //set title to Register
    document.title = "Mart | Register";
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

  const handlerRegister = (e) => {
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
    register(form);
  };

  const register = async (form) => {
    try {
      const res = await backendApi.post("/auth/register", {
        username: form.username.toLowerCase(),
        password: form.password,
      });

      console.log(res);
      alert("Register was successful!");
      navigate("/login", { replace: true });
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <div className={styles.registerContainer}>
      <form onSubmit={handlerRegister}>
        <h2>Register</h2>
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
          Register
        </button>
        <Link className={styles.link} to="/login">
          Already have an account? Login here.
        </Link>
      </form>
    </div>
  );
}
export default RegisterPage;
