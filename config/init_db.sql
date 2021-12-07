create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text
);

create table contact
(
    id          serial,
    resume_uuid char(36) not null
        references resume (uuid)
            on delete cascade,
    type        text     not null,
    value       text     not null
);

create unique index "contact_uuid_ type_index"
    on contact (resume_uuid, type);