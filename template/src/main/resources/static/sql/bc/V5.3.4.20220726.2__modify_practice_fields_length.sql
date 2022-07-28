-- 禅道7140
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `answer` varchar(4096) NOT NULL;
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `blank` varchar(4096) DEFAULT NULL;
ALTER TABLE `oj_practice_theory` MODIFY COLUMN `title` varchar(2048) NOT NULL;
