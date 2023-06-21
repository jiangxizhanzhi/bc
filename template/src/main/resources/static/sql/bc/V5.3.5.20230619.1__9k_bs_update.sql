-- 行为仪表新增运维靶标id
ALTER TABLE bs_user_simulator
    ADD COLUMN yw_id VARCHAR(255) NULL COMMENT '运维靶标id' AFTER device_id;