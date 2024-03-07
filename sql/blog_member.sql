-------- 삭제
-- 테이블 삭제
drop table member;
-- 시퀀스 삭제
drop sequence member_member_id_seq;

-- 회원테이블 생성
create table member(
    member_id     number(10),    -- 내부관리 아이디
    email         varchar2(50),  -- 로그인 아이디
    passwd        varchar2(12),  -- 로그인 비밀번호
    nickname      varchar2(30),  -- 별칭
    gubun         varchar(11),   -- default 'M0101', -- 회원구분(일반, 우수, 관리자1, 관리자2...)
    pic           blob,          -- 사진
    cdate         timestamp,     -- 생성일시, 가입일
    udate         timestamp      -- 수정일시
);

-- 기본키 생성
alter table member add constraint member_member_id_pk primary key(member_id);

--시퀀스생성
create sequence member_member_id_seq;

--디폴트
alter table member modify cdate default systimestamp; -- 운영체제 일시를 기본값으로
alter table member modify udate default systimestamp; -- 운영체제 일시를 기본값으로
alter table member modify gubun default 'M0101'; -- 회원구분

-- 유니크
alter table member modify email constraint member_email_uk unique;


--필수값들 NOT NULL
alter table member modify email not null;
alter table member modify passwd not null;
alter table member modify nickname not null;


--샘플데이터 생성
insert into member(member_id,email,passwd,nickname)
     values(member_member_id_seq.nextval, 'admin@naver.com', 'admin', '김하이');
insert into member(member_id,email,passwd,nickname)
     values(member_member_id_seq.nextval, 'user01@naver.com', 'user01', '학생');
insert into member(member_id,email,passwd,nickname)
     values(member_member_id_seq.nextval, 'user02@naver.com', 'user02', '테스트');

commit;




-- 쿼리문 준비
select * from member;