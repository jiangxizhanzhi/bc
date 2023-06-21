package com.cyberpeace.bs.dao.entity;

import java.time.LocalTime;

import com.baomidou.mybatisplus.annotation.*;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

/**
 * 用户行为仿真仪表
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
@TableName("bs_user_simulator")
public class UserSimulator extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    private String resourceId;

    /**
     * 开始时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private LocalTime endTime;

    /**
     * 访问习惯
     */
    private Integer accessOs;

    /**
     * 用户类型名称
     * 保存名称
     */
    private String userTypes;

    /**
     * 用户数量
     */
    private Integer userCount;

    /**
     * 行为数量
     */
    private Integer behaviorCount;

    /**
     * 访问习惯
     */
    private String accessHabit;

    /**
     * 是否限制带宽
     */
    private Boolean bandwidthLimited;

    /**
     * 带宽
     */
    private Integer bandwidth;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 执行设备id
     */
    private String deviceId;

	/**
	 * 行为节点运维靶标id
	 */
	private String ywId;

    /**
     * 执行设备名称
     */
    private String deviceName;

    /**
     * agent设备的ip
     */
    private String deviceIp;

    /**
     * 设备是否实体设备
     */
    private Boolean isEntity;

    /**
     * 关联场景id
     */
    private String sceneId;

    /**
     * 关联场景名称
     */
    private String sceneName;

    /**
     * 最后执行的行为id
     */
    private String lastExeId;

    /**
     * 最后执行行为的日期
     */
    private String lastExeDate;

    /**
     * lastExeDate当日执行的个数
     */
    private Integer exeCount;

    /**
     * 状态枚举类
     *
     * @author SN
     * @date 2023/05/06
     */
    public enum Status implements GenericEnum {

        NOT_STARTED(0, "未开始"), RUNNING(1, "执行中"), PAUSED(2, "暂停中"), STOPPED(3, "已停止");

        /**
         * 枚举值
         */
        @Getter
        private Integer code;

        /**
         * 描述
         */
        @Getter
        private String desc;

        Status(Integer code, String desc) {
            this.code = code;
            this.desc = desc;
        }
    }
}
