export const defaultProduct = {
  id: 0,
  title: "",
  price: "",
  description: "",
  image: "",
  count: 1,
  priceTotal: 0,
};

export function productReducer(state, action) {
  //Reducer function handles for all the product actions.
  switch (action.type) {
    case "PLUS_COUNT": {
      let newState = { ...state };
      newState.count = state.count + 1;
      if (newState.count > 20) {
        newState.count = 20;
      }
      return newState; //Return the new updated state.
    }

    case "MINUS_COUNT": {
      let newState = { ...state };
      newState.count = state.count - 1;
      if (newState.count < 1) {
        newState.count = 1;
      }
      return newState;
    }

    case "IMAGE_ENTER": {
      return { ...state, isHovered: true };
    }

    case "IMAGE_LEAVE": {
      return { ...state, isHovered: false };
    }

    default:
      throw Error("productReducer - unknown action:", action.type);
  }
}
