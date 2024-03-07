package com.board.test.web.apireq.comments;

import lombok.Data;

@Data
public class ReqUpdate {
  private String ccontent; // 댓글 내용
  private String writer; // 작성자
}
