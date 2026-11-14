create table cr_business_template (
    id int, resource_id varchar(64) primary key, topo_id varchar(64), name varchar(255),
    description text, business_type int, difficulty int, create_user_id varchar(64), logo text,
    local_scene_id varchar(64), xd_template_type int, xd_template_category int,
    operation_auth_type int, operation_auth_ids text, read_auth_type int, read_auth_ids text,
    create_time timestamp, update_time timestamp
);
create table cr_single_template (
    id int, resource_id varchar(64) primary key, business_template_id varchar(64),
    pre_skills text, train_skills text, sub_type int, duration_time int, missions_scheme text,
    create_time timestamp, update_time timestamp
);
create table cr_mission_template (
    id int, resource_id varchar(64) primary key, title varchar(255), description text, score int,
    skill_points text, answer text, `option` text, `type` int, category_id varchar(64), write_up text,
    create_user_id varchar(64), template_id varchar(64), attachment text,
    business_template_id varchar(64), create_time timestamp, update_time timestamp
);
