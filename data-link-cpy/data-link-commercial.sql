use data_link;

create table dl_user(
id_user int primary key auto_increment,
first_name varchar(30),
last_name varchar(30),
email varchar(50) unique,
user_pass varchar(255),
company varchar(50),
phone_number varchar(20),
active int,
reg_date timestamp,
last_updated timestamp
);

create table dl_role(
id_role int primary key auto_increment,
rol_name varchar(30),
active int,
reg_date timestamp,
last_updated timestamp
);

create table dl_module(
id_module int primary key auto_increment,
module_name varchar(30),
url varchar(50),
active int,
reg_date timestamp,
last_updated timestamp
);

create table dl_user_x_role(
id_user_x_role int primary key auto_increment,
id_user int,
id_role int,
active int,
reg_date timestamp,
last_updated timestamp
);

create table dl_role_x_module(
id_role_x_module int primary key auto_increment,
id_role int,
id_module int,
active int,
reg_date timestamp,
last_updated timestamp
);


create table dl_infusionsoft_tokens(
id_token int primary key auto_increment,
id_user int,
actual_token longtext,
refresh_token longtext,
reg_date timestamp,
last_updated timestamp,
active int
);

create table dl_connectwise_credentials(
id_credential int primary key auto_increment,
id_user int,
url longtext,
company longtext,
public_key longtext,
secret_key longtext,
reg_date timestamp,
active int
);

create table dl_connector(
id_connector int primary key auto_increment,
id_user int,
img varchar(255),
platform varchar(100),
type varchar(50),
descrip longtext,
id_cred int,
reg_date timestamp,
last_updated timestamp,
active int
);

create table dl_errors(
id_error int primary key auto_increment,
id_user int,
body longtext,
class varchar(50),
descrip longtext,
reg_date timestamp,
line varchar(100)
);

create table dl_mapping(
id_mapping int primary key auto_increment,
id_conn_fst int,
id_conn_snd int,
img varchar(200),
type varchar(100),
reg_date timestamp,
last_updated timestamp,
active int
);

create table dl_mapped_fields(
id_field int primary key auto_increment,
id_mapping int,
fst_field_name varchar(255),
fst_value_type varchar(100),
fst_platform varchar(100),
snd_field_name varchar(255),
snd_value_type varchar(100),
snd_platform varchar(100),
reg_date timestamp,
last_updated timestamp,
active int
);

create table dl_callback_cw(
id_callback int primary key auto_increment,
id_mapping int,
id_conn int,
id_cw int,
url varchar(250),
object_id int,
type varchar(250),
level varchar(250),
reg_date timestamp,
last_updated timestamp,
active int,
member_id int,
info longtext
);



create table dl_webhooks_is(
id_webhook int primary key auto_increment,
id_mapping int,
id_conn int,
key_webhook varchar(255),
event_key varchar(255),
hook_url varchar(255),
status varchar(255),
reg_date timestamp,
last_updated timestamp,
active int
);

alter table dl_webhooks_is add constraint dl_webhook_to_mapping foreign key(id_mapping) references dl_mapping(id_mapping);
alter table dl_webhooks_is add constraint dl_webhook_to_connector foreign key(id_conn) references dl_connector(id_connector);



create table dl_syncs(
id_sync int primary key auto_increment,
id_user int,
body longtext,
operation varchar(100),
platform varchar(100),
entity varchar(100),
url varchar(255),
reg_date timestamp,
last_updated timestamp,
active int
);

alter table dl_syncs add constraint dl_syncs_to_user foreign key(id_user) references dl_user(id_user);





alter table dl_user_x_role add constraint dl_role_to_user foreign key (id_user) references dl_user (id_user);
alter table dl_user_x_role add constraint dl_user_to_role foreign key (id_role) references dl_role (id_role);
alter table dl_role_x_module add constraint dl_module_to_role foreign key (id_role) references dl_role (id_role);
alter table dl_role_x_module add constraint dl_role_to_module foreign key (id_module) references dl_module (id_module);

alter table dl_connector add constraint dl_conn_to_user foreign key (id_user) references dl_user (id_user);
alter table dl_connectwise_credentials add constraint dl_cw_to_user foreign key (id_user) references dl_user (id_user);
alter table dl_infusionsoft_tokens add constraint dl_is_to_user foreign key (id_user) references dl_user (id_user);

alter table dl_mapping add constraint dl_map_to_connfst foreign key (id_conn_fst) references dl_connector (id_connector);
alter table dl_mapping add constraint dl_map_to_connsnd foreign key (id_conn_snd) references dl_connector (id_connector);
alter table dl_mapped_fields add constraint dl_field_to_map foreign key (id_mapping) references dl_mapping (id_mapping);
alter table dl_callback_cw add constraint dl_callback_to_mapping foreign key(id_mapping) references dl_mapping(id_mapping);
alter table dl_callback_cw add constraint dl_callback_to_conn foreign key(id_conn) references dl_connector(id_connector);

insert into dl_user (first_name,last_name,email,user_pass,company,phone_number,active,reg_date,last_updated)
values ('Eduardo','Valdez','eduardoc1112@gmail.com','12345','Orasoft','(503)7108-3413',1,now(),now());

insert into dl_role(rol_name,active,reg_date,last_updated)
values ('admin',1,now(),now());
insert into dl_role(rol_name,active,reg_date,last_updated)
values ('user',1,now(),now());


insert into dl_user_x_role(id_user,id_role,active,reg_date,last_updated)
values (1,1,1,now(),now());
insert into dl_user_x_role(id_user,id_role,active,reg_date,last_updated)
values (1,2,1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Users','/users',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Add user','/user',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Edit user','/user/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Delete user','/delete/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Disable user','/disable/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Enable user','/enable/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Roles','/roles',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Add role','/role',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Edit role','/role/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Delete role','/role/delete/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Enable role','/role/enable/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Disable role','/role/disable/**',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Assign role','/assignrole',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Assign role to user','/assignroleuser',1,now(),now());


INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('base','/',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Dashboard','/dashboard',1,now(),now());

INSERT INTO dl_module(module_name,url,active,reg_date,last_updated)
VALUES('Connectors','/connectors',1,now(),now());


INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,1,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,2,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,3,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,4,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,5,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,6,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,7,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,8,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,9,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,10,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,11,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,12,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,13,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(1,14,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(2,15,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(2,16,1,now(),now());

INSERT INTO dl_role_x_module
(id_role,id_module,active,reg_date,last_updated)
VALUES(2,17,1,now(),now());






