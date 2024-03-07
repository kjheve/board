package com.board.test.web;

import com.board.test.domain.blog.svc.BlogSVC;
import com.board.test.domain.entity.Blog;
import com.board.test.web.form.blog.AddForm;
import com.board.test.web.form.blog.UpdateForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/blog") // URL매핑 http://localhost:9080/blog
public class BlogController {
  private BlogSVC blogSVC;
  public BlogController(BlogSVC blogSVC) {
    this.blogSVC = blogSVC;
  }

  // ★ 3) 게시판 목록
  @GetMapping // GET, http://localhost:9080/blog
  public String findAll(Model model) {
    List<Blog> list = blogSVC.findAll();
    model.addAttribute("list", list);
    return "blog/all";
  }

  // ★ 1) 게시판 등록 양식
  @GetMapping("/add") // Get, http://localhost:9080/blog/add
  public String addForm(Model model) {
    model.addAttribute("addForm", new AddForm());
    return "blog/add";
  }
  
  // ★ 2) 게시판 등록 처리
  @PostMapping("/add") // add.html의 Post요청시, http://localhost:9080/blog/add
  public String add(AddForm addForm,
                    Model model,
                    RedirectAttributes redirectAttributes) {



    // 제목 유효성 체크 ------------------- (Server)
    String pattern = "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\s]{1,30}$";
    if(!Pattern.matches(pattern, addForm.getTitle())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_title", "특수문자 입력안됨(길이30자이내)(S)");
      return "blog/add";
    }
    // 작성자 유효성 체크 ------------------- (Server)
    pattern = "^[a-zA-Z0-9ㄱ-ㅎ가-힣_\\-]{1,20}$";
    if(!Pattern.matches(pattern, addForm.getWriter())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_writer", "특문 '-','_'만 사용가능(길이20자이내)(S)");
      return "blog/add";
    }
    // 내용 유효성 체크 ------------------- (Server)
    pattern = "^.{2,}$";
    if(!Pattern.matches(pattern, addForm.getBcontent())) {
      model.addAttribute("addForm", addForm);
      model.addAttribute("s_err_bcontent", "2글자 이상 입력(S)");
      return "blog/add";
    }

    // 게시글 등록
    Blog blog = new Blog();
    blog.setTitle(addForm.getTitle());
    blog.setWriter(addForm.getWriter());
    blog.setBcontent(addForm.getBcontent());

    Long blogId = blogSVC.save(blog);

//    // ★ 2-2) 게시판 조회 -> 새로고침 시 POST 요청이 계속 되어 아래와 같이
//    Optional<Blog> findedBlog = blogSVC.findByID(blogId);
//    blog = findedBlog.orElseThrow();
//    model.addAttribute("blog", blog);
//    return "blog/detailForm";

    // ★ 2-3) POST 요청시 redirect를 이용하여 경로변수로 설정
    // add() 매개변수 RedirectAttributes 추가
    redirectAttributes.addAttribute("bid", blogId);
    return "redirect:/blog/{bid}/detail";
  }

  // ★ 4) 조회
  @GetMapping("/{bid}/detail") // GET, http://localhost:9080/blog/게시글번호/detail
  public String findById(@PathVariable("bid") Long blogId, Model model) {
    Optional<Blog> findedBlogId = blogSVC.findByID(blogId);
    Blog blog = findedBlogId.orElseThrow();
    model.addAttribute("blog", blog);

    return "blog/detailForm";
  }

  // ★ 5) 삭제
  @GetMapping("/{bid}/del")
  public String delete(@PathVariable("bid") Long blogId) {
    // 게시글 삭제 처리
    blogSVC.deleteById(blogId);

    return "redirect:/blog"; // 게시글 목록으로
  }

  // ★ 6) 여러건 삭제
  @PostMapping("/del") // all.html의 POST요청시, http://localhost:9080/blog/del
  public String deletedByIds(@RequestParam("bids") List<Long> bids) {
    blogSVC.deleteByIds(bids);

    return "redirect:/blog";
  }

  // ★ 7) 게시판 수정 양식
  @GetMapping("/{bid}/edit")
  public String updateForm(@PathVariable("bid") Long blogId, Model model) {
    Optional<Blog> modifiedBlog = blogSVC.findByID(blogId); // 수정할 게시글 번호
    Blog findBlog = modifiedBlog.orElseThrow(); // 게시글 번호의 정보 찾아오기
    model.addAttribute("blog", findBlog); // Model에 속성이름으로 불러오기

    return "blog/updateForm";
  }
  
  // ★ 8) 수정 처리
  @PostMapping("/{bid}/edit")
  public String update(@PathVariable("bid") Long blogId,
                       UpdateForm updateForm,
                       RedirectAttributes redirectAttributes,
                       Model model) {

    // 제목 유효성 체크 ------------------- (Server)
    String pattern = "^[a-zA-Z0-9ㄱ-ㅎ가-힣\\s]{1,30}$";
    if(!Pattern.matches(pattern, updateForm.getTitle())) {
      model.addAttribute("blog", updateForm);
      model.addAttribute("s_err_title", "특수문자 입력안됨(길이30자이내)(S)");
      return "/blog/updateForm";
    }
    // 내용 유효성 체크 ------------------- (Server)
    pattern = "^.{2,}$";
    if(!Pattern.matches(pattern, updateForm.getBcontent())) {
      model.addAttribute("blog", updateForm);
      model.addAttribute("s_err_bcontent", "2글자 이상 입력(S)");
      return "/blog/updateForm";
    }


    // 게시글 수정
    Blog blog = new Blog();
    blog.setTitle(updateForm.getTitle());
    blog.setBcontent(updateForm.getBcontent());

    blogSVC.updateById(blogId, blog);

    redirectAttributes.addAttribute("bid", blogId);
    return "redirect:/blog/{bid}/detail";
  }

  
}
