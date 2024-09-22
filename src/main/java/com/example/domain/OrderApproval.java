package com.example.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderApproval extends BaseEntity {

  @OneToOne
  @JoinColumn(name = "order_header_id")
  private OrderHeader orderHeader;

  private String approvedBy;

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
    OrderApproval that = (OrderApproval) o;
    return Objects.equals(orderHeader, that.orderHeader) && Objects.equals(
        approvedBy, that.approvedBy);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), orderHeader, approvedBy);
  }
}
