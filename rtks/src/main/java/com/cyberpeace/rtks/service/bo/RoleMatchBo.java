package com.cyberpeace.rtks.service.bo;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色匹配 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@ApiModel("岗位匹配度数据统计计算")
public class RoleMatchBo implements Serializable {

    /**
     * 过程
     */
    @ApiModelProperty("岗位课程完成进度")
    private Integer process;

    /**
     * 理论计数
     */
    @ApiModelProperty("完成理论总课时")
    private Integer theoryCount;

    /**
     * 锻炼计数
     */
    @ApiModelProperty("完成实验总课时")
    private Integer exerciseCount;

    /**
     * 课程
     */
    @ApiModelProperty("课程进度信息")
	private List<CourseKnowledgeSkillBo> courses;

}
