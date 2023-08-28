-- 删除唯一索引(红队/蓝队/日常 business_type相同)
ALTER TABLE cr_business_template DROP INDEX cr_business_template_name_type;