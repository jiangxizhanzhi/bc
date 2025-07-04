alter table cr_business_template
    modify unite_task_id varchar(64) null comment '联合任务id';
alter table cr_rb_room
    add column train_target text null comment '训练目标';
alter table cr_rb_room
    modify business_type int null comment '所属训练类型';
alter table cr_rb_room
    add column task_id varchar(64) null comment '训练目标';