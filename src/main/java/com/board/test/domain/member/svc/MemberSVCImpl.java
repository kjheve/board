package com.board.test.domain.member.svc;

import com.board.test.domain.entity.Member;
import com.board.test.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSVCImpl implements MemberSVC{

  private final MemberDAO memberDAO;

  // 1. 회원가입
  @Override
  public Long joinMember(Member member) {
    return memberDAO.joinMember(member);
  }

  // 2-1. 이메일 존재 유무
  @Override
  public boolean existEmail(String email) {
    return memberDAO.existEmail(email);
  }

  // 2-2. 회원 확인
  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    return memberDAO.findByEmailAndPasswd(email, passwd);
  }
}
