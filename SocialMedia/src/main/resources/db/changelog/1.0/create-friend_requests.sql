create table friend_requests (
    id serial primary key ,
    sender_id int,
    receiver_id int,
    constraint fk_friend_requests_users1
                             foreign key (sender_id) references users(id),
    constraint fk_friend_requests_users2
                             foreign key (receiver_id) references users(id)
)