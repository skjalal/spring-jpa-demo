package com.example.repositories;

import com.example.domain.Product;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByDescription(String description);

  @NonNull
  @Override
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  Optional<Product> findById(@NonNull Long id);
}
