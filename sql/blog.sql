-------- 삭제
-- 테이블 삭제
drop table comments;
drop table blog;
-- 시퀀스 삭제
drop sequence blog_blog_id_seq;
drop sequence comments_comments_id_seq;

---------
--게시판
---------
create table blog(
    blog_id     number(10),  -- 게시글id
    title       varchar(90), -- 제목
    bcontent    clob,        -- 내용
    writer      varchar(15), -- 작성자
    cdate       timestamp,   -- 작성날짜
    udate       timestamp    -- 수정날짜
);

---------
--댓글
---------
create table comments(
  comments_id     number(10), -- 댓글id
  blog_id         number(10), -- 게시글id
  ccontent        clob,        -- 내용
  writer          varchar(15), -- 작성자
  cdate           timestamp,   -- 작성날짜
  udate           timestamp    -- 수정날짜
);


--기본키
alter table blog add constraint blog_blog_id primary key(blog_id);
alter table comments add constraint comments_id primary key(comments_id);

--외래키
alter table comments add constraint comments_blog_id_fk
                      foreign key (blog_id) references blog;

--시퀀스생성
create sequence blog_blog_id_seq;
create sequence comments_comments_id_seq;

--디폴트
alter table blog modify cdate default systimestamp; -- 운영체제 일시를 기본값으로
alter table blog modify udate default systimestamp; -- 운영체제 일시를 기본값으로
alter table comments modify cdate default systimestamp; -- 운영체제 일시를 기본값으로
alter table comments modify udate default systimestamp; -- 운영체제 일시를 기본값으로

--필수값들 NOT NULL
alter table blog modify title not null;
alter table blog modify writer not null;
alter table blog modify bcontent not null;
alter table comments modify blog_id not null;
alter table comments modify writer not null;
alter table comments modify ccontent not null;

--생성--
-- 게시판 데이터
insert into blog(blog_id, title, bcontent, writer)
     values(blog_blog_id_seq.nextval, '하이', '냉무', '김하이');

insert into blog(blog_id, title, bcontent, writer)
     values(blog_blog_id_seq.nextval, 'TEST임', '제곧내', '테스트');

insert into blog(blog_id, title, bcontent, writer)
     values(blog_blog_id_seq.nextval, '자바', '공부중', '학생');

-- 댓글 데이터
insert into comments(comments_id, blog_id, ccontent, writer)
     values(comments_comments_id_seq.nextval, '1', '하이요', '테스트');
insert into comments(comments_id, blog_id, ccontent, writer)
     values(comments_comments_id_seq.nextval, '1', '김하이님 하이요', '학생');
insert into comments(comments_id, blog_id, ccontent, writer)
     values(comments_comments_id_seq.nextval, '2', '테스트님', '김하이');
insert into comments(comments_id, blog_id, ccontent, writer)
     values(comments_comments_id_seq.nextval, '2', '네 김하이님', '테스트');
insert into comments(comments_id, blog_id, ccontent, writer)
     values(comments_comments_id_seq.nextval, '1', '모두 안녕요', '김하이');
insert into comments(comments_id, blog_id, ccontent, writer)
     values(comments_comments_id_seq.nextval, '3', '왜 내 글엔 댓글이 없어', '학생');

commit;


-----------------------------------댓글 쿼리문 준비
-- 목록
select c.comments_id, c.blog_id, c.ccontent, c.writer, c.cdate, c.udate
  from comments c join blog b on c.blog_id = b.blog_id
  where c.blog_id = 1
  order by comments_id desc;

select count(*) from comments;
select * from comments;

-- 해당 게시물의 댓글 레코드 건수
select count(c.comments_id)
  from comments c join blog b on c.blog_id = b.blog_id
  where c.blog_id = 1;

--단건수정
update comments
  set ccontent = '테스트님아ㅏㅏ',
      udate = systimestamp
  where comments_id = 3;

--단건삭제
delete from comments
  where comments_id = 1;

-----------------------------------게시판 쿼리문 준비
--목록
select blog_id, title, bcontent, writer, cdate, udate
  from blog
  order by blog_id desc;

select count(*) from blog;

--단건조회
select blog_id, title, bcontent, writer, cdate, udate
  from blog
  where blog_id = 1;

--단건수정
update blog
  set title = 'DB공부중',
      bcontent = 'SQL공부하자',
      writer = '대학생',
      udate = systimestamp
  where blog_id = 2;

--단건삭제
delete from blog
  where blog_id = 1;

--여러건 삭제
delete from blog
  where blog_id in ( 1, 2, 3 );


rollback;
