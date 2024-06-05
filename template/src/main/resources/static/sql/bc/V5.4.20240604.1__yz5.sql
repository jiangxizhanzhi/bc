alter table cr_eval_index_ref add column `event_type` int(11) NULL DEFAULT NULL COMMENT '告警事件评分设置类型';
alter table cr_eval_index_ref add column `event_attr` int(11) NULL DEFAULT NULL COMMENT '告警事件评分设置属性';
alter table cr_eval_index_ref add column `event_value` int(11) NULL DEFAULT NULL COMMENT '告警事件评分设置值';
alter table cr_eval_index_ref add column `event_attr_value` int(11) NULL DEFAULT NULL COMMENT '告警事件评分设置中心值';