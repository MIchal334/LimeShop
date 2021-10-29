CREATE TABLE if not exists users(

    id BIGINT auto_increment primary key ,
    username varchar(50),
    name varchar(40),
    last_name varchar(40),
    phone_number varchar(12),
    email varchar(60),
    address int,
    role int,
    isDeleted bool
)
