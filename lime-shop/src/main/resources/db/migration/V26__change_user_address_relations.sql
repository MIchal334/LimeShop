ALTER TABLE users drop constraint fk_address_user;


ALTER TABLE addresses add constraint  fk_address_user foreign key (id) references users(address);