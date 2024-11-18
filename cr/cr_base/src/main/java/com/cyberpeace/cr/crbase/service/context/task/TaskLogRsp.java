package com.cyberpeace.cr.crbase.service.context.task;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务日志响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TaskLogRsp implements Serializable {
    /**
     * 房间id
     *
     * @see String
     */
    private String roomId;

    /**
     * 任务id
     *
     * @see String
     */
    private String missionId;

    /**
     * 任务id
     *
     * @see String
     */
    private String taskId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

    /**
     * 行动代号
     *
     * @see Integer
     */
    private Integer actionCode;

    /**
     * 用户id
     *
     * @see String
     */
    private String userId;

    /**
     * 行动名称
     *
     * @see String
     */
    private String actionName;

    /**
     * 创建时间
     *
     * @see Date
     */
    private Date createTime;

    /**
     * 创建时间字符串
     *
     * @see String
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
