CREATE TABLE if not exists order_status(
    id smallint,
    status varchar(15)
);


INSERT INTO order_status (id,status) values (1 , 'WAITING');
INSERT INTO order_status (id,status) values (2 , 'ACCEPTED');
INSERT INTO order_status (id,status) values (3 , 'DONE');
INSERT INTO order_status (id,status) values (4 , 'CANCELED');
