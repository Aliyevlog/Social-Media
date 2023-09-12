create table users (
    id serial primary key ,
    name varchar(100) not null ,
    surname varchar(100) not null ,
    age int,
    gender varchar(100),
    address varchar(200),
    phone varchar(200),
    email varchar(255) not null ,
    password varchar(255) not null ,
    created_at date,
    role varchar(50),
    picture_id int,
    constraint fk_users_files
                   foreign key (picture_id) references files (id)
)