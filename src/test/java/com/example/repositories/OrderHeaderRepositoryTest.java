package com.example.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.example.domain.Customer;
import com.example.domain.OrderApproval;
import com.example.domain.OrderHeader;
import com.example.domain.OrderLine;
import com.example.domain.Product;
import com.example.domain.ProductStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderHeaderRepositoryTest {

  @Autowired
  OrderHeaderRepository orderHeaderRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  ProductRepository productRepository;

  Product product;

  @BeforeEach
  void setUp() {
    Product newProduct = new Product();
    newProduct.setProductStatus(ProductStatus.NEW);
    newProduct.setDescription("test product");
    product = productRepository.saveAndFlush(newProduct);
  }

  @Test
  void testSaveOrderWithLine() {
    OrderHeader orderHeader = new OrderHeader();
    Customer customer = new Customer();
    customer.setCustomerName("New Customer");
    Customer savedCustomer = customerRepository.save(customer);

    orderHeader.setCustomer(savedCustomer);

    OrderLine orderLine = new OrderLine();
    orderLine.setQuantityOrdered(5);
    orderLine.setProduct(product);

    orderHeader.addOrderLine(orderLine);

    OrderApproval approval = new OrderApproval();
    approval.setApprovedBy("me");

    orderHeader.setOrderApproval(approval);

    OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

    orderHeaderRepository.flush();

    assertNotNull(savedOrder);
    assertNotNull(savedOrder.getId());
    assertNotNull(savedOrder.getOrderLines());
    assertEquals(1, savedOrder.getOrderLines().size());

    OrderHeader fetchedOrder = orderHeaderRepository.findById(savedOrder.getId()).orElse(null);

    assertNotNull(fetchedOrder);
    assertEquals(1, fetchedOrder.getOrderLines().size());
  }

  @Test
  void testSaveOrder() {
    OrderHeader orderHeader = new OrderHeader();
    Customer customer = new Customer();
    customer.setCustomerName("New Customer");
    Customer savedCustomer = customerRepository.save(customer);

    orderHeader.setCustomer(savedCustomer);
    OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);

    assertNotNull(savedOrder);
    assertNotNull(savedOrder.getId());

    OrderHeader fetchedOrder = orderHeaderRepository.findById(savedOrder.getId()).orElse(null);

    assertNotNull(fetchedOrder);
    assertNotNull(fetchedOrder.getId());
    assertNotNull(fetchedOrder.getCreatedDate());
    assertNotNull(fetchedOrder.getLastModifiedDate());
  }

  @Test
  void testDeleteCascade() {

    OrderHeader orderHeader = new OrderHeader();
    Customer customer = new Customer();
    customer.setCustomerName("new Customer");
    orderHeader.setCustomer(customerRepository.save(customer));

    OrderLine orderLine = new OrderLine();
    orderLine.setQuantityOrdered(3);
    orderLine.setProduct(product);

    OrderApproval orderApproval = new OrderApproval();
    orderApproval.setApprovedBy("me");
    orderHeader.setOrderApproval(orderApproval);

    orderHeader.addOrderLine(orderLine);
    OrderHeader savedOrder = orderHeaderRepository.saveAndFlush(orderHeader);

    System.out.println("order saved and flushed");

    orderHeaderRepository.deleteById(savedOrder.getId());
    orderHeaderRepository.flush();

    OrderHeader fetchedOrder = orderHeaderRepository.findById(savedOrder.getId()).orElse(null);
    assertNull(fetchedOrder);
  }
}