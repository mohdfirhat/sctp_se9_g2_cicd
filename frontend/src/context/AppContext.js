import { Provider } from "react-redux";
import store, { persistor } from "../app/store";
import { PersistGate } from "redux-persist/integration/react";

function AppContext({ children }) {
  return (
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        {children}
      </PersistGate>
    </Provider>
  );
}
export default AppContext;
