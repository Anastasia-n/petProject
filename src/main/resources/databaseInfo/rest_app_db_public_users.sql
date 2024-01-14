create table users
(
    user_id  bigint default nextval('users_id_seq'::regclass) not null
        constraint user_id
            primary key,
    login    varchar(45)                                      not null
        constraint login
            unique,
    password varchar(45)                                      not null,
    role     varchar(10),
    email    varchar(45)                                      not null
        constraint email
            unique
);

alter table users
    owner to postgres;

INSERT INTO public.users (user_id, login, password, role, email) VALUES (1, 'admin', '$2a$12$dtrFxi0Jc.RbVA2.XmD7KOKYi2wNaGjTB82O0h8652Io3b9DzvWRO', 'ROLE_ADMIN', 'admin@mail.ru');
INSERT INTO public.users (user_id, login, password, role, email) VALUES (3, 'user', '$2a$12$SW7zj6F3l0MU05hzZMaHCepH69SZJT/2XjNdKVVaq9F6zUQENf.BG', 'ROLE_USER', 'user@mail.ru');
INSERT INTO public.users (user_id, login, password, role, email) VALUES (2, 'moderator', '$2a$12$X9MXV5K3Cyeu5BwfptavJeUjEOiMw9dhwuqtgd/u6hCkn7RUa8dKC', 'ROLE_MODERATOR', 'moderator@mail.ru');
