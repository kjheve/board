package com.board.test.domain.member.dao;

import com.board.test.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  // 1) 회원가입
  Long joinMember(Member member);

  // 2-1) 이메일 존재 유무 (회원 아이디 조회)
  boolean existEmail(String email);

  // 2-2) 회원 조회 (이메일과 비밀번호를 입력받아서 조회하기)
  Optional<Member> findByEmailAndPasswd(String email, String passwd);

  // 3) 회원 수정

  // 4) 회원 탈퇴
}