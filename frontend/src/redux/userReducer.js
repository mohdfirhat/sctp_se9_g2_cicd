import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLoggedIn: false,
  username: "",
  id: null,
};

//UserSlice
export const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    logIn: (state, action) => {
      return {
        isLoggedIn: true,
        username: action.payload.username,
        id: action.payload.id,
      };
    },
    logOut: (state) => {
      return initialState;
    },
  },
});

export const USER_ACTION = userSlice.actions;

export default userSlice.reducer;
