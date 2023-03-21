drop table if exists FILM cascade;
drop table if exists GENRE cascade;
drop table if exists MPA cascade;
drop table if exists LIKES cascade;
drop table if exists USERS cascade;
drop table if exists FILM_GENRE cascade;
drop table if exists GENRE_FILM cascade;
drop table if exists FRIENDSHIP cascade;
drop table if exists RATING_MPA cascade;

create table IF NOT EXISTS GENRE
(
    genreID   INTEGER not null,
    NAME CHARACTER VARYING(250),
    constraint GENRE_PK
        primary key (genreID)
);

create table IF NOT EXISTS Rating_MPA
(
    Rating_ID   BIGINT                 not null,
    NAME CHARACTER VARYING(250) not null,
    constraint MPA_PK
        primary key (Rating_ID)
);

create table IF NOT EXISTS FILM
(
    Film_ID           BIGINT                 auto_increment,
    NAME         CHARACTER VARYING(100) not null,
    DESCRIPTION  CHARACTER VARYING(200),
    RELEASE_DATE DATE,
    DURATION     INTEGER,
    Rating_MPA         BIGINT,
    RATE         INTEGER,
    LIKES_AMOUNT INTEGER,
    constraint FILM_PK
        primary key (Film_ID),
    constraint MPA_FK
        foreign key (Rating_MPA) references Rating_MPA
            on update set null on delete set null
);


CREATE TABLE PUBLIC.GENRE_FILM (
	FILM_ID BIGINT NOT NULL,
	GENRE_ID BIGINT NOT NULL,
	CONSTRAINT GENRE_FILM_PK PRIMARY KEY (FILM_ID,GENRE_ID),
	CONSTRAINT GENRE_FILM_FK FOREIGN KEY (GENRE_ID) REFERENCES PUBLIC.GENRE(GENREID),
	CONSTRAINT GENRE_FILM_FK_1 FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILM(FILM_ID)
);

create table IF NOT EXISTS USERS
(
    User_ID       BIGINT                 auto_increment,
    EMAIL    CHARACTER VARYING(250) not null,
    LOGIN    CHARACTER VARYING(250) not null,
    NAME     CHARACTER VARYING(250),
    BIRTHDAY DATE,
    constraint USER_PK
        primary key (User_ID)
);

create table IF NOT EXISTS FRIENDSHIP
(
    Friend_ID BIGINT not null,
    User_ID BIGINT not null,
    constraint FRIENDSHIP_PK
        primary key (Friend_ID, User_ID),
    constraint FRIENDSHIP_USER1_FK
        foreign key (Friend_ID) references USERS
            on update cascade on delete cascade,
    constraint FRIENDSHIP_USER2_FK
        foreign key (User_ID) references USERS
            on update cascade on delete cascade
);

create table IF NOT EXISTS LIKES
(
    FILM_ID BIGINT not null,
    USER_ID BIGINT not null,
    constraint LIKES_PK
        primary key (FILM_ID, USER_ID),
    constraint LIKES_FILM_FK
        foreign key (FILM_ID) references FILM
            on update cascade on delete cascade,
    constraint LIKES_USER_FK
        foreign key (USER_ID) references USERS
            on update cascade on delete cascade
);
