alter table cr_eval_record add column `eval_end_time` datetime NULL DEFAULT NULL;
alter table cr_eval_index_ref add column `progress` int(11) NULL DEFAULT NULL;