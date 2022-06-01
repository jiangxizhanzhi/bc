ALTER TABLE `init_rtks_role`
    ADD COLUMN `knowledge_weight` tinyint(4) NOT NULL DEFAULT 50,
    ADD COLUMN `skill_missions`   text       NULL     DEFAULT '[]';