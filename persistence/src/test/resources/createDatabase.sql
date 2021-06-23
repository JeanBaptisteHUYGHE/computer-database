drop table if exists computer;
drop table if exists company;
drop table if exists user;

create table company (
	id bigint not null auto_increment,
	name varchar(255),
    constraint pk_company primary key (id)
    );

  create table computer (
    id bigint not null auto_increment,
    name varchar(255),
    introduced timestamp NULL,
    discontinued timestamp NULL,
    company_id bigint default NULL,
    constraint pk_computer primary key (id))
  ;

	create table user (
		id                        bigint not null auto_increment,
		login                     varchar(255),
		password                  varchar(255),
		name                      varchar(255),
		role                      enum('admin', 'user') default 'user',
		active                    boolean default 1
	);

  alter table computer add constraint fk_computer_company_1 foreign key (company_id) references company (id) on delete restrict on update restrict;
  create index ix_computer_company_1 on computer (company_id);
