package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author xuxz
 * @Date 11/11/2022 上午11:41
 */
@Data
@TableName("dashboard_components_setting")
public class DashboardComponents {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("组件类型")
    private String type;

    @ApiModelProperty("数据源id")
    private String datasourceId;

    @ApiModelProperty("数据源配置")
    private String datasourceSetting;

    @ApiModelProperty("组件id")
    private String componentsId;

    @ApiModelProperty("组件配置")
    private String componentSetting;
}
