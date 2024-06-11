alter table cr_eval_index_ref add column `node_id` VARCHAR(64)  COMMENT '告警事件节点id';
alter table cr_eval_index_ref add column `even_name` VARCHAR(64)  COMMENT '告警事件事件名称';
