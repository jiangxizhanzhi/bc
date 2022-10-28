-- 为平台中已有训练数据设置默认值
UPDATE cr_business_template set `read_auth_type` = 2 where read_auth_type is null;