package com.board.test.web;

import com.board.test.domain.pubdata.NaverNews;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/pubdata")
@RequiredArgsConstructor // final 필드를 매개변수값으로 생성자를 자동 만들어줌
public class PubdataController {


  private final NaverNews naverNews;
//  @Autowired
//  public PubdataController(NaverNews naverNews) {
//    this.naverNews = naverNews;
//  }

  @GetMapping("/news") // GET, http://localhost:9080/pubdata/news
  public String news() {
    return "pubdata/news";
  }

  @ResponseBody
  @GetMapping("/news/search")
  public String search(@RequestParam("keyword") String keyword) {
    log.info("keyword={}", keyword);
    String data = naverNews.reqNews(keyword);
    return data; // body에 바로 쏘려고 @ResponseBody 어노테이션도 추가
  }
}