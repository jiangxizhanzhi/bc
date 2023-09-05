package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
@TableName("cr_role_asset_node_copy")
public class CrRoleAssetNodeCopy extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String roomId;

    /**
     * 目前归属角色id
     */
//    private String roleId;

    // todo 如果role_type一致，跳过事件； 不一致更新发送事件
    /**
     * 目前归属角色组类型, 目前仅支持4/5
     */
    private Integer roleType;

    // todo 如果device_id有不同role_type，不发送事件
    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 中台存储数据，为操作系统、网络终端等
     */
    private Integer nodeCategory;

    /**
     * 资产原归属角色id
     */
//    private String originalRoleId;

    /**
     * 资产原归属角色组类型
     */
    private Integer originalRoleType;

    private String evalTemplateId;

}
