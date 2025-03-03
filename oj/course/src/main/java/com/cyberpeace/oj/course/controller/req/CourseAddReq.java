package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 课程新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("课程新增信息")
public class CourseAddReq implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@NotBlank(groups = { Create.class })
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程描述")
	private String description;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@NotNull(groups = { Create.class })
	@ApiModelProperty("难度，type=51")
	private Integer difficulty;

	/**
	 * 等级
	 *
	 * @see Integer
	 */
	@ApiModelProperty("等级,type=9")
	private Integer rank;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程分类 0=基础 2=攻击 3=防御")
	private Integer courseType;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseNum;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位")
	private Integer role;

	public void validate() {
		// 校验分数最大值
		int scoreMax = 1000;
		if (null != score ) {
			CrCommonValidator.validateScore(score, scoreMax, OjBusinessErrorCode.MAX_SCORE_1000);
		}
		// 验证名称长度
		if(null != lecturer && !lecturer.isEmpty()){
			int lenMin = 1;
			int lenMax = 64;
			CrCommonValidator.validateRange(lecturer.length(),lenMin, lenMax, OjBusinessErrorCode.MAX_64_CHARACTERS);
		}
	}

	public String getName() {
		return StringUtils.isNotBlank(name) ? name.trim() : name;
	}
}
