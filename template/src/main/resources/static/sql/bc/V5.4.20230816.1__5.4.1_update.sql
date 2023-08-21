ALTER TABLE cr_rb_time_management ADD COLUMN length int(11) NOT NULL COMMENT '已记录训练时长';
ALTER TABLE cr_rb_time_management ADD COLUMN recover_time datetime(6) not null comment '恢复时间';
ALTER TABLE cr_rb_time_management ADD COLUMN end_time datetime(6) null comment '结束时间';
