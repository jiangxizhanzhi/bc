ALTER TABLE `bs_user_model`
    ADD COLUMN `user_num` int NOT NULL DEFAULT 1 COMMENT '用户人数';
ALTER TABLE `bs_user_instance`
    ADD COLUMN `user_num` int NOT NULL DEFAULT 1 COMMENT '用户人数';
