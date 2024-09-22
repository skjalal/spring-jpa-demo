package com.example.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AttributeOverrides(value = {
    @AttributeOverride(name = "address.addressLine", column = @Column(name = "address"))})
public class Customer extends BaseEntity {

  private String customerName;

  @Embedded
  private Address address;

  private String phone;
  private String email;

  @Version
  private Integer version;

  @OneToMany(mappedBy = "customer")
  private Set<OrderHeader> orders = new LinkedHashSet<>();

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
    Customer customer = (Customer) o;
    return Objects.equals(customerName, customer.customerName) && Objects.equals(address,
        customer.address) && Objects.equals(phone, customer.phone) && Objects.equals(email,
        customer.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), customerName, address, phone, email);
  }
}
