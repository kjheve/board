// Server측 데이터 유효성 검사
package com.board.test.web.form.blog;

import lombok.Data;

@Data
public class UpdateForm {
  // 게시글 수정시 유효성 검사 속성
  private Long blogId;
  private String title;     // 제목
  private String writer;    // 작성자
  private String bcontent;  // 내용
}
