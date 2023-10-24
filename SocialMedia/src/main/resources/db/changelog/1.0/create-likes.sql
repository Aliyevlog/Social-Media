create table likes (
    id serial primary key ,
    user_id int,
    post_id int,
    reaction boolean,
    constraint fk_likes_users
                   foreign key (user_id) references users(id),
    constraint fk_likes_posts
                   foreign key (post_id) references posts(id)
)