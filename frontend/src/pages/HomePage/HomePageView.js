import { PuffLoader } from "react-spinners";

import styles from "./HomePage.module.css";

import CategoryList from "../../components/CategoryList/CategoryList";
import ProductList from "../../components/ProductList/ProductList";

function HomePageView({ selectedProducts }) {
  return (
    <>
      {selectedProducts === undefined ||
        (selectedProducts.length <= 0 && (
          <div className={styles.spinnerStyle}>
            <PuffLoader color="#193993" size={170} speedMultiplier={2} />
          </div>
        ))}
      {selectedProducts !== undefined && selectedProducts.length > 0 && (
        <section className={styles.sectionStyle}>
          <section>
            <div>
              <div className={styles.headerStyle}>
                <section className={styles.sectionStyle}>
                  <div className={styles.titleStyle}>
                    <h2 className={styles.titleName}>Popular</h2>
                  </div>
                </section>
              </div>
              <ProductList products={selectedProducts} />
            </div>
          </section>
        </section>
      )}
      <CategoryList />
    </>
  );
}
export default HomePageView;
