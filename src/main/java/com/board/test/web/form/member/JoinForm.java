package com.board.test.web.form.member;

import lombok.Data;

@Data
public class JoinForm {
  private String email;
  private String passwd;
  private String nickname;
}