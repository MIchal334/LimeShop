
ALTER TABLE orders add foreign key (producent_id) references users(id);
ALTER TABLE orders add foreign key (client_id) references users(id);

