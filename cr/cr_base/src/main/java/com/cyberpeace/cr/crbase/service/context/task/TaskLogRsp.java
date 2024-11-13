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
 * @date 2024/11/13
 */
@Data
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

    /**
     * 行动代号
     *
     * @author SN
     * @date 2024/11/13
     */
    public enum ActionCode implements GenericEnum {

        /**
         * 下发
         */
        RELEASE(1, "下发任务"),
        /**
         * 开启题解
         */
        OPEN_WRITEUP(2, "开放题解"),
        /**
         * 关闭题解
         */
        CLOSE_WRITEUP(3, "关闭题解"),
        /**
         * 提交
         */
        SUBMIT(4,
                "上报任务"),
        /**
         * 机评成功
         */
        ROBOT_SUCCESS(5, "机评成功"),
        /**
         * 机评失败
         */
        ROBOT_FAILURE(6, "机评失败"),
        /**
         * 人评成功
         */
        MAN_SUCCESS(7,
                "人评成功"),
        /**
         * 人评失败
         */
        MAN_FAILURE(8, "人评失败"),
        /**
         * 人评驳回
         */
        MAN_REJECT(9, "人评驳回");

        /**
         * 代号
         */
        @Getter
        private Integer code;

        /**
         * 描述
         */
        @Getter
        private String desc;

        ActionCode(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }



    }

}
