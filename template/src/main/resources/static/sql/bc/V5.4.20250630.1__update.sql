ALTER TABLE `bc`.`cr_tool_tool`
    MODIFY COLUMN `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL AFTER `resource_id`;