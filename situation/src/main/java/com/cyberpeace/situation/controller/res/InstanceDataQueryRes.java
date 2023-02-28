package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.dao.entity.DashboardComponents;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class InstanceDataQueryRes implements Serializable {

    @ApiModelProperty("组件配置信息")
    private List<DashboardComponents> componentsSetting;

}
