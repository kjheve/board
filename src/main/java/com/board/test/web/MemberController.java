package com.board.test.web;

import com.board.test.domain.entity.Member;
import com.board.test.domain.member.svc.MemberSVC;
import com.board.test.web.form.member.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;

  // 회원가입 ★양식★ ------------------------------------------
  @GetMapping("/join")
  public String joinForm() {
    return "member/joinForm";
  }

  // 회원가입 ★처리★ ------------------------------------------
  @PostMapping("/join")
  public String join(JoinForm joinForm) {
    log.info("joinForm={}", joinForm);

    // 1) 유효성 검증

    // 2) 가입처리
    Member member = new Member();
    BeanUtils.copyProperties(joinForm, member); // (데이터, 타겟)
//    member.setEmail(joinForm.getEmail());
//    member.setPasswd(joinForm.getPasswd());
//    member.setNickname(joinForm.getNickname());
    Long memberId = memberSVC.joinMember(member);
    // 회원 가입 후 가입처리가 되면 로그인 화면 아니면 다시 회원가입 화면
    return (memberId != null) ? "redirect:/login" : "/redirect:/join";
  }
}
