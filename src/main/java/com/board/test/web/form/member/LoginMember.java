package com.board.test.web.form.member;

// ★세션에 정보를 저장★하려고 클래스 생성

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginMember {
  private Long memberId;
  private String email;
  private String nickname;
  private String gubun;

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public void setGubun(String gubun) {
    this.gubun = gubun;
  }

}
