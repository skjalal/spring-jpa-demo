package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Version;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderLine extends BaseEntity {

  @Version
  private Integer version;
  private Integer quantityOrdered;

  @ManyToOne
  private OrderHeader orderHeader;

  @ManyToOne
  private Product product;

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
    OrderLine orderLine = (OrderLine) o;
    return Objects.equals(quantityOrdered, orderLine.quantityOrdered) && Objects.equals(orderHeader,
        orderLine.orderHeader) && Objects.equals(product, orderLine.product);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), quantityOrdered, orderHeader, product);
  }
}
