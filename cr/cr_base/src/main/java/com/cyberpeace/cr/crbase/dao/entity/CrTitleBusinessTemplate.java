package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_title_business_template")
public class CrTitleBusinessTemplate extends BaseTable {
    private String titleId;

    private String businessTemplateId;
}
