package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_role_asset_node")
public class CrRoleAssetNode extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String roomId;

    private String roleId;

    // todo 如果role_type一致，跳过事件； 不一致更新发送事件
    /**
     * 目前仅支持4/5
     */
    private Integer roleType;

    // todo 如果device_id有不同role_type，不发送事件
    private String deviceId;

    // 中台存储数据，为操作系统、网络终端等
    private Integer nodeCategory;

    private String originalRoleId;

    private Integer originalRoleType;

    public enum RoleType {
        RED(1, "红队"),
        BLUE(2, "蓝队");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        RoleType(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
