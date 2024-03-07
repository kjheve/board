package com.board.test.web.apires.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 댓글 등록시 응답

@AllArgsConstructor
@Getter
public class ResSave {
  private Long commentsId;
  private Long blogId;
  private String ccontents;
  private String writer;
}
