ALTER TABLE orders DROP COLUMN is_accept;
ALTER TABLE orders DROP COLUMN is_check;
ALTER TABLE orders DROP COLUMN is_deleted;

ALTER TABLE order_status add primary key (id);
ALTER TABLE orders ADD COLUMN status smallint;
ALTER TABLE orders add foreign key (status) references order_status(id);
