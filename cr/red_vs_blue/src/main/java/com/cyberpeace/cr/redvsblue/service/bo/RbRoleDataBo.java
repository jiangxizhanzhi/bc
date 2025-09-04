package com.cyberpeace.cr.redvsblue.service.bo;

import com.cyberpeace.common.context.role.CrRoleNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RbRoleDataBo {

    /**
     * 角色名
     */
    @ApiModelProperty(value = "角色名")
    private String name;

    /**
     * 资产节点
     */
    @ApiModelProperty(value = "资产节点")
    private List<CrRoleNode> assetNodes;

    /**
     * 战争迷雾
     */
    @ApiModelProperty(value = "战争迷雾")
    private List<CrRoleNode> visibleNodes;

    /**
     * 当前角色关联席位id
     */
    @ApiModelProperty(value = "当前角色关联席位id列表")
    private List<String> seatsInfo;
}
