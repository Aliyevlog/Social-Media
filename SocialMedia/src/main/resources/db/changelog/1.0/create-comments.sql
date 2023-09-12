create table comments
(
    id      serial primary key,
    text    varchar(100) not null,
    user_id int,
    post_id int,
    constraint fk_comments_users
        foreign key (user_id) references users (id),
    constraint fk_comments_posts
        foreign key (post_id) references posts (id)
)