-- 修改唯一索引
DROP INDEX room_user ON cr_eval_score;
ALTER TABLE cr_eval_score ADD UNIQUE room_user (`eval_template_id`,`room_id`,`user_id`);