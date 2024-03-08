package com.board.test.domain.blog.dao;

import com.board.test.domain.entity.Blog;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class BlogDAOImpl implements BlogDAO {

  private final NamedParameterJdbcTemplate template;

  public BlogDAOImpl(NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  // ★1) 게시글 작성--------------------------------------------------
  @Override
  public Long save(Blog blog) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into blog(blog_id,title,bcontent,writer) ");
    sql.append("values(blog_blog_id_seq.nextval, :title, :bcontent, :writer) ");

    SqlParameterSource param = new BeanPropertySqlParameterSource(blog);
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sql.toString(), param, keyHolder, new String[]{"blog_id"});
    long blogId = keyHolder.getKey().longValue();
    return blogId;
  }

  // ★2) 1건(단건) 조회----------------------------------------------
  @Override
  public Optional<Blog> findByID(Long blogId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select blog_id, title, bcontent, writer, cdate, udate ");
    sql.append("from blog ");
    sql.append("where blog_id = :blogId ");

    try {
      SqlParameterSource param = new MapSqlParameterSource()
              .addValue("blogId", blogId);
      Blog blog = template.queryForObject(sql.toString(), param, BeanPropertyRowMapper.newInstance(Blog.class));
      return Optional.of(blog);
    } catch (EmptyResultDataAccessException err) {
      // 조회 Null인 경우
      return Optional.empty();
    }
  }

  // ★3) 전체 조회----------------------------------------------
  @Override
  public List<Blog> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select blog_id, title, bcontent, writer, cdate, udate ");
    sql.append("from blog ");
    sql.append("order by blog_id desc ");

    List<Blog> list = template.query(sql.toString(), BeanPropertyRowMapper.newInstance(Blog.class));
    return list;
  }
  // ★3-1) 전체 조회(페이징)----------------------------------------------
  @Override
  public List<Blog> findAll(Long reqPage, Long recCnt) {
    StringBuffer sql = new StringBuffer();
    sql.append("select blog_id, title, bcontent, writer, cdate, udate ");
    sql.append("from blog ");
    sql.append("order by blog_id desc ");
    sql.append("offset (:reqPage-1) * :recCnt rows ");
    sql.append("fetch first :recCnt rows only ");

    try {
      Map<String, Long> map = Map.of("reqPage", reqPage, "recCnt", recCnt);
      List<Blog> list = template.query(sql.toString(), map,
              BeanPropertyRowMapper.newInstance(Blog.class));
      return list;
    } catch (EmptyResultDataAccessException e) {
      // 조회 결과가 없는 경우
      return List.of();
    }
  }

  // ★4) 1건 삭제----------------------------------------------
  @Override
  public int deleteById(Long blogId) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from blog ");
    sql.append("where blog_id = :blogId ");

    Map<String, Long> map = Map.of("blogId", blogId);
    int deleteRowCnt = template.update(sql.toString(), map);

    return deleteRowCnt;
  }

  // ★5) 여러건 삭제----------------------------------------------
  @Override
  public int deleteByIds(List<Long> blogIds) {
    StringBuffer sql = new StringBuffer();
    sql.append("delete from blog ");
    sql.append("where blog_id in (:blogIds) ");

    Map<String, List<Long>> map = Map.of("blogIds", blogIds);
    int delRowCnts = template.update(sql.toString(), map);

    return delRowCnts;
  }

  // ★6) 수정----------------------------------------------
  @Override
  public int updateById(Long blogId, Blog blog) {
    StringBuffer sql = new StringBuffer();
    sql.append("update blog ");
    sql.append("set title = :title, ");
    sql.append("    bcontent = :bcontent, ");
    sql.append("    udate = default ");
    sql.append("where blog_id = :blogId ");

    SqlParameterSource param = new MapSqlParameterSource()
            .addValue("title", blog.getTitle())
            .addValue("bcontent", blog.getBcontent())
            .addValue("blogId", blogId);
    int updateRowCnt = template.update(sql.toString(), param);

    return updateRowCnt;
  }

  // ☆7) 레코드 총 건수----------------------------------------------
  @Override
  public int totalCnt() {
    String sql = "select count(blog_id) from blog ";
    SqlParameterSource param = new MapSqlParameterSource();
    Integer cnt = template.queryForObject(sql, param, Integer.class);
    return cnt;
  }
}
