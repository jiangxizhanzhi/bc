package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习试卷评估博
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PracticePaperEvaluateBo implements Serializable {

	/**
	 * 名称
	 */
	@ApiModelProperty("姓名")
	private String name;

	/**
	 * 用户名
	 */
	@ApiModelProperty("用户名")
	private String username;

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户id")
	private String userId;

	/**
	 * 组织名称
	 */
	@ApiModelProperty("组织名称")
	private String organizationName;

	/**
	 * 提交状态
	 */
	@ApiModelProperty("提交状态")
	private Boolean submitState=false;

	/**
	 * 提交时间
	 */
	@ApiModelProperty("提交时间")
	private Date submitTime;

	/**
	 * 准确性
	 */
	@ApiModelProperty("理论题正确率")
	private String accuracy;

	/**
	 * 水平
	 */
	@ApiModelProperty("评分等级")
	private Integer level;

	/**
	 * 校正者
	 */
	@ApiModelProperty("批改人")
	private String corrector;

	/**
	 * 正确时间
	 */
	@ApiModelProperty("批改时间")
	private Date correctTime;

}
