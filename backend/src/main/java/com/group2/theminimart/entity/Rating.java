package com.group2.theminimart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "ratings", uniqueConstraints = {
    @UniqueConstraint(name = "UniqueUserAndProduct", columnNames = { "userId", "productId" }) })
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @Column
  private Double rate;

  @JsonIgnoreProperties("ratings")
  @ManyToOne(optional = false)
  @JoinColumn(name = "userId", referencedColumnName = "id")
  private User user;

  @JsonIgnoreProperties("ratings")
  @ManyToOne(optional = false)
  @JoinColumn(name = "productId", referencedColumnName = "id")
  private Product product;
}
