ALTER
DATABASE ojj character set utf8 collate utf8_general_ci;

INSERT INTO `ojj`.`test_migrate` ( `resource_id`, `insert_key`, `content`, `create_time`, `update_time`) VALUES ( '432423', '432423', '4324', '2022-01-10 11:51:27', '2022-01-20 11:51:30');
 
commit;