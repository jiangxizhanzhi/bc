package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("oj_classroom")
public class Classroom extends BaseTable {

    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("课程表编号")
    private String resourceId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("课程编号")
    private String courseId;

    @ApiModelProperty("课时编号")
    private String lessonId;

    @ApiModelProperty("组织编号")
    private String organizationId;

    @ApiModelProperty("子组织编号")
    private String organizationIds;

    @ApiModelProperty("创建者编号")
    private String createUserId;

    @ApiModelProperty("训练计划编号")
    private String trainPlanId;

    private Boolean enableRecording;

	private Boolean showGuidance;

}
