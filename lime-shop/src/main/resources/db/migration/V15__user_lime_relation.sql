
alter table  roles add primary key (id);
ALTER TABLE lime add foreign key (owner_id) references users(id);
ALTER TABLE users add foreign key (role) references roles(id);
