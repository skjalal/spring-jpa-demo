package com.example;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.domain.Address;
import com.example.domain.Customer;
import com.example.domain.OrderHeader;
import com.example.domain.OrderLine;
import com.example.domain.Product;
import com.example.domain.ProductStatus;
import com.example.repositories.CustomerRepository;
import com.example.repositories.OrderHeaderRepository;
import com.example.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
class SpringJpaDemoApplicationTests {

  private static final String PRODUCT_D1 = "Product 1";
  private static final String PRODUCT_D2 = "Product 2";
  private static final String PRODUCT_D3 = "Product 3";
  private static final String TEST_CUSTOMER = "TEST CUSTOMER";

  @Autowired
  OrderHeaderRepository orderHeaderRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  ProductRepository productRepository;

  /**
   * From MySQL Workbench (or other client, run the following SQL statement, then test below.) Once
   * you commit, the test will complete. If test completes immediately, check autocommit settings in
   * client. {@code SELECT * FROM orderservice.order_header where id = 1 for update; }
   */
  @Test
  void testDBLock() {
    Long id = 55L;

    OrderHeader orderHeader = orderHeaderRepository.findById(id).orElse(null);

    assertNotNull(orderHeader);
    Address billTo = new Address();
    billTo.setAddressLine("Bill me");
    orderHeader.setBillToAddress(billTo);
    orderHeaderRepository.saveAndFlush(orderHeader);

    System.out.println("I updated the order");
  }

  @Test
  void testN_PlusOneProblem() {

    Customer customer = customerRepository.findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER)
        .orElse(null);

    IntSummaryStatistics totalOrdered = orderHeaderRepository.findAllByCustomer(customer).stream()
        .flatMap(orderHeader -> orderHeader.getOrderLines().stream())
        .collect(Collectors.summarizingInt(OrderLine::getQuantityOrdered));

    assertNotNull(totalOrdered);
    System.out.println("total ordered: " + totalOrdered.getSum());
  }

  @Test
  void testLazyVsEager() {
    OrderHeader orderHeader = orderHeaderRepository.findById(52L).orElse(null);

    assertNotNull(orderHeader);
    System.out.println("Order Id is: " + orderHeader.getId());

    System.out.println("Customer Name is: " + orderHeader.getCustomer().getCustomerName());

  }

  @Disabled(value = "To verify the transaction commit")
  @Rollback(value = false)
  @Test
  void testDataLoader() {
    List<Product> products = loadProducts();
    Customer customer = loadCustomers();

    assertNotNull(customer);
    int ordersToCreate = 10000;

    for (int i = 0; i < ordersToCreate; i++) {
      System.out.println("Creating order #: " + i);
      saveOrder(customer, products);
    }

    orderHeaderRepository.flush();
  }

  private void saveOrder(Customer customer, List<Product> products) {
    Random random = new Random();

    OrderHeader orderHeader = new OrderHeader();
    orderHeader.setCustomer(customer);

    products.forEach(product -> {
      OrderLine orderLine = new OrderLine();
      orderLine.setProduct(product);
      orderLine.setQuantityOrdered(random.nextInt(20));

      orderHeader.addOrderLine(orderLine);
    });

    orderHeaderRepository.save(orderHeader);
  }

  private Customer loadCustomers() {
    return getOrSaveCustomer();
  }

  private Customer getOrSaveCustomer() {
    return customerRepository.findCustomerByCustomerNameIgnoreCase(TEST_CUSTOMER).orElseGet(() -> {
      Customer c1 = new Customer();
      c1.setCustomerName(TEST_CUSTOMER);
      c1.setEmail("test@example.com");
      Address address = new Address();
      address.setAddressLine("123 Main");
      address.setCity("New Orleans");
      address.setState("LA");
      c1.setAddress(address);
      return customerRepository.save(c1);
    });
  }

  private List<Product> loadProducts() {
    List<Product> products = new ArrayList<>();

    products.add(getOrSaveProduct(PRODUCT_D1));
    products.add(getOrSaveProduct(PRODUCT_D2));
    products.add(getOrSaveProduct(PRODUCT_D3));

    return products;
  }

  private Product getOrSaveProduct(String description) {
    return productRepository.findByDescription(description).orElseGet(() -> {
      Product p1 = new Product();
      p1.setDescription(description);
      p1.setProductStatus(ProductStatus.NEW);
      return productRepository.save(p1);
    });
  }
}
