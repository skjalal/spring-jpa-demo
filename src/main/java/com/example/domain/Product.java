package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product extends BaseEntity {

  private String description;

  @Enumerated(EnumType.STRING)
  private ProductStatus productStatus;

  @ManyToMany
  @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
  private Set<Category> categories;

  private Integer quantityOnHand = 0;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(description, product.description)
        && productStatus == product.productStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), description, productStatus);
  }
}
