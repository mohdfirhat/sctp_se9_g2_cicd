import { useEffect, useState } from "react";
import styles from "./CategoryList.module.css";
import ProductList from "../ProductList/ProductList";
import { backendApi } from "../../api/backendApi";

function Categorylist() {
  const [selectedCategory, setSelectedCategory] = useState("women's clothing");
  const [products, setProducts] = useState([]);
  useEffect(() => {
    const getProductByCategory = async () => {
      try {
        const param = new URLSearchParams({ category: selectedCategory });
        const res = await backendApi.get(`/products/search?${param.toString()}`);
        setProducts(res.data.filter((product) => product.category === selectedCategory));
      } catch (e) {
        console.log(e);
      }
    };
    getProductByCategory();
  }, [selectedCategory]);

  return (
    <>
      <h2 className={styles.titleName}>Categories</h2>
      <select
        className={styles.categoryName}
        name="category"
        id="category"
        value={selectedCategory}
        onChange={(e) => {
          setSelectedCategory(e.target.value);
        }}
      >
        <option value="women's clothing">Woman</option>
        <option value="men's clothing">Mens</option>
        <option value="jewelery">Jewelery</option>
        <option value="electronics">Electronics</option>
      </select>
      <ProductList products={products} />
    </>
  );
}
export default Categorylist;
