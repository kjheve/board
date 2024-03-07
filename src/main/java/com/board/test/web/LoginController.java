package com.board.test.web;

import com.board.test.domain.entity.Member;
import com.board.test.domain.member.svc.MemberSVC;
import com.board.test.web.form.member.LoginForm;
import com.board.test.web.form.member.LoginMember;
import com.board.test.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
  private final MemberSVC memberSVC;

  // 로그인 ★양식★ ------------------------------------------
  @GetMapping("/login")
  public String loginForm() {
    return "member/loginForm";
  }

  // 로그인 ★처리★ ------------------------------------------
  @PostMapping("/login")
  public String login(LoginForm loginForm, HttpServletRequest request,
                      @RequestParam(value = "redirectUrl", required = false) String redirectUrl) { // 로그인 했을 때 해당 쿼리파라미터로
    log.info("loginForm={}", loginForm);
    // 1. 유효성 체크

    // 2. 회원 유무 체크
    // 2-1. 회원 아이디 존재 유무 체크
    if (memberSVC.existEmail(loginForm.getEmail())) {
      Optional<Member> byEmailAndPasswd = memberSVC.findByEmailAndPasswd(loginForm.getEmail(), loginForm.getPasswd());
      // 2-2. 회원 조회가 된 경우 회원정보를 세션에 저장
      if (byEmailAndPasswd.isPresent()) {
        // 세션 생성
        HttpSession session = request.getSession(true);
        // 회원 정보를 세션에 저장
        Member member = byEmailAndPasswd.get();
        LoginMember loginMember = new LoginMember(member.getMember_id(), member.getEmail(), member.getNickname(), member.getGubun());
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
      }
    } else {
      return "member/loginForm";
    }
    return redirectUrl != null ? "redirect:"+redirectUrl : "redirect:/";
  }

  // 로그아웃 ★처리★ ------------------------------------------
  @ResponseBody
  @PostMapping("/logout")
  public String logout(HttpServletRequest request) {
    String flag = "NoOK";
    // 세션이 있으면 가져오고 없으면 생성하지 않음
    HttpSession session = request.getSession(false);

    if (session != null) {
      session.invalidate(); // 세션제거
      flag = "OK";
    }
    return flag;
  }


}


