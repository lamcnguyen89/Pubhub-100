drop table if exists BOOKS;
drop table if exists BOOK_TAGS;

CREATE TABLE BOOKS
(
    isbn_13 varchar (13) primary key,
    title varchar (100),
    author varchar (80),
    publish_date date,
    price decimal (6,2),
    content bytea
);

CREATE TABLE BOOK_TAGS
(
    isbn_13 varchar(13) references BOOKS(isbn_13),
    tags varchar(100),
    primary key (isbn_13, tags)
);

insert into BOOKS
values
    (
        '1111111111111', -- id
        'The Adventures of Steve', -- title
        'Russell Barron', -- author
        current_date, -- publishDate
        123.50, -- price
        null -- blob
);

insert into BOOK_TAGS
values
    (
		'1111111111111',
        'An action adventure about the most generic guy you would ever meet.' --tag name
    );