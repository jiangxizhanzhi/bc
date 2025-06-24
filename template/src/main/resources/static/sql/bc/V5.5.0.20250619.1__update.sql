create table cr_unite_business_task
(
    id               int auto_increment
        primary key,
    resource_id      varchar(64)  not null comment 'uuid',
    task_id          varchar(64)  not null comment '任务id',
    task_name        varchar(255) null comment '任务名称',
    task_description text         null comment '任务描述',
    start_time       datetime     null comment '训练开始时间',
    end_time         datetime     null comment '训练结束时间',
    train_target     text         null comment '训练目标',
    update_time      datetime     null comment '更新时间',
    create_time      datetime     null comment '创建时间',
    constraint resource_id
        unique (resource_id),
    constraint task_id
        unique (task_id)
)
    comment '联合训练任务表' row_format = DYNAMIC;

alter table cr_business_template
    add unite_task_id  varchar(64)  not null comment '联合任务id',
    add start_time   datetime null comment '训练开始时间',
    add end_time     datetime null comment '训练结束时间',
    add train_target text     null comment '训练目标';

create table cr_unite_business_link
(
    id                 int auto_increment
        primary key,
    resource_id        varchar(64) not null comment 'uuid',
    task_id            varchar(64) not null comment '任务id',
    link_id          varchar(64) null comment '链路id',
    source_number_id varchar(64) null comment '源设备id',
    target_number_id        varchar(64) null comment '目的设备名称',
    medium_type         varchar(64)  null comment '光纤/网线',
    band_width          int(10) null comment '带宽',
    business_type          varchar(64) null comment '视频/语音',
    update_time        datetime    not null comment '更新时间',
    create_time        datetime    not null comment '创建时间',
    constraint resource_id
        unique (resource_id)
)
    comment '联合训练链路表' row_format = DYNAMIC;

create table cr_unite_business_node
(
    id                 int auto_increment
        primary key,
    resource_id        varchar(64) not null comment 'uuid',
    task_id            varchar(64) not null comment '任务id',
    number_id          varchar(64) null comment '设备ID',
    ident_relationship varchar(64) null comment '红蓝所属',
    number_name        varchar(64) null comment '设备名称',
    node_type          varchar(64) null comment '交换机/防火墙/终端/路由',
    update_time        datetime    not null comment '更新时间',
    create_time        datetime    not null comment '创建时间',
    constraint resource_id
        unique (resource_id)
)
    comment '联合训练节点表' row_format = DYNAMIC;

alter table cr_rb_room
    add business_type int not null comment '所属训练类型';