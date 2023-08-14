package com.cyberpeace.cr.evaluation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_eval_export_record")
public class EvalExportRecord extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")

    private String resourceId;

    private String record;

    private String exportFileMd5;

}
