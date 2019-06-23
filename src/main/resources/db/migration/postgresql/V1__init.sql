create table bond (
    bonds_id  serial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    crypto_base varchar(255) not null,
    crypto_pair varchar(255) not null,
    crypto_quote varchar(255) not null,
    isin varchar(255) not null,
    name varchar(255) not null,
    state int4 not null,
    primary key (bonds_id)
);

create table exchange(
    exchanges_id  serial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    country_code varchar(4) not null,
    interval_delay integer default 0,
    name varchar(255) not null,
    symbol varchar(255) not null,
    primary key (exchanges_id)
);

create table exchange_bonds (
    exchanges_exchanges_id int4 not null,
    bonds_bonds_id int4 not null,
    primary key (exchanges_exchanges_id, bonds_bonds_id)
);

create table tickdata1minute (
    tickdata1minute_id  bigserial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    aggregated boolean not null,
    ask float4 not null,
    bid float4 not null,
    close float4 not null,
    high float4 not null,
    low float4 not null,
    open float4 not null,
    timestamp timestamp not null,
    volume float4 not null,
    bonds_bonds_id int4,
    exchange_exchanges_id int4,
    primary key (tickdata1minute_id)
);

create table tickdata_current (
    tickdata_currents_id  bigserial not null,
    insert_timestamp timestamp,
    insert_user varchar(255),
    modify_timestamp timestamp,
    modify_user varchar(255),
    aggregated boolean not null,
    ask float4 not null,
    bid float4 not null,
    high float4 not null,
    last float4 not null,
    low float4 not null,
    volume float4 not null,
    bond_bonds_id int4,
    exchange_exchanges_id int4,
    primary key (tickdata_currents_id)
);

alter table exchange_bonds add constraint FKm5a9ds39gckypvgescolen53h foreign key (bonds_bonds_id) references bond;
alter table exchange_bonds add constraint FK6yy0ft6s7anj8loeb0g0310p9 foreign key (exchanges_exchanges_id) references exchange;
alter table tickdata1minute add constraint FKc8g31cwk9i5a8yfaoywup9ygq foreign key (bonds_bonds_id) references bond;
alter table tickdata1minute add constraint FK7c7aqvl9l6tcjn1f5peexthnc foreign key (exchange_exchanges_id) references exchange;
alter table tickdata_current add constraint FKhqv8upysfebhv5fbdasb6bq50 foreign key (bond_bonds_id) references bond;
alter table tickdata_current add constraint FK7fq1966nq98ws67cjxvcg9g8e foreign key (exchange_exchanges_id) references exchange;
