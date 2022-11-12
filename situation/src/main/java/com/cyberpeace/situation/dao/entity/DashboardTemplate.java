package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 11/11/2022 上午10:47
 */
@Data
@TableName("dashboard_template")
public class DashboardTemplate extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板类型")
    private String type;

    @ApiModelProperty("模板级别")
    private Integer level;

    @ApiModelProperty("缩略图")
    private String thumbnail;

    @ApiModelProperty("组件配置")
    private String componentsSetting;

}
