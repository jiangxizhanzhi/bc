alter table cr_eval_variable_ref
    add column event_fifth_index_context text null comment '五级指标告警设置';
alter table cr_eval_index_ref
    drop column event_fifth_index_context;

alter table cr_eval_index_ref
    drop column load_type;

alter table cr_eval_index_ref
    drop column judgment_status;

alter table cr_eval_index_ref
    drop column load_value;

alter table cr_eval_index_ref
    drop column load_attr_value;

alter table cr_eval_index_ref
    drop column load_execution_id;

alter table cr_eval_index_ref
    drop column load_execution_name;

