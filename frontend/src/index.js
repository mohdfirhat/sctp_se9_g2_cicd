import React from "react";
import ReactDOM from "react-dom/client";

import "./index.css";

import AppContext from "./context/AppContext";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <AppContext>
      <App />
    </AppContext>
  </React.StrictMode>
);
