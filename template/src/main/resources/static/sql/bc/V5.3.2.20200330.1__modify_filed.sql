/** 修改课时表字段字符集*/
ALTER TABLE oj_course_lesson CHANGE handout_url handout_url LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

/** 修改课程表大纲字段*/
ALTER TABLE oj_course CHANGE outline outline LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

/** 修改实操习题表字段*/
ALTER TABLE oj_practice_operation CHANGE writeup writeup LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
ALTER TABLE oj_practice_operation CHANGE content content LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

/** 修改理论习题表字段*/
ALTER TABLE oj_practice_theory CHANGE content content LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
ALTER TABLE oj_practice_theory CHANGE `option` `option` LONGTEXT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

/** 增加唯一索引*/
ALTER TABLE local_scene ADD CONSTRAINT businessInstanceUniq UNIQUE(business_instance_id);