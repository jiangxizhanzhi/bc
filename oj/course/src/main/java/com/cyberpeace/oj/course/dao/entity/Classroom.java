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
 * @date 2024/11/11
 */
@Data
@TableName("oj_classroom")
public class Classroom extends BaseTable {

    /**
     * 资源id
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("课程表编号")
    private String resourceId;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 课程id
     */
    @ApiModelProperty("课程编号")
    private String courseId;

    /**
     * 课id
     */
    @ApiModelProperty("课时编号")
    private String lessonId;

    /**
     * 组织id
     */
    @ApiModelProperty("组织编号")
    private String organizationId;

    /**
     * 组织id列表
     */
    @ApiModelProperty("子组织编号")
    private String organizationIds;

    /**
     * 创造用户id
     */
    @ApiModelProperty("创建者编号")
    private String createUserId;

    /**
     * 训练计划id
     */
    @ApiModelProperty("训练计划编号")
    private String trainPlanId;

}
