import styles from "./CartCard.module.css";
import trashIcon from "../../icons/trash.svg";

export const CartCard = ({ product, handlerAddProduct, handlerSubtractProduct, handlerRemoveFromCart }) => {
  return (
    <div className={styles.cardContainer}>
      <div className={styles.cartCard}>
        <img className={styles.cartImage} src={product.image} alt={product.description}></img>
        <div className={styles.cartProductDetails}>
          <section className={styles.cardTitleSection}>
            <h1>{product.title}</h1>
          </section>
          <div className={styles.detailsAndTrash}>
            <div className={styles.details}>
              <span>Price: ${product.price.toFixed(2)}</span>
              <span>
                Quantity:
                <button
                  className={styles.button}
                  disabled={product.quantity === 1}
                  onClick={() => handlerSubtractProduct(product)}
                >
                  {"➖"}
                </button>
                {product.quantity}
                <button className={styles.button} onClick={() => handlerAddProduct(product)}>
                  {"➕"}
                </button>
              </span>
              <span>Total Price: ${product.total.toFixed(2)}</span>
            </div>
            <div className={styles.trashContainer}>
              <button className={styles.trashButton} onClick={() => handlerRemoveFromCart(product)}>
                <img src={trashIcon} alt="Remove from cart" />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
