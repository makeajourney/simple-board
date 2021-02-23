-- user
insert into user (email, password, `role`) values ('rose@carrotins.com', '{noop}1234', 'USER');
insert into user (email, password, `role`) values ('hailey@carrotins.com', '{noop}1234', 'USER');
insert into user (email, password, `role`) values ('anderson@carrotins.com', '{noop}1234', 'USER');

-- post
insert into post (user_id, title, content, created_datetime, modified_datetime) values (1, '테스트 게시글 제목 1', '테스트 게시글 내용 1', now(), now());
insert into post (user_id, title, content, created_datetime, modified_datetime) values (2, '테스트 게시글 제목 2', '테스트 게시글 내용 2', now(), now());
insert into post (user_id, title, content, created_datetime, modified_datetime) values (3, '테스트 게시글 제목 3', '테스트 게시글 내용 3', now(), now());
insert into post (user_id, title, content, created_datetime, modified_datetime) values (1, '테스트 게시글 제목 4', '테스트 게시글 내용 4', now(), now());
insert into post (user_id, title, content, created_datetime, modified_datetime) values (2, '테스트 게시글 제목 5', '테스트 게시글 내용 5', now(), now());

-- comment on post1
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (1, 1, '게시글 1 댓글 1', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (1, 2, '게시글 1 댓글 2', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (1, 3, '게시글 1 댓글 3', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (1, 1, '게시글 1 댓글 4', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (1, 2, '게시글 1 댓글 5', now(), now());

-- comment on post2
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (2, 3, '게시글 2 댓글 1', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (2, 1, '게시글 2 댓글 2', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (2, 2, '게시글 2 댓글 3', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (2, 3, '게시글 2 댓글 4', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (2, 1, '게시글 2 댓글 5', now(), now());

-- comment on post3
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (3, 2, '게시글 3 댓글 1', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (3, 3, '게시글 3 댓글 2', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (3, 1, '게시글 3 댓글 3', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (3, 2, '게시글 3 댓글 4', now(), now());
insert into comment (post_id, user_id, content, created_datetime, modified_datetime) values (3, 3, '게시글 3 댓글 5', now(), now());

-- subcomment

-- depth 2
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 6, 1, '게시글 2 댓글 1 대댓글 1', now(), now());
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 6, 2, '게시글 2 댓글 1 대댓글 2', now(), now());

insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 7, 1, '게시글 2 댓글 2 대댓글 1', now(), now());
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 7, 2, '게시글 2 댓글 2 대댓글 2', now(), now());

-- depth 3
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 16, 1, '게시글 2 댓글 1 대댓글 1 대댓글 1', now(), now());
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 16, 1, '게시글 2 댓글 1 대댓글 1 대댓글 2', now(), now());

insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 19, 1, '게시글 2 댓글 2 대댓글 2 대댓글 1', now(), now());
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 19, 1, '게시글 2 댓글 2 대댓글 2 대댓글 2', now(), now());

-- depth 4
insert into comment (post_id, parent_id, user_id, content, created_datetime, modified_datetime) values (2, 23, 1, '게시글 2 댓글 2 대댓글 2 대댓글 2 대댓글 1', now(), now());
