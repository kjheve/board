package com.board.test.web.apires;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

// 공통 응답 메세지

@Getter
@ToString
public class ApiResponse<T> {
  private Header header; // 헤더
  private T body; // 바디
  private int totalCnt; // 데이터 갯수
  private int recCnt; // 1페이지 보여줄 레코드 수
  private int reqPage; // 요청 페이지

  private ApiResponse(Header header, T body) {
    this.header = header;
    this.body = body;
  }

  @Getter
  @ToString
  @AllArgsConstructor
  private static class Header {
    private String rtcd; // 응답 코드
    private String rtmsg; // 응답 메세지
    private String rtdetail; // 응답 세부메세지

    // createApiResponse()에 파라미터 2개만 받는거 때문에 생성자 생성
    public Header(String rtcd, String rtmsg) {
      this.rtcd = rtcd;
      this.rtmsg = rtmsg;
    }
  }

  public static <T> ApiResponse<T> createApiResponse(String rtcd, String rtmsg, T body) {
    return new ApiResponse<T>(new Header(rtcd, rtmsg), body);
  }

  public static <T> ApiResponse<T> createApiResponseDetail(String rtcd, String rtmsg, String rtdetail, T body) {
    return new ApiResponse<T>(new Header(rtcd, rtmsg, rtdetail), body);
  }

  public void setTotalCnt(int totalCnt) {
    this.totalCnt = totalCnt;
  }

  public void setRecCnt(int recCnt) {
    this.recCnt = recCnt;
  }

  public void setReqPage(int reqPage) {
    this.reqPage = reqPage;
  }
}
