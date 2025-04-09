ALTER TABLE `oj_course`
    ADD COLUMN `attack` varchar(2048) NULL,
    ADD COLUMN `defense` varchar(2048) NULL;

ALTER TABLE `oj_practice_operation`
    ADD COLUMN `attack` varchar(2048) NULL,
    ADD COLUMN `defense` varchar(2048) NULL;

ALTER TABLE `oj_practice_theory`
    ADD COLUMN `attack` varchar(2048) NULL,
    ADD COLUMN `defense` varchar(2048) NULL;

ALTER TABLE `cr_eval_algorithm`
    ADD COLUMN `instructions` text NULL,
    ADD COLUMN `advantage` text NULL;