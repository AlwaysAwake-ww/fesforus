
drop table member;

CREATE TABLE MEMBER (
	member_index	number(10)		primary key,
	member_id	varchar2(200)		NOT NULL,
	member_pw	varchar2(200)		NOT NULL,
	member_birth	date            NOT NULL,
	member_addr	varchar2(500)		NOT NULL,
	member_email	varchar2(200)		NOT NULL,
	member_nickname	varchar2(50)        NOT NULL,
	member_state	number(10)		NOT NULL,
	member_category VARCHAR2(100)
);

drop table POSTS;
CREATE TABLE POSTS (
	post_num	number(10)		primary key,
	admin_index	number(1)		NOT NULL,
	content_text	varchar2(4000)		NULL,
	review_scoreAvg	number(1)		NULL,
	content_views	number(10)		NULL,
	board_addr	varchar2(2000)		NULL,
	board_loc_addr	number(10)		NULL,
	content_image	varchar2(2000)		NULL,
	progress_state	number(1)		NULL,
	festival_title	varchar2(1000)		NULL,
	festival_upload_date	DATE		NULL,
	festival_category VARCHAR2(100),
    CONSTRAINT admin_index FOREIGN KEY(admin_index) REFERENCES admin(admin_index)

);
drop table Favorite;
CREATE TABLE Favorite (
	member_index	number(10)		NOT NULL,
	post_num	number(10)		NOT NULL,
    CONSTRAINT member_favorite_index FOREIGN KEY(member_index) REFERENCES member(member_index) on delete cascade,
    CONSTRAINT post_favorite_index FOREIGN KEY(post_num) REFERENCES POSTS(post_num) on delete cascade
);


ALTER TABLE Favorite ADD CONSTRAINT PK_FAVORITE PRIMARY KEY (
	member_index,
	post_num
);


drop table FESTIVAL_REVIEW;
CREATE TABLE FESTIVAL_REVIEW (
    review_index number(10) primary key,
	post_num	number(10)		NOT NULL,
	member_index VARCHAR2(30) NOT NULL,
	review_text	varchar2(1000)   NOT	NULL,
	review_score	number(1)		NOT NULL,
    CONSTRAINT post_num FOREIGN KEY(post_num) REFERENCES POSTS(post_num) on delete cascade

);

drop table NOTICE;
CREATE TABLE NOTICE (
	post_num	number(10)		primary key,
	admin_index	number(1)		NOT NULL,
	content_title	varchar2(200)		NOT NULL,
	content_text	varchar2(4000)		NOT NULL,
    CONSTRAINT admin_notice_index FOREIGN KEY(admin_index) REFERENCES admin(admin_index)
);

drop table MESSAGE;
CREATE TABLE MESSAGE (
    message_index number(38)		primary key,
    member_index	number(10)		NOT NULL,
	chatroom_id	number(10)		NOT NULL,
    message	varchar2(50)		NULL,
	message_created_date	date		NULL
);

drop table CHAT_ROOM;
CREATE TABLE CHAT_ROOM (
	chatroom_id	number(10)		primary key,
	post_num	number(10)		NOT NULL
);

drop table ADMIN;
CREATE TABLE ADMIN (
	admin_index	number(10)		primary key,
	admin_id	varchar2(100)		NOT NULL,
	admin_pw	varchar2(100)		NOT NULL
);

DROP TABLE CATEGORY;
CREATE TABLE CATEGORY(
    member_index Number(10) not null,
    category_class VARCHAR2(100) not null,
    CONSTRAINT category_PK PRIMARY KEY(member_index, category_class),
    CONSTRAINT member_category_index FOREIGN KEY(member_index) REFERENCES member(member_index) on delete cascade
);