ALTER TABLE `cr_mission`
    MODIFY COLUMN `answer` varchar(4096);

ALTER TABLE `cr_mission_template`
    MODIFY COLUMN `answer` varchar(4096);

ALTER TABLE `cr_task`
    MODIFY COLUMN `user_answer` longtext;