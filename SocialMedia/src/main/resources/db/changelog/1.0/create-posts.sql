create table posts (
    id serial primary key ,
    text varchar not null ,
    created_at date,
    user_id int,
    constraint fk_posts_users
                   foreign key (user_id) references users (id)
)