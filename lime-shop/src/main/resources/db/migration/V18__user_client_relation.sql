
ALTER TABLE producnet_client add foreign key (producnet_id) references users(id);
ALTER TABLE producnet_client add foreign key (client_id) references users(id);

