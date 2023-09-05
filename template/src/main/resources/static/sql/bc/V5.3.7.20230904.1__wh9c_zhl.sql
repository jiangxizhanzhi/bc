UPDATE cr_eval_record SET type=1 WHERE type=0;
ALTER TABLE cr_role_asset_node_copy add column `eval_template_id` varchar(32) DEFAULT NULL COMMENT '评测模版id';


