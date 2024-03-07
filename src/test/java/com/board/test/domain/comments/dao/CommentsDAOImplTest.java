package com.board.test.domain.comments.dao;

import com.board.test.domain.entity.Comments;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CommentsDAOImplTest {

  @Autowired
  CommentsDAO commentsDAO;

  @Test
  @DisplayName("댓글 작성")
  void save() {
    Comments comments = new Comments();
    comments.setBlogId(3L);
    comments.setCcontent("하이요");
    comments.setWriter("하이맨");

    Long commentsId = commentsDAO.save(comments);
    log.info("commentsId = {}", commentsId);
  }

  @Test
  @DisplayName("해당 게시물의 댓글 목록")
  void findAll() {
    Long blogId = 1L; // 1번의 게시물 댓글
    List<Comments> list = commentsDAO.findAll(blogId);

    // 게시물 blogId의 댓글 목록
    for (Comments comments : list) {
      log.info("comments = {}", comments);
    }

    // 그 게시물의 댓글 갯수
    log.info("size = {}", list.size());
  }

  @Test
  @Transactional
  @DisplayName("1건 삭제")
  void deleteById() {
    int delRowCnt = commentsDAO.deleteById(1L);
    log.info("삭제건수확인 = {}", delRowCnt);
    // delRowCnt의 값이 1과 동일한지 확인
    Assertions.assertThat(delRowCnt).isEqualTo(1);
  }

  @Test
  @DisplayName("댓글 수정")
  void updateById() {
    Long id = 1L;
    Comments c = new Comments();
    c.setCcontent("호잇");
    c.setWriter("뿌잇");

    int updateRowCnt = commentsDAO.updateById(id, c);
    log.info("updateRowCnt={}", updateRowCnt);

    if(updateRowCnt == 0) {
      Assertions.fail("수정 내역 없음");
    }
  }

  @Test
  @DisplayName("총 레코드 건수")
  void totalCnt() {
    int totalCnt = commentsDAO.totalCnt(3L);
    log.info("totalCnt = {}", totalCnt);

    List<Comments> list = commentsDAO.findAll(3L);

    Assertions.assertThat(totalCnt).isEqualTo(list.size());
  }
}
