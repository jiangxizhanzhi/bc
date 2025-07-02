ALTER TABLE init_rtks_role ADD COLUMN avatar varchar(2048);
COMMENT ON COLUMN init_rtks_role.avatar IS '头像';
UPDATE init_rtks_role SET avatar = '/cpms/rtks_role/Default.png' WHERE 1 = 1;
COMMIT;
ALTER TABLE init_rtks_role ALTER COLUMN avatar SET NOT NULL;

update init_rtks_role set avatar = '/cpms/rtks_role/Default.png' where 1 = 1;
update init_rtks_role set avatar = '/cpms/rtks_role/PenetrationTestingEngineer.png' where resource_id = 'c3fe96a8de569cdb92e273c8809a73b7';
update init_rtks_role set avatar = '/cpms/rtks_role/SecurityDevelopmentEngineer.png' where resource_id = 'e25dc8c9d15ddd621f16616083d68726';
update init_rtks_role set avatar = '/cpms/rtks_role/SecurityForensicsEngineer.png' where resource_id = 'aeec2fc773691f459105b0323c3e1711';
update init_rtks_role set avatar = '/cpms/rtks_role/ReverseAnalysisEngineer.png' where resource_id = 'f7753c4236c6b6981a577118a686aeb3';
update init_rtks_role set avatar = '/cpms/rtks_role/SecurityOperationMaintenanceEngineer.png' where resource_id = '542fc3e2caf0fa9b4935432ffe4daafa';
COMMIT;

ALTER TABLE "cr_train_record" ADD  CONSTRAINT "CRCHECKBTYPE" CHECK("business_type" >= 0) ENABLE ;
ALTER TABLE "cr_rb_time_management" ADD  CONSTRAINT "CRCHECKRATE" CHECK("rate" >= 0) ENABLE ;
ALTER TABLE "cr_rb_time_management" ADD  CONSTRAINT "CRCHECKSTATUS" CHECK("status" >= 0) ENABLE ;
ALTER TABLE "cr_single_template" ADD  CONSTRAINT "CRCHECKDURATION" CHECK("duration_time" >= 0) ENABLE ;
COMMIT;