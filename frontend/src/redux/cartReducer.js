import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  cart: [],
};

export const cartSlice = createSlice({
  name: "cart",
  initialState,
  reducers: {
    getFromDB: (state, action) => {
      state.cart = action.payload;
    },
    addToCart: (state, action) => {
      const index = state.cart.findIndex((cartProduct) => cartProduct.id === action.payload.id);

      if (index < 0) {
        //if doesnt exist push
        state.cart.push(action.payload);
      } else {
        //if exist update total and quantity
        state.cart[index].total += action.payload.total;
        state.cart[index].quantity += action.payload.quantity;
      }
    },
    addOneProduct: (state, action) => {
      const index = state.cart.findIndex((cartProduct) => cartProduct.id === action.payload.id);
      // console.log(`ID to add quantity: ${action.payload.id}`);
      // console.log(`Index to add quantity: ${index}`);
      state.cart[index].quantity += 1;
      state.cart[index].total += state.cart[index].price;
    },
    deductOneProduct: (state, action) => {
      const index = state.cart.findIndex((cartProduct) => cartProduct.id === action.payload.id);
      // console.log(`ID to deduct quantity: ${action.payload.id}`);
      // console.log(`Index to deduct quantity: ${index}`);

      if (state.cart[index].quantity === 1) {
        // remove when 0 left
        state.cart.splice(index, 1);
      } else {
        state.cart[index].quantity -= 1;
        state.cart[index].total -= state.cart[index].price;
      }
    },
    removeProduct: (state, action) => {
      const index = state.cart.findIndex((cartProduct) => cartProduct.id === action.payload.id);
      // console.log(`ID to remove: ${action.payload.id}`);
      // console.log(`Index to remove: ${index}`);

      state.cart.splice(index, 1);
    },
    cartReset: (state) => {
      state.cart = [];
    },
  },
});

export const CART_ACTION = cartSlice.actions;

export default cartSlice.reducer;
