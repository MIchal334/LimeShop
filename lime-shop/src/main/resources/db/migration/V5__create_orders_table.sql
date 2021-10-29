CREATE TABLE if not exists orders(

    id BIGINT auto_increment primary key ,
    producent_id BIGINT,
    client_id BIGINT,
    lime_id BIGINT,
    amount BIGINT,
    date_of_receipt datetime


)
