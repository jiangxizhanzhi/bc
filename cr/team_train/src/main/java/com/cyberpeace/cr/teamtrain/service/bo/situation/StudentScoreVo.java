package com.cyberpeace.cr.teamtrain.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 学员得分VO
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class StudentScoreVo implements Serializable {

	/**
	 * 学员id
	 */
	@ApiModelProperty(value = "学员id")
	private String resourceId;

	/**
	 * 姓名
	 */
	@ApiModelProperty(value = "姓名")
	private String name;

	/**
	 * 头像
	 */
	@ApiModelProperty(value = "头像")
	private String avatar;

	/**
	 * 分数
	 */
	@ApiModelProperty(value = "分数")
	private Integer score = 0;

	public void addScore(Integer addend) {
		this.score += addend;
	}
}
