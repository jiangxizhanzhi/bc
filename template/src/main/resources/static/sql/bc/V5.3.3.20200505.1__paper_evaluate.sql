/** 课堂练习答题记录表增加等级、准确率、批改人*/
ALTER TABLE `oj_lesson_paper_evaluate`
    ADD COLUMN `level` int(11) DEFAULT NULL,
    ADD COLUMN `accuracy` VARCHAR(64) DEFAULT NULL,
    ADD COLUMN `corrector` VARCHAR(64) DEFAULT NULL;