package com.group2.theminimart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group2.theminimart.entity.CartContent;

public interface CartContentRepository extends JpaRepository<CartContent, Long> {
    Optional<CartContent> findByUserIdAndProductId(Long userId, Long prodctId);
    
    Optional<List<CartContent>> findByUserId(Long userId);
}
