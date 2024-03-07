package com.board.test.web.interceptor;

import com.board.test.web.form.member.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
public class LoginCheckInterCeptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // 리다이렉트 URL만들기
    String redirectUrl = null;

    // URI주소 불러오기
    String requestURI = request.getRequestURI(); // blog

    log.info("요청uri = {}", requestURI);               // ex) /blog/add
    log.info("요청url = {}", request.getRequestURL());  // ex) http://localhost:9080/blog/add

    // §요청 파라미터 정보가 있는 경우 ex) http://localhost:9080/blog?bid=1
    if(request.getQueryString() != null) { // getQueryString() = ?뒤에 파라미터 가져오기
      // 요청 쿼리파라미터 인코딩
      String encode = URLEncoder.encode(request.getQueryString(), "UTF-8");
      // StringBuffer로 URL 주소 완성시키기
      StringBuffer stringBuff = new StringBuffer();
      redirectUrl = stringBuff.append(requestURI).append("?").append(encode).toString();
    } else { // 요청 파라미터가 없는 경우
      redirectUrl = requestURI;
    }

    // 세션 정보 읽어오기
    HttpSession session = request.getSession(false); // false 있으면 해당세션 가져오고 없으면 null
    if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
      log.info("미인증 요청");
      response.sendRedirect("/login?redirectUrl="+redirectUrl);
      return false; // 다음 인터셉터 수행X
    }
    return true;
  }
}
