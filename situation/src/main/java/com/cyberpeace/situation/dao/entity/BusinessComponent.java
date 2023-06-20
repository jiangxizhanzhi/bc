package com.cyberpeace.situation.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.situation.constant.dashboard.CustomCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 业务组件
 *
 * @author dell
 * @date 2023/06/20
 */
@Data
@TableName("situation_business_component")
public class BusinessComponent extends BaseTable {

        @TableId(type = IdType.UUID, value = "resource_id")
        private String resourceId;

        @ApiModelProperty("模板名称")
        private String name;

        @ApiModelProperty("模板类型")
        private String type;

        @ApiModelProperty("缩略图")
        private String thumbnail;

        @ApiModelProperty("组件配置")
        private String config;

        @ApiModelProperty("图层信息")
        private String layerShow;

        @ApiModelProperty("组件配置")
        private String componentsSetting;

        @ApiModelProperty("创建人")
        private String createUserId;
}
