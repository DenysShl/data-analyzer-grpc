create table data_sensor
(
    id               bigserial primary key,
    sensor_id        bigint       not null,
    timestamp        timestamp    not null,
    measurement      float        not null,
    type             varchar(255) not null
);