package com.board.test.domain.comments.svc;

import com.board.test.domain.comments.dao.CommentsDAO;
import com.board.test.domain.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsSVCImpl implements CommentsSVC{
  private final CommentsDAO commentsDAO;
  // 댓글 등록
  @Override
  public Long save(Comments comments) {
    return commentsDAO.save(comments);
  }

  // 댓글 목록
  @Override
  public List<Comments> findAll(Long blogId) {
    return commentsDAO.findAll(blogId);
  }

  // 댓글 삭제
  @Override
  public int deleteById(Long commentsId) {
    return commentsDAO.deleteById(commentsId);
  }

  // 댓글 수정
  @Override
  public int updateById(Long commentsId, Comments comments) {
    return commentsDAO.updateById(commentsId, comments);
  }

  // 댓글 총 건수
  @Override
  public int totalCnt(Long blogId) {
    return commentsDAO.totalCnt(blogId);
  }
}
