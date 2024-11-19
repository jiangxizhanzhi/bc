package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 保存课程类别请求体
 *
 * @author SN
 * @version 1.0.0
 * @see Serializable
 * @since 1.0.0
 * @date 2024/11/14
 */
@Data
@ApiModel
public class SaveCourseCategoryReq implements Serializable {

	/**
	 * 课程分类名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类名称")
	@NotBlank(groups = { Create.class })
	private String name;

	/**
	 * 父级分类编号 第一级则不传值
	 *
	 * @see String
	 */
	@ApiModelProperty("父级分类编号 第一级则不传值")
	private String parentId;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "课程类型", hidden = true)
	private Integer courseType = CourseCategory.CourseType.BASE.getCode();

	public String getName() {
		return StringUtils.isNotBlank(name) ? name.trim() : name;
	}
}
