package com.group2.theminimart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cart_content")
public class CartContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "count")
    @PositiveOrZero(message = "count must be a postive number")
    private int count;
    @PositiveOrZero(message = "total must be a positive number")
    @Column(name = "total")
    private double total;

    @JsonIgnoreProperties("cart")
    @ManyToOne(optional = false)
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private User user;

    @JsonIgnoreProperties("cart")
    @ManyToOne(optional = false)
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;
}
