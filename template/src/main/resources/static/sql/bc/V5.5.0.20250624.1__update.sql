ALTER TABLE cr_business_template
    ADD COLUMN report_status TINYINT(1) DEFAULT 0  COMMENT '是否上报';