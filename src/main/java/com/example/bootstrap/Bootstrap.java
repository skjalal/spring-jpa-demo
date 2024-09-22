package com.example.bootstrap;

import com.example.domain.Customer;
import com.example.domain.Product;
import com.example.domain.ProductStatus;
import com.example.repositories.CustomerRepository;
import com.example.services.ProductService;
import com.example.services.impl.BootstrapOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class Bootstrap implements CommandLineRunner {

  private final BootstrapOrderService bootstrapOrderService;
  private final CustomerRepository customerRepository;
  private final ProductService productService;

  private void updateProduct() {
    Product product = new Product();
    product.setDescription("My Product");
    product.setProductStatus(ProductStatus.NEW);

    Product savedProduct = productService.saveProduct(product);

    Product savedProduct2 = productService.updateQOH(savedProduct.getId(), 25);

    log.info("Updated Qty: {}", savedProduct2.getQuantityOnHand());
  }

  @Override
  public void run(String... args) {
    updateProduct();

    bootstrapOrderService.readOrderData();

    Customer customer = new Customer();
    customer.setCustomerName("Testing Version");
    Customer savedCustomer = customerRepository.save(customer);
    logVersion(savedCustomer.getVersion());

    savedCustomer.setCustomerName("Testing Version 2");
    Customer savedCustomer2 = customerRepository.save(savedCustomer);
    logVersion(savedCustomer2.getVersion());

    savedCustomer2.setCustomerName("Testing Version 3");
    Customer savedCustomer3 = customerRepository.save(savedCustomer2);
    logVersion(savedCustomer3.getVersion());

    customerRepository.delete(savedCustomer3);
  }

  private void logVersion(Integer version) {
    log.info("Version is: {}", version);
  }
}
