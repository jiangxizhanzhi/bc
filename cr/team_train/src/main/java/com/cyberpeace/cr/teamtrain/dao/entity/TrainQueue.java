package com.cyberpeace.cr.teamtrain.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("oj_train_queue")
public class TrainQueue extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    private String roomId;

    private String roomName;

    private String entityDeviceId;

    private LocalDateTime applyTime;

    private Integer status;

    private String operatorUserId;


    public static class Status {
        public static final int WAITING = 0;
        public static final int RUNNING = 1;
        public static final int CANCELED = 2;
        public static final int FINISHED = 3;
        public static final int ERROR = 4;
    }
}
