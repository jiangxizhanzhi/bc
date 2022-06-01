start transaction;

ALTER TABLE oj_classroom ADD UNIQUE INDEX org_lesson_uniq(organization_id, lesson_id);
ALTER TABLE oj_classroom ADD UNIQUE INDEX resource_uniq(resource_id);
ALTER TABLE oj_classroom_group_info ADD UNIQUE INDEX resource_uniq(resource_id);
ALTER TABLE oj_classroom_group_info ADD UNIQUE INDEX room_group_uniq(classroom_id, group_no);
ALTER TABLE oj_classroom_group_archive ADD UNIQUE INDEX room_name_uniq(classroom_id, name);

ALTER TABLE oj_course ADD UNIQUE INDEX name_uniq(name);
ALTER TABLE oj_course_lesson ADD UNIQUE course_name_uniq(course_id, name);
ALTER TABLE oj_lesson_paper_evaluate ADD UNIQUE INDEX lesson_user_uniq(lesson_id, submit_user_id);

ALTER TABLE oj_train_plan ADD UNIQUE user_name_uniq(create_user_id, name);

ALTER TABLE oj_practice_category ADD UNIQUE INDEX resource_uniq(resource_id);
ALTER TABLE oj_practice_category ADD UNIQUE INDEX type_name_uniq(practice_type, name);

ALTER TABLE oj_practice_set ADD UNIQUE name_uniq(name);
ALTER TABLE oj_practice_theory MODIFY COLUMN content VARCHAR(512);
ALTER TABLE oj_practice_theory ADD UNIQUE set_content_uniq(practice_set_id, content);
ALTER TABLE oj_practice_theory_ref ADD UNIQUE paper_origin_uniq(paper_id, origin_id);

ALTER TABLE oj_practice_operation MODIFY COLUMN title VARCHAR(128);
ALTER TABLE oj_practice_operation ADD UNIQUE resource_uniq(resource_id);
ALTER TABLE oj_practice_operation ADD UNIQUE set_title_uniq(practice_set_id, title);
ALTER TABLE oj_practice_operation_ref ADD UNIQUE paper_origin_uniq(paper_id, origin_id);
ALTER TABLE oj_practice_random_flag ADD UNIQUE prac_type_user_uniq(practice_id, type, user_id);

commit;
