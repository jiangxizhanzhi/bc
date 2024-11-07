package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.CourseKnowledgeSkillBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 职业课程结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("职业课程学习进度")
public class OccupationCourseRes implements Serializable {

    /**
     * 课程百分比
     */
    @ApiModelProperty("课程进度")
    private Integer coursePercent;

    /**
     * 理论百分之
     */
    @ApiModelProperty("理论课时进度")
    private Integer theoryPercent;

    /**
     * 运动百分比
     */
    @ApiModelProperty("实验课时进度")
    private Integer exercisePercent;

    /**
     * 知识 BOS
     */
    @ApiModelProperty("课程知识点信息")
	private List<CourseKnowledgeSkillBo> knowledgeBos;

}
