package com.cyberpeace.oj.course.controller.res;

import com.cyberpeace.common.util.ConfigUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;

@Data
public class WebCourseRes implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("课程简介")
	private String description;

	@ApiModelProperty("课程方向")
	private String categoryId;

	@ApiModelProperty("课程海报")
	private String logo;

	@ApiModelProperty("课程大纲")
	private String outline;

	@ApiModelProperty("课程难度")
	private Integer difficulty;

	@ApiModelProperty("课程讲师")
	private String lecturer;

	@ApiModelProperty("课程学分")
	private Integer score;

	@ApiModelProperty("课程方向名称")
	private String directionName;

	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("访问量")
	private Integer heat;

}
