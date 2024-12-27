create table data_offset
(
    current_offset bigint default 0
);

insert into data_offset (current_offset)
values (0);