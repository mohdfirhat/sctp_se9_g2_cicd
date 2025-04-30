package com.group2.theminimart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.group2.theminimart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

        @Query("SELECT p FROM Product p WHERE " +
                        "(:title = '' OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
                        "(:description = '' OR LOWER(p.description) LIKE LOWER(CONCAT('%', :description, '%'))) AND " +
                        "(:category = '' OR LOWER(p.category) LIKE LOWER(CONCAT('%', :category, '%')))")
        List<Product> searchProducts(@Param("title") String title, @Param("description") String description,
                        @Param("category") String category);
}
