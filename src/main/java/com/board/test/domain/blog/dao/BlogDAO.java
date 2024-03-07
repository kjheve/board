package com.board.test.domain.blog.dao;

import com.board.test.domain.entity.Blog;

import java.util.List;
import java.util.Optional;

public interface BlogDAO {
  // ★ 1) 게시글 작성
  Long save(Blog blog);

  // ★ 2) 1건 조회
  Optional<Blog> findByID(Long blogId);

  // ★ 3) 전체 조회(목록)
  List<Blog> findAll();

  // ★ 4) 1건 삭제
  int deleteById(Long blogId);

  // ★ 5) 여러건 삭제
  int deleteByIds(List<Long> blogIds);

  // ★ 6) 수정
  int updateById(Long blogId, Blog blog);

  // ☆ 7) 레코드 총 건수 (CSR)
  int totalCnt();
}
