import { PuffLoader } from "react-spinners";
import Button from "../../components/ProductCard/Button";
//Use the Magnifier component from the react18-image-magnifier package for image
import Magnifier from "react18-image-magnifier";
import styles from "./ProductPage.module.css";
import ReactStars from "react-stars";

function ProductPageView({
  products,
  handlerMinus,
  count,
  handlerAddRating,
  userRating,
  setUserRating,
  handlerPlus,
  priceTotal,
  handlerAddToCart,
  handlerContinueShopping,
  isLoaded,
  addedItemsToCart,
  isHovered,
  handlerImageEnter,
  handlerImageLeave,
}) {
  return (
    <>
      {products === undefined ||
        (products.length <= 0 && (
          <div className={styles.spinnerStyle}>
            <PuffLoader color="#193993" size={170} speedMultiplier={2} />
          </div>
        ))}
      {products !== null && products !== undefined && isLoaded && (
        <div className={styles.productContainer} hidden={!isLoaded}>
          <div className={styles.productLeftSide}>
            <div className={styles.productLeftSideContainer}>
              <section className={styles.pictureSection}>
                <div className={styles.pictureContainer}>
                  <div className={styles.pictureRatio}>
                    <div className={styles.pictureResponsive}>
                      <div className={styles.pictureDisplay}>
                        {products.image && (
                          <div
                            className={styles.magnifierPicture}
                            onMouseEnter={handlerImageEnter}
                            onMouseLeave={handlerImageLeave}
                            onTouchStart={handlerImageEnter}
                            onTouchEnd={handlerImageLeave}
                          >
                            {!isHovered && <div className={styles.overlayText}>Hover / Touch to Zoom</div>}
                            <Magnifier
                              src={products.image}
                              className={styles.pictureShow}
                              alt="Store Product"
                              zoomImgSrc={products.image}
                              zoomFactor={1.8}
                              mgShowOverflow="false"
                              mgMouseOffsetX="0"
                              mgMouseOffsetY="0"
                              mgTouchOffsetX="0"
                              mgTouchOffsetY="0"
                              height="auto"
                              width="80%"
                            ></Magnifier>
                          </div>
                        )}
                      </div>
                    </div>
                  </div>
                </div>
              </section>
            </div>
          </div>
          <div className={styles.productRightSide}>
            <div className={styles.productRightSideContainer}>
              <div className={styles.productDetails}></div>
              <div className={styles.productDetailsContainer}></div>
              <section className={styles.productTitleSection}>
                <h1>{products.title}</h1>
              </section>
              <hr className={styles.sectionSeparator}></hr>
              <div className={styles.productDetailsContainer}>
                <span>{products.description}</span>
              </div>
              <div>
                {addedItemsToCart !== undefined && addedItemsToCart > 0 && (
                  <div className={styles.addedToCart}>
                    <span>{addedItemsToCart} Item(s) Added to Cart!</span>
                  </div>
                )}
              </div>
              <div className={styles.productNumberContainer}>
                <span>Price: ${products.price ? products.price.toFixed(2) : 0}</span>
                <span>
                  <div className={styles.counter}>
                    Quantity: <Button label="➖" onClick={handlerMinus} />
                    <span className={styles.count}>{count}</span>
                    <Button label="➕" onClick={handlerPlus} />
                  </div>
                </span>
                <div className={styles.productTotalNumberContainer}>
                  <span>Total Price: {priceTotal}</span>
                </div>
                {/* TODO: Implement posting user rating */}
                {/* Product Rating */}
                <div className={styles.ratingContainer}>
                  <div className={styles.ratingStarContainer}>
                    <p>Product Rating: </p>
                    <ReactStars value={products.rating.rate} edit={false} />({products.rating.count})
                  </div>

                  <button
                    className={styles.addRatingButton}
                    onClick={() => {
                      setUserRating((prevState) => ({ ...prevState, isVisible: !prevState.isVisible }));
                    }}
                  >
                    Add Rating
                  </button>
                </div>
                {/* User Rating */}
                {userRating.isVisible && (
                  <div className={styles.ratingContainer}>
                    <div className={styles.ratingStarContainer}>
                      <p>User Rating: </p>
                      <ReactStars value={userRating.rate} edit={userRating.rate === 0} onChange={handlerAddRating} />
                    </div>
                  </div>
                )}
              </div>
              <div className={styles.productActionContainer}>
                <button className={styles.productActionButton} onClick={handlerAddToCart}>
                  Add to Cart
                </button>
                <button className={styles.productActionButton} onClick={handlerContinueShopping}>
                  Continue Shopping
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
export default ProductPageView;
