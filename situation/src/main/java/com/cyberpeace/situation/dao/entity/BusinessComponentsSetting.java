package com.cyberpeace.situation.dao.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.situation.constant.dashboard.CustomCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务组件设置
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
@TableName("situation_business_components_setting")
public class BusinessComponentsSetting extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    @ApiModelProperty("视角类型")
    private String type;

    @ApiModelProperty("数据源id")
    private String datasourceId;

    @ApiModelProperty("数据源配置")
    private String datasourceSetting;

    @ApiModelProperty("组件id")
    private String componentsId;

    @ApiModelProperty("组件配置")
    private String componentSetting;

    @ApiModelProperty("组件配置对象")
    @TableField(exist = false)
    private JSONObject componentSettingObject;

    @ApiModelProperty("图层顺序")
    private Integer componentIndex;
}
