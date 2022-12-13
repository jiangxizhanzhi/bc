ALTER TABLE `assess_file_data`
    MODIFY COLUMN `content` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件地址';

delete from `cr_constants_config` where type = 12 and code = 2;