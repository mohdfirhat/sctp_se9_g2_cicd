import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { CART_ACTION } from "../../redux/cartReducer";

import styles from "./CartPage.module.css";

import { CartCard } from "../../components/CartCard/CartCard";
import { CartNotLoggedInPage } from "./CartNotLoggedInPage";
import { CartEmptyPage } from "./CartEmptyPage";
import { CartBar } from "../../components/CartBar/CartBar";

import { backendApi } from "../../api/backendApi";

function CartPage() {
  // declarations
  const user = useSelector((state) => state.user);
  const { cart } = useSelector((state) => state.cart);
  const dispatch = useDispatch();

  const [cartWasCleared, setCartWasCleared] = useState(false);

  // useEffects
  useEffect(() => {
    //set title to cart
    document.title = "Mart | Cart";
  }, []);

  useEffect(() => {
    if (cartWasCleared && cart.length === 0) {
      setCartWasCleared(false);
      alert("Cart has been checked out!");
    }
  }, [cartWasCleared, cart.length]);

  // handler functions
  const handlerAddProduct = (product) => {
    try {
      backendApi.put(
        `users/cart/${product.id}`,
        {
          ...product,
          quantity: product.quantity + 1,
          total: product.total + product.price,
        },
        {
          headers: { Authorization: `Bearer ${localStorage.getItem("minimartJwtToken")}` },
        }
      );
      console.log("Adding product Successful!");
      dispatch(CART_ACTION.addOneProduct({ id: product.id }));
    } catch (e) {
      console.log(`Adding cartproduct Failed!\n${e}`);
    }
  };
  const handlerSubtractProduct = (product) => {
    try {
      backendApi.put(
        `users/cart/${product.id}`,
        {
          ...product,
          quantity: product.quantity - 1,
          total: product.total - product.price,
        },
        {
          headers: { Authorization: `Bearer ${localStorage.getItem("minimartJwtToken")}` },
        }
      );
      console.log("Subtract product Successful!");
      dispatch(CART_ACTION.deductOneProduct({ id: product.id }));
    } catch (e) {
      console.log(`Subtract cartproduct Failed!\n${e}`);
    }
  };
  const handlerRemoveFromCart = (product) => {
    try {
      backendApi.delete(`users/cart/${product.id}`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("minimartJwtToken")}` },
      });
      console.log("Delete product Successful!");
      dispatch(CART_ACTION.removeProduct({ id: product.id }));
    } catch (e) {
      console.log(`Delete cartproduct Failed!\n${e}`);
    }
  };

  const handlerCartCheckout = () => {
    // currently just removes items from cart as there's no checkout flow
    try {
      backendApi.delete(`users/cart`, {
        headers: { Authorization: `Bearer ${localStorage.getItem("minimartJwtToken")}` },
      });
      console.log("Delete cart was Successful!");
      setCartWasCleared(true);
      dispatch(CART_ACTION.cartReset());
    } catch (e) {
      console.log(`Delete cart Failed!\n${e}`);
    }
  };

  // early return checks
  if (!user.isLoggedIn) {
    return <CartNotLoggedInPage />;
  }

  if (!cart || cart.length === 0) {
    return <CartEmptyPage />;
  }

  // return statement
  return (
    <div className={styles.cartList}>
      <CartBar cart={cart} handlerCheckout={handlerCartCheckout} />
      {cart.map((product) => {
        return (
          <CartCard
            key={product.id}
            product={product}
            handlerAddProduct={handlerAddProduct}
            handlerSubtractProduct={handlerSubtractProduct}
            handlerRemoveFromCart={handlerRemoveFromCart}
          />
        );
      })}
    </div>
  );
}
export default CartPage;
