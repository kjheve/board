package com.board.test.domain.pubdata;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class NaverNewsTest {
  @Autowired
  private NaverNews news;

  @Test
  void reqNews() {
    String data = news.reqNews("액침냉각");
    log.info("data={}", data);
  }
}