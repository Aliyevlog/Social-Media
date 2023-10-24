create table friends (
    id serial primary key ,
    user1_id int,
    user2_id int,
    constraint fk_friend_requests_users1
                             foreign key (user1_id) references users(id),
    constraint fk_friend_requests_users2
                             foreign key (user2_id) references users(id)
)