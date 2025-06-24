package com.cyberpeace.cr.redvsblue.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

import java.util.Date;

/**
 * cr标记
 *
 * @author SN
 * @date 2023/05/24
 */
@Data
@TableName(value = "cr_unite_business_task")
public class CrUniteBusinessTask extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String taskId;

    private String taskName;

    private String taskDescription;

    private Date startTime;

    private Date endTime;

    private String trainTarget;
}
