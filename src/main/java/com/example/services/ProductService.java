package com.example.services;

import com.example.domain.Product;

public interface ProductService {

  Product saveProduct(Product product);

  Product updateQOH(Long id, Integer quantityOnHand);
}
