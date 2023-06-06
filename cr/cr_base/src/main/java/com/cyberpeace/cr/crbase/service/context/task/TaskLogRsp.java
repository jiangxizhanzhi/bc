package com.cyberpeace.cr.crbase.service.context.task;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class TaskLogRsp {
    private String roomId;

    private String missionId;

    private String taskId;

    private String name;

    private Integer actionCode;

    private String userId;

    private String actionName;

    private Date createTime;

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
