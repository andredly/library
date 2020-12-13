create table users
(
    id    bigserial    not null
        constraint users_pkey
            primary key,
    name  varchar(100) not null,
    email varchar(200) not null
);

alter table users
    owner to postgres;

create table roles
(
    id        bigserial    not null
        constraint roles_pkey
            primary key,
    role_type varchar(100) not null
);

alter table roles
    owner to postgres;

create unique index roles_role_type_uindex
    on roles (role_type);

create table user_role
(
    id      bigserial not null
        constraint user_role_pkey
            primary key,
    user_id bigint    not null
        constraint "FK_user_id"
            references users,
    role_id bigint    not null
        constraint "FK_role_id"
            references roles
);

alter table user_role
    owner to postgres;

create unique index user_role_user_id_role_id_uindex
    on user_role (user_id, role_id);