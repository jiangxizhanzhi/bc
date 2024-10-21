ALTER TABLE `oj_course_lesson`
    ADD COLUMN `job` tinyint NOT NULL DEFAULT 1 ,
    ADD COLUMN `handicap` tinyint NOT NULL DEFAULT 1;
