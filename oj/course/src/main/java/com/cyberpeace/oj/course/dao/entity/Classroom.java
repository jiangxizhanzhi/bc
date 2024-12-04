package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课堂
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("oj_classroom")
public class Classroom extends BaseTable {

    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("课程表编号")
    private String resourceId;

    /**
     * 开始时间
     *
     * @see Date
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 课程id
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
    private String courseId;

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("课时编号")
    private String lessonId;

    /**
     * 组织id
     *
     * @see String
     */
    @ApiModelProperty("组织编号")
    private String organizationId;

    /**
     * 组织id列表
     *
     * @see String
     */
    @ApiModelProperty("子组织编号")
    private String organizationIds;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建者编号")
    private String createUserId;

    /**
     * 训练计划id
     *
     * @see String
     */
    @ApiModelProperty("训练计划编号")
    private String trainPlanId;

}
