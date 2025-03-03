ALTER TABLE cr_eval_index_ref
    ADD COLUMN load_type INT COMMENT '载荷执行记录评分设置类型（三级指标包含）百分比/周期/整数值',
    ADD COLUMN judgment_status INT COMMENT '载荷执行记录评分设置属性（三级指标包含）效益/成本/固定/偏离',
    ADD COLUMN load_value INT COMMENT '载荷执行记录评分设置值（三级指标包含）类型输入',
    ADD COLUMN load_attr_value INT COMMENT '载荷属性值',
    ADD COLUMN load_execution_id VARCHAR(64) COMMENT '载荷执行记录id',
    ADD COLUMN load_execution_name VARCHAR(64) COMMENT '载荷执行记录名称';