package com.example.services.impl;

import com.example.domain.Product;
import com.example.repositories.ProductRepository;
import com.example.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Product saveProduct(Product product) {
    return productRepository.saveAndFlush(product);
  }

  @Transactional
  @Override
  public Product updateQOH(Long id, Integer quantityOnHand) {
    Product product = productRepository.findById(id).orElseThrow();

    product.setQuantityOnHand(quantityOnHand);

    return productRepository.saveAndFlush(product);
  }
}
