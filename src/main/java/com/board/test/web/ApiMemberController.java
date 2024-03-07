package com.board.test.web;

import com.board.test.domain.member.svc.MemberSVC;
import com.board.test.web.apireq.member.ReqExitsEmail;
import com.board.test.web.apires.ApiResponse;
import com.board.test.web.apires.ResCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class ApiMemberController {
  private final MemberSVC memberSVC;

  // 회원 중복 체크 ----------------------------------
  @PostMapping("/dupchk")
  public ApiResponse<?> dupchk(@RequestBody ReqExitsEmail reqExitsEmail) {
    ApiResponse<?> res = null;

    log.info("reqExitsEmail = {}", reqExitsEmail);
    // 같은 이메일 존재시 응답코드 21 / 존재 안할시 응답코드 22
    if (memberSVC.existEmail(reqExitsEmail.getEmail())) {
      res = ApiResponse.createApiResponse(ResCode.EXIST.getCode(), ResCode.EXIST.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.NONE.getCode(), ResCode.NONE.name(), null);
    }
    return res;
  }
}
