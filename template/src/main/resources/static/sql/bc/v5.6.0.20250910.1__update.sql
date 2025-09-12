alter table oj_course add column lessons varchar(4096) not null default '[]' comment '组成课程的实验和科目';
alter table oj_course add column attachment varchar(1024) not null default '' comment '课程的附件';