insert into BOOKS (`NAME`) values ('Java Persistence with Hibernate');
insert into BOOKS (`NAME`) values ('Spring in Action');
insert into BOOKS (`NAME`) values ('Head First. Программирование для Android');

insert into AUTHORS (`NAME`) values ('Christian Bauer');
insert into AUTHORS (`NAME`) values ('Gavin King');
insert into AUTHORS (`NAME`) values ('Gary Gregory');
insert into AUTHORS (`NAME`) values ('Craig Walls');
insert into AUTHORS (`NAME`) values ('Дэвид Гриффитс');
insert into AUTHORS (`NAME`) values ('Дон Гриффитс');

insert into GENRES(`NAME`) values ('Java development');
insert into GENRES (`NAME`) values ('Spring development');
insert into GENRES (`NAME`) values ('Mobile development');

insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (1, 1);
insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (1, 2);
insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (1, 3);
insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (2, 4);
insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (3, 5);
insert into BOOKS_AUTHORS (BOOKID, AUTHORID) values (3, 6);

insert into BOOKS_GENRES (BOOKID, GENREID) values (1, 1);
insert into BOOKS_GENRES (BOOKID, GENREID) values (2, 2);
insert into BOOKS_GENRES (BOOKID, GENREID) values (3, 1);
insert into BOOKS_GENRES (BOOKID, GENREID) values (3, 3);