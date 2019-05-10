create sequence hibernate_sequence start with 1 increment by 1
create table Job (id bigint not null, name varchar(200), description varchar(10000), publication_date date, primary key (id))
