package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

@Data
@TableName("cr_train_action")
public class CrTrainAction extends BaseTable {
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 训练实例id
     */
    private String roomId;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 训练角色id
     */
    private String roleId;

    /**
     * 训练角色类型
     */
    private Integer roleType;

    /**
     * 事件内容
     */
    private String content;

    /**
     * 模块
     */
    private Integer module;

    public enum Type {
        NORMAL(1, "普通"), FINISH(2, "完成");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Type(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum Module {
        PLATFORM(1, "平台动态"), TEAM(2, "队伍动态");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        Module(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
