package com.board.test.domain.comments.dao;

import com.board.test.domain.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class CommentsDAOImpl implements CommentsDAO{

  private final NamedParameterJdbcTemplate template;

  // ★ 1) 댓글 작성
  @Override
  public Long save(Comments comments) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into comments(comments_id, blog_id, ccontent, writer) ");
    sql.append("values(comments_comments_id_seq.nextval, :blogId, :ccontent, :writer) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(comments);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"comments_id"});
    long commentsId = keyHolder.getKey().longValue();

    return commentsId;
  }

  // ★ 2) 해당(blogId) 게시물 댓글 목록
  @Override
  public List<Comments> findAll(Long blogId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select c.comments_id, c.blog_id, c.ccontent, c.writer, c.cdate, c.udate ");
    sql.append("from comments c join blog b on c.blog_id = b.blog_id ");
    sql.append("where c.blog_id = :blogId ");
    sql.append("order by comments_id desc ");

    try {
      // (:blogId) 파라미터와 값을 매핑하는 Map 생성
      Map<String, Long> map = Map.of("blogId", blogId);
      List<Comments> list = template.query(sql.toString(), map,
              BeanPropertyRowMapper.newInstance(Comments.class));
      return list;
    } catch (EmptyResultDataAccessException e) {
      // 조회 결과가 없는 경우
      return List.of();
    }
  }

  // ★ 3) 댓글 삭제
  @Override
  public int deleteById(Long commentsId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from comments ");
    sql.append("where comments_id = :commentsId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("commentsId", commentsId);
    int deleteRowCnt = template.update(sql.toString(), param);

    return deleteRowCnt;
  }

  // ★ 4) 댓글 수정
  @Override
  public int updateById(Long commentsId, Comments comments) {
    StringBuffer sql = new StringBuffer();
    sql.append(" update comments ");
    sql.append(" set ccontent = :ccontent, ");
    sql.append("          udate = default ");
    sql.append(" where comments_id = :commentsId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("ccontent", comments.getCcontent())
            .addValue("commentsId", commentsId);
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }

  // ★ 5) 해당 게시물 총 댓글 레코드 수
  @Override
  public int totalCnt(Long blogId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select count(c.comments_id) ");
    sql.append("from comments c join blog b on c.blog_id = b.blog_id ");
    sql.append("where c.blog_id = :blogId ");

    SqlParameterSource param = new MapSqlParameterSource("blogId", blogId);
    Integer cnt = template.queryForObject(sql.toString(), param, Integer.class);

    return cnt;
  }
}
