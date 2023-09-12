create table files (
    id serial primary key ,
    name varchar not null ,
    type varchar(100),
    size int
)