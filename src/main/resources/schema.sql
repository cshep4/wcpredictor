create table User
(
   id integer not null,
   firstname varchar(50) not null,
   surname varchar(50) not null,
   email varchar(255) not null,
   password varchar(255) not null,
   primary key(id)
);

create table Token
(
  token varchar(255) not null,
  PRIMARY KEY(token)
)