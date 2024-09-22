package com.example.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class OrderHeaderTest {

  @Test
  void testEquals() {
    OrderHeader oh1 = new OrderHeader();
    oh1.setId(1L);

    OrderHeader oh2 = new OrderHeader();
    oh2.setId(1L);

    assertEquals(oh1, oh2);
  }

  @Test
  void testNotEquals() {
    OrderHeader oh1 = new OrderHeader();
    oh1.setId(1L);

    OrderHeader oh2 = new OrderHeader();
    oh2.setId(3L);

    assertNotEquals(oh1, oh2);
  }
}