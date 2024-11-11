package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 课程新增请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("课程新增信息")
public class CourseAddReq implements Serializable {

	/**
	 * 名称
	 */
	@NotBlank(groups = { Create.class })
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("课程描述")
	private String description;

	/**
	 * 类别id
	 */
	@ApiModelProperty("课程方向")
	@NotBlank(groups = { Create.class })
	private String categoryId;

	/**
	 * 图标
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 大纲
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 困难
	 */
	@NotNull(groups = { Create.class })
	@ApiModelProperty("难度")
	private Integer difficulty;

	/**
	 * 是公共
	 */
	@ApiModelProperty("是否公开")
	@NotNull(groups = { Create.class })
	private Boolean isPublic;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程分类 0=基础 2=攻击 3=防御")
	private Integer courseType;

	/**
	 * 讲师
	 */
	@ApiModelProperty("讲师")
	private String lecturer;

	/**
	 * 得分
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程数量
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseNum;

	public void validate() {
		// 校验分数最大值
		int scoreMax = Integer.parseInt(ConfigUtil.getContextProperty("oj.score.max"));
		if (null != score ) {
			CrCommonValidator.validateScore(score, scoreMax, OjBusinessErrorCode.MAX_SCORE_1000);
		}
		// 验证名称长度
		if(null != lecturer && !lecturer.isEmpty()){
			int lenMin = Integer.parseInt(ConfigUtil.getContextProperty("oj.str.len.name.min"));
			int lenMax = Integer.parseInt(ConfigUtil.getContextProperty("oj.str.len.name.max"));
			CrCommonValidator.validateRange(lecturer.length(),lenMin, lenMax, OjBusinessErrorCode.MAX_64_CHARACTERS);
		}
	}

	public String getName() {
		return StringUtils.isNotBlank(name) ? name.trim() : name;
	}
}
