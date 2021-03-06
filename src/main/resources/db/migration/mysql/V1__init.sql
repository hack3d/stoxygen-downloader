create table bond (
    bonds_id integer not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    crypto_base varchar(255) not null,
    crypto_pair varchar(255) not null,
    crypto_quote varchar(255) not null,
    isin varchar(255) not null,
    name varchar(255) not null,
    state integer not null,
    primary key (bonds_id)
) ENGINE=InnoDB;

create table exchange (
    exchanges_id integer not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    country_code varchar(4) not null,
    interval_delay int(2) default 0,
    name varchar(255) not null,
    symbol varchar(255) not null,
    primary key (exchanges_id)
) ENGINE=InnoDB;

create table exchange_bonds (
    exchanges_exchanges_id integer not null,
    bonds_bonds_id integer not null,
    primary key (exchanges_exchanges_id, bonds_bonds_id)
) ENGINE=InnoDB;

create table tickdata1minute (
    tickdata1minute_id bigint not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    aggregated bit not null,
    ask float not null,
    bid float not null,
    close float not null,
    high float not null,
    low float not null,
    open float not null,
    volume float not null,
    bonds_bonds_id integer,
    exchanges_exchanges_id integer,
    primary key (tickdata1minute_id)
) ENGINE=InnoDB;

create table tickdata_current (
    tickdata_currents_id bigint not null auto_increment,
    insert_timestamp datetime,
    insert_user varchar(255),
    modify_timestamp datetime,
    modify_user varchar(255),
    aggregated bit not null,
    ask float not null,
    bid float not null,
    high float not null,
    last float not null,
    low float not null,
    volume float not null,
    bond_bonds_id integer,
    exchange_exchanges_id integer,
    primary key (tickdata_currents_id)
) ENGINE=InnoDB;

alter table exchange_bonds add constraint FKm5a9ds39gckypvgescolen53h foreign key (bonds_bonds_id) references bond (bonds_id);
alter table exchange_bonds add constraint FK6yy0ft6s7anj8loeb0g0310p9 foreign key (exchanges_exchanges_id) references exchange (exchanges_id);
alter table tickdata1minute add constraint FKc8g31cwk9i5a8yfaoywup9ygq foreign key (bonds_bonds_id) references bond (bonds_id);
alter table tickdata1minute add constraint FKswwih90qc6es3um1ixnxbdgjn foreign key (exchanges_exchanges_id) references exchange (exchanges_id);
alter table tickdata_current add constraint FKhqv8upysfebhv5fbdasb6bq50 foreign key (bond_bonds_id) references bond (bonds_id);
alter table tickdata_current add constraint FK7fq1966nq98ws67cjxvcg9g8e foreign key (exchange_exchanges_id) references exchange (exchanges_id);
