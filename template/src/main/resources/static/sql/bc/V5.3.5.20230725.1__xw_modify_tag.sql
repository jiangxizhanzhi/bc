-- 用户行为模拟 - 行为标签修改
delete from `cr_constants_config` where type=41 and code=1;
delete from `cr_constants_config` where type=41 and code=2;

update `cr_constants_config` set `cn_name`='OA办公' where type=41 and code=4;
update `cr_constants_config` set `cn_name`='服务监控' where type=41 and code=9;
update `cr_constants_config` set `cn_name`='文件管理' where type=41 and code=17;
update `cr_constants_config` set `cn_name`='数据库访问' where type=41 and code=12;