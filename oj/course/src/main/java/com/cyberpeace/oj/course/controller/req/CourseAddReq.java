package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.model.AuthTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课程新增信息")
public class CourseAddReq implements Serializable {

	@NotBlank(groups = { Create.class })
	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("课程描述")
	private String description;

	@ApiModelProperty("课程方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	@ApiModelProperty("课程封面")
	private String logo;

	@ApiModelProperty("课程大纲")
	private String outline;

	@NotNull(groups = { Create.class })
	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("是否公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	@ApiModelProperty("课程分类 0=基础 2=攻击 3=防御")
	private Integer courseType;

	@ApiModelProperty("讲师")
	private String lecturer;

	@ApiModelProperty("学分")
	private Integer score;

	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseNum;

}
