package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 组合课程导入上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class CombinedCourseImportContext implements Serializable {

	/**
	 * 课时地址绝对路径
	 */
	@ApiModelProperty("课时地址绝对路径")
    private String path;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
    @NotBlank
    private String courseName;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
    @NotBlank
    private String courseNum;

	/**
	 * 课程方向
	 */
	@ApiModelProperty("课程方向")
    @NotBlank
    private String categoryName;

	/**
	 * 课程章节
	 */
	@ApiModelProperty("课程章节")
    @NotBlank
    private String chapterName;

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
    @NotBlank
    private String lessonName;

	/**
	 * 原课程名称
	 */
	@ApiModelProperty("原课程名称")
    private String orgCourseName;

	/**
	 * 原课程章节
	 */
	@ApiModelProperty("原课程章节")
    private String orgChapterName;

	/**
	 * 场景编号
	 */
	@ApiModelProperty("场景编号")
    private String topoId;

}
