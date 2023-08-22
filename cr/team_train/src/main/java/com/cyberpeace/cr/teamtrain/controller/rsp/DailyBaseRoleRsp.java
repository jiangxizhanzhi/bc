package com.cyberpeace.cr.teamtrain.controller.rsp;

import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DailyBaseRoleRsp {

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "组织类型")
    private String type;

    @ApiModelProperty(value = "角色组")
    private Integer roleGroup;

    @ApiModelProperty(value = "角色结构")
    private List<BaseRole> roleStructure;

}