create table news
(
    news_id          bigint default nextval('news_id_seq'::regclass) not null
        primary key,
    title            varchar(255)                                    not null,
    publication_date timestamp                                       not null,
    content          varchar                                         not null,
    user_idfk        bigint                                          not null
        constraint user_idfk
            references users
);

alter table news
    owner to postgres;

INSERT INTO public.news (news_id, title, publication_date, content, user_idfk) VALUES (2, 'Exceptionally rare white alligator born at Florida park', '2023-10-16 15:46:01.325000', 'An incredibly rare form of white alligator has been born at a Florida alligator-themed park, which is now consulting the public for a name.', 1);
INSERT INTO public.news (news_id, title, publication_date, content, user_idfk) VALUES (3, 'Zebra captured after three hours on the run in Seoul', '2023-11-27 10:28:01.425000', 'A young zebra walked, trotted and galloped for hours through the busy streets of Seoul before emergency workers tranquillised the animal and brought it back to a zoo.', 1);
INSERT INTO public.news (news_id, title, publication_date, content, user_idfk) VALUES (4, '‘AI can teach us a lot’: scientists say cats’ expressions richer than imagined and aim to translate them', '2023-10-03 19:53:03.700000', 'Scientists are turning to new technology to unpick the meanings behind the vocal and physical cues of a host of animals. “We could use AI to teach us a lot about what animals are trying to say to us,” said Daniel Mills, a professor of veterinary behavioural medicine at the University of Lincoln.', 2);
INSERT INTO public.news (news_id, title, publication_date, content, user_idfk) VALUES (5, 'New Zealand’s ratio of sheep to humans at lowest point in 170 years', '2023-09-29 21:59:04.785000', 'New Zealand’s ratio of sheep to people has dropped below five to one for the first time since national population records began in the late 1850s.', 2);
INSERT INTO public.news (news_id, title, publication_date, content, user_idfk) VALUES (6, 'Humans may have influenced evolution of dogs’ eye colour, researchers say', '2023-12-08 22:03:06.540000', 'A study by scientists in Japan found that dark eyes are more common in domesticated dogs than their wild relatives, and that humans perceive dogs with dark eyes as being more friendly.', 2);
INSERT INTO public.news (news_id, title, publication_date, content, user_idfk) VALUES (1, 'Lost Michigan toddler found asleep in woods using family dog as furry pillow', '2023-10-25 13:16:01.425000', 'A two-year-old girl who walked away from her home in Michigan’s Upper Peninsula alongside two family dogs was found in the woods hours later sleeping on the smaller dog like a furry pillow, state police said.', 1);
