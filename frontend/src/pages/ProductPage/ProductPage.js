import React, { useEffect, useState, useMemo, useCallback, useReducer } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { defaultProduct, productReducer } from "../../reducers/ProductReducer";
import { useDispatch, useSelector } from "react-redux";
import { CART_ACTION } from "../../redux/cartReducer";

import ProductPageView from "./ProductPageView";

import { backendApi, jwtBackendApi } from "../../api/backendApi";

function ProductPage() {
  const [state, dispatch] = useReducer(productReducer, defaultProduct);
  const userCtx = useSelector((state) => state.user);
  const { cart } = useSelector((state) => state.cart);
  const dispatchRedux = useDispatch();
  const navigate = useNavigate();
  const { id } = useParams();
  const [products, setProducts] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [addedItemsToCart, setAddedItemsToCart] = useState(0);
  const [userRating, setUserRating] = useState({ isVisible: false, rate: 0, isAllowEdit: true });
  //To check the states change on Page
  const [isCartUpdated, setIsCartUpdated] = useState(false);

  const getProduct = useCallback(async () => {
    try {
      //get the deails of product and set to product.(state)
      setIsLoaded(false);
      const res = await backendApi.get("/products/" + id);
      const singleProduct = res.data;
      if (singleProduct !== null && singleProduct !== undefined) {
        setProducts(singleProduct);
        setIsLoaded(true);
      }
    } catch (error) {
      console.log(error);
    }
  }, [id]);
  const getUserRating = useCallback(async () => {
    try {
      //get the deails of product and set to product.(state)
      const res = await jwtBackendApi.get(`/users/ratings/products/${id}`);
      const userRating = res.data;
      console.log("User Rating Response:");
      console.log(res.data);
      if (userRating !== null && userRating !== undefined) {
        setUserRating((prevState) => ({ ...prevState, rate: userRating.rate }));
      }
    } catch (error) {
      console.log(error);
    }
  }, [id]);

  useEffect(() => {
    //set title to login
    document.title = "Mart | Product";
    //getProduct fetches the product only when user goes to product(once).
    getProduct();
    getUserRating();
  }, [getProduct, getUserRating]);

  const handlerPlus = () => {
    dispatch({ type: "PLUS_COUNT" });
  };

  const handlerMinus = () => {
    dispatch({ type: "MINUS_COUNT" });
  };

  const handlerImageEnter = () => {
    dispatch({ type: "IMAGE_ENTER" });
  };

  const handlerImageLeave = () => {
    dispatch({ type: "IMAGE_LEAVE" });
  };

  const handlerAddToCart = () => {
    if (userCtx.isLoggedIn && userCtx.id !== null) {
      dispatchRedux(
        CART_ACTION.addToCart({
          ...products,
          quantity: state.count,
          total: parseFloat((state.count * products.price).toFixed(2)),
        })
      );
      updateUserCart();

      setAddedItemsToCart(state.count);
      setIsCartUpdated(true);
    } else {
      navigate("/login");
    }
  };

  const handlerContinueShopping = () => {
    navigate("/");
  };
  const handlerAddRating = (newRate) => {
    try {
      // TODO: refetch product rating after we rate
      const res = jwtBackendApi.post(`/users/ratings/products/${products.id}`, { rate: newRate });
      setUserRating((prevState) => ({ ...prevState, isVisible: false, rate: newRate }));
      console.log("Created rating Successful");
      console.log(res);
      //Logic for updating rating display
      setProducts((prevState) => ({
        ...prevState,
        rating: {
          count: prevState.rating.count + 1,
          rate: (prevState.rating.count * prevState.rating.rate + newRate) / (prevState.rating.count + 1),
        },
      }));
    } catch (e) {
      console.log("Created rating Failed");
      console.log(e);
    }
  };
  //Update user cart back to Api
  const updateUserCart = useCallback(async () => {
    if (userCtx.isLoggedIn && userCtx.id !== null) {
      try {
        // create cartproduct if not in cart
        // update cart product if not in cart
        const filteredCart = cart.filter((product) => product.id === products.id);

        if (filteredCart.length === 0) {
          const res = jwtBackendApi.post(`/users/cart`, {
            ...products,
            quantity: state.count,
            total: parseFloat((state.count * products.price).toFixed(2)),
          });
          console.log("Created cartproduct!" + res);
        } else {
          const res = jwtBackendApi.put(`users/cart/${products.id}`, {
            ...products,
            quantity: state.count + filteredCart[0].quantity,
            total: parseFloat(((state.count + filteredCart[0].quantity) * products.price).toFixed(2)),
          });
          console.log("Updated cartproduct!" + res);
        }
      } catch (error) {
        console.error("Failed to update user cart:", error);
      }
    }
  }, [userCtx.isLoggedIn, userCtx.id, cart, products, state.count]);

  useEffect(() => {
    if (isCartUpdated) {
      // updateUserCart();
      setIsCartUpdated(false);
    }
  }, [isCartUpdated, updateUserCart]);

  const calculatePriceTotal = useMemo(() => {
    return state.count * products.price;
  }, [state.count, products.price]);

  return (
    <div>
      <ProductPageView
        products={products}
        handlerMinus={handlerMinus}
        count={state.count}
        handlerAddRating={handlerAddRating}
        userRating={userRating}
        setUserRating={setUserRating}
        handlerPlus={handlerPlus}
        priceTotal={calculatePriceTotal.toFixed(2)}
        handlerAddToCart={handlerAddToCart}
        handlerContinueShopping={handlerContinueShopping}
        isLoaded={isLoaded}
        addedItemsToCart={addedItemsToCart}
        isHovered={state.isHovered}
        handlerImageEnter={handlerImageEnter}
        handlerImageLeave={handlerImageLeave}
      />
    </div>
  );
}
export default ProductPage;
