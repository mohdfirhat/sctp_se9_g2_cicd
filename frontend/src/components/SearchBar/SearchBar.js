import { useState } from "react";
import { dummyProductData } from "../../dummydata/dummyProductData";
import styles from "./SearchBar.module.css";
import Fuse from "fuse.js";
import RecommendList from "../RecommendList/RecommendList";
import ProductList from "../ProductList/ProductList";
import { backendApi } from "../../api/backendApi";

const initialSearchState = {
  searchInput: "",
  prevSearch: "",
  isHidden: true,
  searchProducts: [],
  searchError: "",
};

function SearchBar() {
  const [search, setSearch] = useState(initialSearchState);

  const options = {
    includeScore: true,
    threshold: 0.4,
    ignoreLocation: true,
    keys: ["title"],
  };

  const fuse = new Fuse(dummyProductData, options);
  const result = fuse.search(search.searchInput);
  const topFiveResult = result.slice(0, 5);

  const handlerSearch = (e) => {
    e.preventDefault();
    const getSearchProducts = async () => {
      //reset the searchError
      setSearch((prevState) => ({
        ...prevState,
        searchError: "",
      }));

      // search the backendAPI for the following params. If found set result to searchProducts else set searchError message
      try {
        const param = new URLSearchParams({ title: search.searchInput });
        const res = await backendApi.get(`/products/search?${param.toString()}`);
        setSearch((prevState) => ({ ...prevState, searchProducts: res.data, prevSearch: prevState.searchInput }));
      } catch (e) {
        setSearch((prevState) => ({
          ...prevState,
          searchProducts: [],
          prevSearch: prevState.searchInput,
          searchError: `No search result for "${prevState.searchInput}"`,
        }));
      }
    };
    if (search.searchInput) {
      getSearchProducts();
    } else {
      //if searchInput empty("") does not search and remove searchProducts, searchError and prevSearch
      setSearch((prevState) => ({
        ...prevState,
        searchError: "",
        searchProducts: [],
        prevSearch: prevState.searchInput,
      }));
    }
  };

  return (
    <>
      <form className={styles.searchContainer} onSubmit={handlerSearch}>
        <div className={styles.inputRecommendContainer}>
          <input
            className={styles.searchInput}
            type="text"
            id="searchBox"
            name="search"
            placeholder="Search Products"
            maxLength={100}
            value={search.searchInput}
            onChange={(e) => {
              setSearch((prevState) => ({ ...prevState, searchInput: e.target.value }));
            }}
            onBlur={() => {
              setTimeout(() => {
                setSearch((prevState) => ({ ...prevState, isHidden: true }));
              }, 200);
            }}
            onFocus={() => {
              setSearch((prevState) => ({ ...prevState, isHidden: false }));
            }}
            onKeyUp={(e) => e.key === "Enter" && e.currentTarget.blur()}
          />
          {!search.isHidden && <RecommendList result={topFiveResult} />}
        </div>
        <button type="submit" className={styles.searchButton}>
          Search
        </button>
      </form>

      {/* If backendApi returns error show No result found */}
      {search.searchError && (
        <>
          <h2 className={styles.titleName}>Search Results for "{search.prevSearch}"</h2>
          <div className={styles.errorContainer}>
            <span className={styles.errorMessage}>{search.searchError}</span>
          </div>
        </>
      )}
      {/* If backendApi returns data, display searchProducts */}
      {search.searchProducts.length > 0 && (
        <>
          <h2 className={styles.titleName}>Search Results for "{search.prevSearch}"</h2>
          <ProductList products={search.searchProducts} />
        </>
      )}
    </>
  );
}
export default SearchBar;
