alter table init_rtks_role add column avatar varchar(2048) not null comment '头像';

update init_rtks_role set avatar = '/cpms/rtks_role/Default.png' where 1 = 1;
update init_rtks_role set avatar = '/cpms/rtks_role/PenetrationTestingEngineer.png' where resource_id = 'c3fe96a8de569cdb92e273c8809a73b7';
update init_rtks_role set avatar = '/cpms/rtks_role/SecurityDevelopmentEngineer.png' where resource_id = 'e25dc8c9d15ddd621f16616083d68726';
update init_rtks_role set avatar = '/cpms/rtks_role/SecurityForensicsEngineer.png' where resource_id = 'aeec2fc773691f459105b0323c3e1711';
update init_rtks_role set avatar = '/cpms/rtks_role/ReverseAnalysisEngineer.png' where resource_id = 'f7753c4236c6b6981a577118a686aeb3';
update init_rtks_role set avatar = '/cpms/rtks_role/SecurityOperationMaintenanceEngineer.png' where resource_id = '542fc3e2caf0fa9b4935432ffe4daafa';