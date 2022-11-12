package com.cyberpeace.situation.controller.res;

import com.cyberpeace.situation.dao.entity.DashboardComponents;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author xuxz
 * @Date 11/11/2022 下午5:24
 */
@Data
public class DashboardTemplateRes implements Serializable {
    @ApiModelProperty("模板资源id")
    private String resourceId;

    @ApiModelProperty("模板名称")
    private String name;

    @ApiModelProperty("模板类型")
    private String type;

    @ApiModelProperty("模板级别")
    private Integer level;

    @ApiModelProperty("缩略图")
    private String thumbnail;

    @ApiModelProperty("组件配置信息")
    private List<DashboardComponents> componentsSetting;
}
