package com.example.domain;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class Address {

  private String addressLine;
  private String city;
  private String state;
  private String zipCode;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Address address = (Address) o;
    return Objects.equals(addressLine, address.addressLine) && Objects.equals(city, address.city)
        && Objects.equals(state, address.state) && Objects.equals(zipCode, address.zipCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(addressLine, city, state, zipCode);
  }
}
