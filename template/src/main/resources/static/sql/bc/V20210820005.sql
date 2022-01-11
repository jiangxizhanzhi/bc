ALTER
DATABASE ojj character set utf8 collate utf8_general_ci;

ALTER TABLE `ojj`.`test_migrate` 
ADD COLUMN `insert_key` varchar(255) NULL AFTER `resource_id`;

commit;