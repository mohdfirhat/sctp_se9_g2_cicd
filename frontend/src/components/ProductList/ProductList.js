import { Link } from "react-router-dom";
import { displayCost } from "../../utils/displayCost";
import styles from "./ProductList.module.css";
import ReactStars from "react-stars";

function ProductList({ products }) {
  return (
    <div className={styles.productList}>
      {products.map((product) => (
        <div className={styles.productCard} key={product.id}>
          <Link to={`/product/${product.id}`} className={styles.link}>
            <div>
              <img className={styles.image} loading="lazy" src={product.image} alt="Store Product" />
              <div className={styles.productDescContainer}>
                <p className={styles.title}>{product.title}</p>
                <div className={styles.ratingPriceContainer}>
                  <p className={styles.rating}>
                    <ReactStars value={product.rating.rate} edit={false} />({product.rating.count})
                  </p>
                  <p className={styles.price}>{displayCost(product.price)}</p>
                </div>
              </div>
            </div>
          </Link>
        </div>
      ))}
    </div>
  );
}
export default ProductList;
