CREATE TABLE if not exists lime(

    id BIGINT auto_increment primary key ,
    type varchar(100),
    amount int,
    owner_id BIGINT
)
