package com.board.test.web.apireq.comments;

import lombok.Data;

// 댓글 저장시 요청

@Data
public class ReqSave {
  private Long blogId; // 게시글 id
  private String ccontent; // 댓글 내용
  private String writer; // 작성자
}
