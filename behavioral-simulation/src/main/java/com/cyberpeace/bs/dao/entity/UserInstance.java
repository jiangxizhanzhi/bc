package com.cyberpeace.bs.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * 用户实例
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
@TableName("bs_user_instance")
public class UserInstance implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型id
     */
    private String userTypeId;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 开始时间
     */
    private LocalTime startTime;

    /**
     * 结束时间
     */
    private LocalTime endTime;

    /**
     * 访问配置
     */
    private Integer accessConfig;

    /**
     * 行为列表
     */
    private String behaviorList;

    /**
     * 行为变量
     */
    private String behaviorVariable;

    /**
     * 行为统计
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
     * 用户仪表id
     */
    private String userSimulatorId;

    /**
     * 用户模型id
     */
    private String userModelId;
}
