package com.example.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@AttributeOverrides(value = {
    @AttributeOverride(name = "shippingAddress.addressLine", column = @Column(name = "shipping_address")),
    @AttributeOverride(name = "shippingAddress.city", column = @Column(name = "shipping_city")),
    @AttributeOverride(name = "shippingAddress.state", column = @Column(name = "shipping_state")),
    @AttributeOverride(name = "shippingAddress.zipCode", column = @Column(name = "shipping_zip_code")),
    @AttributeOverride(name = "billToAddress.addressLine", column = @Column(name = "bill_to_address")),
    @AttributeOverride(name = "billToAddress.city", column = @Column(name = "bill_to_city")),
    @AttributeOverride(name = "billToAddress.state", column = @Column(name = "bill_to_state")),
    @AttributeOverride(name = "billToAddress.zipCode", column = @Column(name = "bill_to_zip_code"))})
public class OrderHeader extends BaseEntity {

  @Version
  private Integer version;
  @ManyToOne(fetch = FetchType.LAZY)
  private Customer customer;

  @Embedded
  private Address shippingAddress;

  @Embedded
  private Address billToAddress;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @Fetch(FetchMode.SUBSELECT)
  @OneToMany(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST,
      CascadeType.REMOVE}, fetch = FetchType.EAGER)
  private Set<OrderLine> orderLines;

  @Fetch(FetchMode.SELECT)
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  private OrderApproval orderApproval;

  public void addOrderLine(OrderLine orderLine) {
    if (orderLines == null) {
      orderLines = new HashSet<>();
    }
    orderLines.add(orderLine);
    orderLine.setOrderHeader(this);
  }

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
    OrderHeader that = (OrderHeader) o;
    return Objects.equals(customer, that.customer) && Objects.equals(shippingAddress,
        that.shippingAddress) && Objects.equals(billToAddress, that.billToAddress)
        && orderStatus == that.orderStatus;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), customer, shippingAddress, billToAddress, orderStatus);
  }
}
