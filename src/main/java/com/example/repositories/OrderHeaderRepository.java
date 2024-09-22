package com.example.repositories;

import com.example.domain.Customer;
import com.example.domain.OrderHeader;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

  List<OrderHeader> findAllByCustomer(Customer customer);
}
