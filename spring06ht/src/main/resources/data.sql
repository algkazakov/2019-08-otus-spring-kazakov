insert into BOOKS (ID, `NAME`) values (1, 'Java Persistence with Hibernate');
insert into BOOKS (ID, `NAME`) values (2, 'Spring in Action');
insert into BOOKS (ID, `NAME`) values (3, 'Head First. Программирование для Android');

insert into AUTHORS (ID, `NAME`) values (1, 'Christian Bauer');
insert into AUTHORS (ID, `NAME`) values (2, 'Gavin King');
insert into AUTHORS (ID, `NAME`) values (3, 'Gary Gregory');
insert into AUTHORS (ID, `NAME`) values (4, 'Craig Walls');
insert into AUTHORS (ID, `NAME`) values (5, 'Дэвид Гриффитс');
insert into AUTHORS (ID, `NAME`) values (6, 'Дон Гриффитс');

insert into GENRES(ID, `NAME`) values (1, 'Java development');
insert into GENRES (ID, `NAME`) values (2, 'Spring development');
insert into GENRES (ID, `NAME`) values (3, 'Mobile development');

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