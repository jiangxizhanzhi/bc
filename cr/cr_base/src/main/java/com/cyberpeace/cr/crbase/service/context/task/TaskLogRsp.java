package com.cyberpeace.cr.crbase.service.context.task;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务日志响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TaskLogRsp implements Serializable {
    /**
     * 房间id
     */
    private String roomId;

    /**
     * 任务id
     */
    private String missionId;

    /**
     * 任务id
     */
    private String taskId;

    /**
     * 名称
     */
    private String name;

    /**
     * 行动代号
     */
    private Integer actionCode;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 行动名称
     */
    private String actionName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间字符串
     */
    private String createTimeString;

    public enum ActionCode implements GenericEnum {

        RELEASE(1, "下发任务"), OPEN_WRITEUP(2, "开放题解"), CLOSE_WRITEUP(3, "关闭题解"), SUBMIT(4,
                "上报任务"), ROBOT_SUCCESS(5, "机评成功"), ROBOT_FAILURE(6, "机评失败"), MAN_SUCCESS(7,
                "人评成功"), MAN_FAILURE(8, "人评失败"), MAN_REJECT(9, "人评驳回");

        @Getter
        private Integer code;

        @Getter
        private String desc;

        ActionCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }



    }

}
