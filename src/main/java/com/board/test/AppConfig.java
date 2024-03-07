package com.board.test;

import com.board.test.web.interceptor.LoginCheckInterCeptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class AppConfig implements WebMvcConfigurer {
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // 인증 체크
    registry.addInterceptor(new LoginCheckInterCeptor())
            .order(1) // 인터셉터 실행 순서 지정
            .addPathPatterns("/**") // 루트부터 하위 경로 모두
            .excludePathPatterns( // 인터셉터 제외 url 패턴
                    "/",
                    "/login",
                    "/logout",
                    "/members/join",
                    "/css/**", "/js/**", "/img/**",
                    "/test/**"
            );
  }
}
