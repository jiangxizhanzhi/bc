package com.cyberpeace.cr.redvsblue.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

/**
 * cr标记
 *
 * @author SN
 * @date 2023/05/24
 */
@Data
@TableName(value = "cr_unite_business_link")
public class CrUniteBusinessLink extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @JSONField(name = "task_id")
    private String taskId;

    @JSONField(name = "link_id")
    private String linkId;

    @JSONField(name = "source_number_id")
    private String sourceNumberId;

    @JSONField(name = "target_number_id")
    private String targetNumberId;

    @JSONField(name = "medium_type")
    private String mediumType;

    @JSONField(name = "band_width")
    private Integer bandWidth;

    @JSONField(name = "business_type")
    private String businessType;
}
