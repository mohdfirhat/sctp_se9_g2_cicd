package com.group2.theminimart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.group2.theminimart.dto.ProductRatingDto;
import com.group2.theminimart.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

  Optional<Rating> findByUser_IdAndProduct_Id(Long userId, Long productId);

  Optional<Rating> findByUser_UsernameAndProduct_Id(String username, Long productId);

  Optional<List<Rating>> findAllByUser_Id(Long userId);

  Optional<List<Rating>> findAllByUser_Username(String username);

  @Query(value = "SELECT CAST (COALESCE(SUM(rate * rate_count) / NULLIF(SUM(rate_count),0), 0.0) as DOUBLE PRECISION) AS rate, "
      +
      "CAST(COALESCE(SUM(rate_count),0) as INTEGER) AS count " +
      "FROM ( SELECT r.rate, COUNT(r.rate) AS rate_count FROM ratings r " +
      "JOIN products p ON r.product_id = p.id WHERE p.id = :productId GROUP BY r.rate ) AS subquery", nativeQuery = true)
  ProductRatingDto findAverageRatingByProductId(Long productId);
}
