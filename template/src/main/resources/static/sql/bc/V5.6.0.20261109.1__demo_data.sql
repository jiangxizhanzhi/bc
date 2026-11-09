CREATE TABLE cr_demo_data_run (
    business_date date NOT NULL,
    generated_count int NOT NULL,
    create_time datetime NOT NULL,
    PRIMARY KEY (business_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cr_demo_data_room (
    room_id varchar(64) NOT NULL,
    source_room_id varchar(64) NOT NULL,
    business_date date NOT NULL,
    topo_digest text NOT NULL,
    PRIMARY KEY (room_id),
    INDEX business_date (business_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
