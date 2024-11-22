ALTER TABLE cr_eval_template_template ADD COLUMN auth_user_ids TEXT null comment '授权用户';
ALTER TABLE cr_eval_template_template ADD COLUMN operation_auth_type int(2) null comment '授权模式, 1-所有, 2-自定义';
ALTER TABLE cr_eval_template_template ADD COLUMN system_id varchar(64) null comment '体系ID';