package com.board.test.web.apires.comments;

// 댓글 수정, 삭제 시 응답

import lombok.Data;

@Data
public class ResUpdate {
  private Long commentsId;
  private String ccontent;
}
