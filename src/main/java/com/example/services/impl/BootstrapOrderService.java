package com.example.services.impl;

import com.example.domain.OrderHeader;
import com.example.repositories.OrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BootstrapOrderService {

  private final OrderHeaderRepository orderHeaderRepository;

  @Transactional
  public void readOrderData() {
    orderHeaderRepository.findById(55L).ifPresent(this::execute);
  }

  private void execute(OrderHeader orderHeader) {
    orderHeader.getOrderLines().forEach(ol -> {
      log.info(ol.getProduct().getDescription());

      ol.getProduct().getCategories().forEach(cat -> log.info(cat.getDescription()));
    });
  }
}
