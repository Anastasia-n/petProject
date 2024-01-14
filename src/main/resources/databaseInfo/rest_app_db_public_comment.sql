create table comment
(
    comment_id bigint default nextval('comment_id_seq'::regclass) not null
        constraint comment_id
            primary key,
    date       timestamp                                          not null,
    text       varchar                                            not null,
    user_idfk  bigint                                             not null
        constraint user_idfk
            references users,
    news_idfk  bigint                                             not null
        constraint news_idfk
            references news
            on delete cascade
);

alter table comment
    owner to postgres;

INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (1, '2023-10-25 14:05:09.800000', 'Good news!', 3, 1);
INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (2, '2023-10-17 14:26:58.123000', 'Wow', 3, 2);
INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (3, '2023-11-27 19:54:22.650000', 'First', 3, 3);
INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (4, '2023-10-04 09:06:42.100000', 'Dogs are cool', 3, 4);
INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (5, '2023-10-17 20:36:11.250000', 'Sad', 3, 5);
INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (6, '2023-12-18 22:01:56.270000', 'I do not always bark at night. But when i do, it is for no reason.', 3, 6);
INSERT INTO public.comment (comment_id, date, text, user_idfk, news_idfk) VALUES (7, '2024-01-03 22:42:53.360729', 'туц', null, 1);
