import { Link } from "react-router-dom";
import styles from "./RecommendList.module.css";

function RecommendList({ result }) {
  return (
    <div className={styles.recommendList}>
      {result.map((product) => (
        <Link className={styles.link} to={`/product/${product.item.id}`}>
          <div key={product.item.id} className={styles.recommendCard}>
            <img className={styles.image} loading="lazy" src={product.item.image} alt="Store Product" />
            <div className={styles.description}>
              <p className={styles.title}>{product.item.title}</p>
              <p className={styles.category}>{product.item.category}</p>
            </div>
          </div>
        </Link>
      ))}
    </div>
  );
}
export default RecommendList;
