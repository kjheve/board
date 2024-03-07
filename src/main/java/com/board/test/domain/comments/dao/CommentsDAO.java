package com.board.test.domain.comments.dao;

import com.board.test.domain.entity.Blog;
import com.board.test.domain.entity.Comments;

import java.util.List;
import java.util.Optional;

public interface CommentsDAO {
  // ★ 1) 댓글 작성
  Long save(Comments comments);

  // ★ 2) 해당 게시물 댓글 목록
  List<Comments> findAll(Long blogId);

  // ★ 3) 댓글 삭제
  int deleteById(Long commentsId);

  // ★ 4) 댓글 수정
  int updateById(Long commentsId, Comments comments);

  // ★ 5) 해당 게시물 댓글 총 레코드 건수
  int totalCnt(Long blogId);
}
