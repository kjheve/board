package com.board.test.domain.blog.dao;

import com.board.test.domain.entity.Blog;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class BlogDAOImplTest {

  @Autowired // IOC컨테이너에 객체를 주입
  BlogDAO blogDAO;

  @Test
  @DisplayName("게시글 작성")
  void save() {
    Blog blog = new Blog();
    blog.setTitle("Spring 공부하실분");
    blog.setBcontent("공부하실분 카톡하세요");
    blog.setWriter("김프링");

    Long blogId = blogDAO.save(blog);
    log.info("blogId={}", blogId);
  }

  @Test
  @DisplayName("게시글 작성")
  void saveMutiple() {
    for (int i = 1; i < 153; i++) {
      Blog blog = new Blog();
      blog.setTitle("Spring 공부하실분"+i);
      blog.setBcontent("공부하실분 카톡하세요"+(i*5));
      blog.setWriter("김프링"+i);
      blogDAO.save(blog);
    }
  }

  @Test
  @DisplayName("게시글 1건 조회")
  void findByID() {
    Long blogId = 1L;
    Optional<Blog> findedBlog = blogDAO.findByID(blogId);
//    log.info("blog = {}", findedBlog.get());
    log.info("blog = {}", findedBlog.orElse(null));
  }

  @Test
  @DisplayName("게시글 전체 조회")
  void findAll() {
    List<Blog> list = blogDAO.findAll();
    for (Blog blog : list) {
      log.info("blog={}", blog);
    }
    log.info("size={}", list.size());
  }

  @Test
  @DisplayName("1건 삭제")
  void deleteById() {
    int delRowCnt = blogDAO.deleteById(1L);
    log.info("삭제 건수 확인={}", delRowCnt);
    // delRowCnt의 값이 1과 동일한지 확인
    Assertions.assertThat(delRowCnt).isEqualTo(1);

  }

  @Test
  @DisplayName("여러건 삭제")
  void deleteByIds() {
    List<Long> bids = new ArrayList<>();
    bids.add(1L);
    bids.add(2L);
    bids.add(3L);
    int delRowCnts = blogDAO.deleteByIds(bids);
    log.info("삭제 건수 확인={}", delRowCnts);
    Assertions.assertThat(delRowCnts).isEqualTo(bids.size());
  }

  @Test
  @DisplayName("게시물 수정")
  void updateById() {

    Long blogId = 1L;
    Blog blog = new Blog();
    blog.setTitle("애국가1절");
    blog.setBcontent("동해물과백두산이");
    blog.setWriter("애국자");

    int updateRowCnt = blogDAO.updateById(blogId, blog);
    log.info("updateRowCnt={}", updateRowCnt);

    if(updateRowCnt == 0) {
      Assertions.fail("수정 내역 없음");
    }
    Optional<Blog> optionalProduct = blogDAO.findByID(blogId);
    if (optionalProduct.isPresent()) {
      Blog findedProduct = optionalProduct.get();
      Assertions.assertThat(findedProduct.getTitle()).isEqualTo("애국가1절");
      Assertions.assertThat(findedProduct.getBcontent()).isEqualTo("동해물과백두산이");
      Assertions.assertThat(findedProduct.getWriter()).isEqualTo("애국자");
    } else {
      Assertions.fail("수정할 게시물이 없습니다");
    }
  }
}