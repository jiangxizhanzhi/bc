package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

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
     * 训练用户id
     */
    private String userId;

    /**
     * 训练角色id
     */
    private String roleId;

    /**
     * 训练角色类型（区分红蓝队伍）
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

    /**
     * 模块事件类型
     */
    private Integer moduleEvent;

    /**
     * 动态时间
     */
    private Date actionTime;

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

    public enum TeamEvent {
        RECEIVE_TASK(1, "查看任务"),
        SUBMIT_TASK(2, "队伍动态"),
        FINISH_TASK(3, "完成任务"),
        ACCESS_TERMINAL(4, "接入靶标");

        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        TeamEvent(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

    public enum PlatformEvent {
        CLOSE_TASK(1, "关闭任务"),
        RELEASE_TASK(2, "下发任务"),
        RED_NOTICE(3, "发布红队任务"),
        BLUE_NOTICE(4, "发布蓝队公告"),
        RELEASE_NOTICE(5, "发布全局公告");
        @Getter
        private final Integer code;

        @Getter
        private final String desc;

        PlatformEvent(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }

}
