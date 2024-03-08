package com.board.test.web;

import com.board.test.domain.comments.svc.CommentsSVC;
import com.board.test.domain.entity.Comments;
import com.board.test.web.apireq.comments.ReqSave;
import com.board.test.web.apireq.comments.ReqUpdate;
import com.board.test.web.apires.ApiResponse;
import com.board.test.web.apires.ResCode;
import com.board.test.web.apires.comments.ResSave;
import com.board.test.web.apires.comments.ResUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class ApiCommentController {

  private final CommentsSVC commentsSVC;

  // ☆댓글 등록 -----------------------------------------------------
//  @ResponseBody
  @PostMapping // POST, http://localhost:9080/api/comments
  public ApiResponse<?> saved(@RequestBody ReqSave reqSave) {
    log.info("reqSave = {}", reqSave); // test 완료
    // 1) 유효성 검증
    // 2) 댓글 등록 처리
    Comments comments = new Comments();
//    comments.setBlogId(reqSave.getBlogId());
//    comments.setCcontent(reqSave.getCcontent());
//    comments.setWriter(reqSave.getWriter());
    BeanUtils.copyProperties(reqSave, comments);
    Long commentsId = commentsSVC.save(comments);

    // 응답 객체 (body)
    ResSave resSave = new ResSave(commentsId, reqSave.getBlogId(), reqSave.getCcontent(), reqSave.getWriter());
    // 공통 응답 메세지 ApiResponse<>
    String rtDetail = "댓글번호 : " + commentsId + ", 댓글 등록 완료";
    ApiResponse<ResSave> res = ApiResponse.createApiResponseDetail(ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resSave);

    return res;
  }

  // ☆댓글 목록 -----------------------------------------------------
//  @GetMapping
//  public ApiResponse<?> list(@RequestParam("blogId") Long blogId) {
//    List<Comments> list = commentsSVC.findAll(blogId); // 해당 게시물 댓글 목록 찾기
//
//    ApiResponse<List<Comments>> res = null;
//    if (list.size() > 0) { // 해당 게시물의 댓글이 1개라도 존재시
//      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
//      int totalCnt = commentsSVC.totalCnt(blogId);
//      res.setTotalCnt(totalCnt); // 총 댓글 갯수 응답메세지로
//    } else { // 해당 게시물의 댓글이 0개면
//      res = ApiResponse.createApiResponseDetail(ResCode.FAIL.getCode(), ResCode.FAIL.name(), "댓글이 없는 게시물", list);
//    }
//
//    return res;
//  }

  // ☆댓글 목록 + 페이징 -----------------------------------------------------
  @GetMapping
  public ApiResponse<?> listMultiple(@RequestParam("blogId") Long blogId,
                                     @RequestParam("reqPage") Long reqPage,
                                     @RequestParam("recCnt") Long recCnt) {
    List<Comments> list = commentsSVC.findAll(blogId, reqPage, recCnt); // 해당 게시물 댓글 목록 찾기

    ApiResponse<List<Comments>> res = null;
    if (list.size() > 0) { // 해당 게시물의 댓글이 1개라도 존재시
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      int totalCnt = commentsSVC.totalCnt(blogId);
      res.setTotalCnt(totalCnt); // 총 댓글 갯수 응답메세지로

      res.setReqPage(reqPage.intValue());
      res.setRecCnt(recCnt.intValue());

    } else { // 해당 게시물의 댓글이 0개면
      res = ApiResponse.createApiResponseDetail(ResCode.FAIL.getCode(), ResCode.FAIL.name(), "댓글이 없는 게시물", list);
    }

    return res;
  }


  // ☆댓글 수정 -----------------------------------------------------
  @PatchMapping("/{commentId}")
  public ApiResponse<?> update(@PathVariable("commentId") Long commentId,
                               @RequestBody ReqUpdate reqUpdate) {
    log.info("productId = {}", commentId); // 수정할 데이터들이 왔는지 확인
    log.info("reqUpdate = {}", reqUpdate); // 요청 데이터 확인
    // 1) 유효성 체크

    // 2) 수정 처리
    Comments comments = new Comments();
    BeanUtils.copyProperties(reqUpdate, comments);

    int updateCnt = commentsSVC.updateById(commentId, comments);

    ApiResponse<ResUpdate> res = null; // 공통 응답메세지 바디에 수정한 응답메세지 초기화
    if (updateCnt == 1) {  // 업데이트 건수가 있으면
      ResUpdate resUpdate = new ResUpdate(); // 응답 객체
      BeanUtils.copyProperties(reqUpdate, resUpdate); // 요청 객체를 응답 객체에 넣기
      resUpdate.setCommentsId(commentId); // 응답 body에 댓글ID 넣기
      log.info("resUpdate = {}", resUpdate); // 응답 데이터 확인
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resUpdate); // 공통 응답메세지
    } else { // 업데이트 건수가 없으면
      String rtDetail = "수정 실패";
      res = ApiResponse.createApiResponseDetail(
              ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }
    return res;
  }

  // ☆댓글 삭제 -----------------------------------------------------
  @DeleteMapping("/{commentId}")
  public ApiResponse<?> delete(@PathVariable("commentId") Long commentId) {
    int deletedCnt = commentsSVC.deleteById(commentId);

    ApiResponse<ResUpdate> res = null;

    if (deletedCnt == 1) { // 삭제 건수가 1건이면
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else { // 삭제할 데이터가 없으면
      String rtDetail = "삭제 실패";
      res = ApiResponse.createApiResponseDetail(
              ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }
    return res;
  }

}
