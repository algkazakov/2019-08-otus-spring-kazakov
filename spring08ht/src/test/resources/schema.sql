DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT auto_increment PRIMARY KEY, NAME VARCHAR(255));
DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID BIGINT auto_increment PRIMARY KEY, NAME VARCHAR(255));
DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID BIGINT auto_increment PRIMARY KEY, NAME VARCHAR(255));
DROP TABLE IF EXISTS BOOKS_AUTHORS;
CREATE TABLE BOOKS_AUTHORS(BOOKID BIGINT, AUTHORID BIGINT,);
DROP TABLE IF EXISTS BOOKS_GENRES;
CREATE TABLE BOOKS_GENRES(BOOKID BIGINT, GENREID BIGINT);
DROP TABLE IF EXISTS BOOKS_COMMENTS;
CREATE TABLE BOOKS_COMMENTS(ID BIGINT auto_increment PRIMARY KEY, BOOKID BIGINT, COMMENT_TEXT VARCHAR(1024));