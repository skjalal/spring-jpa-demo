package com.example.repositories;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.domain.Product;
import com.example.domain.ProductStatus;
import com.example.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@DataJpaTest
@ComponentScan(basePackageClasses = {ProductService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

  @Autowired
  ProductRepository productRepository;

  @Autowired
  ProductService productService;

  @Test
  void testGetCategory() {
    Product product = productRepository.findByDescription("PRODUCT1").orElse(null);

    assertNotNull(product);
    assertNotNull(product.getCategories());

  }

  @Test
  void testSaveProduct() {
    Product product = new Product();
    product.setDescription("My Product");
    product.setProductStatus(ProductStatus.NEW);

    Product savedProduct = productRepository.save(product);

    Product fetchedProduct = productRepository.findById(savedProduct.getId()).orElse(null);

    assertNotNull(fetchedProduct);
    assertNotNull(fetchedProduct.getDescription());
    assertNotNull(fetchedProduct.getCreatedDate());
    assertNotNull(fetchedProduct.getLastModifiedDate());
  }

  @Test
  void addAndUpdateProduct() {
    Product product = new Product();
    product.setDescription("My Product");
    product.setProductStatus(ProductStatus.NEW);

    Product savedProduct = productService.saveProduct(product);

    Product savedProduct2 = productService.updateQOH(savedProduct.getId(), 25);

    assertNotNull(savedProduct2);
    System.out.println(savedProduct2.getQuantityOnHand());
  }
}