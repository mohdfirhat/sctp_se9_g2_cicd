import { BrowserRouter, Route, Routes } from "react-router-dom";

import "./App.css";

import HomePage from "./pages/HomePage/HomePage";
import UserLayout from "./layout/UserLayout/UserLayout";
import LoginPage from "./pages/LoginPage/LoginPage";
import CartPage from "./pages/CartPage/CartPage";
import RegisterPage from "./pages/RegisterPage/RegisterPage";
import ProductPage from "./pages/ProductPage/ProductPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<UserLayout />}>
          <Route index element={<HomePage />} />
          <Route path="register" element={<RegisterPage />} />
          <Route path="product/:id" element={<ProductPage />} />
          <Route path="login" element={<LoginPage />} />
          <Route path="cart" element={<CartPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
