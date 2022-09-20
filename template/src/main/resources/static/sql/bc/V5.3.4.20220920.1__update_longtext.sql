
alter table oj_paper modify read_auth_ids longtext null comment '授权访问组织id';

alter table oj_classroom modify organization_ids longtext not null comment '关联组织下所有组织id';