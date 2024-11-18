package com.cyberpeace.cr.singletrain.service.bo.mission;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 单矩阵进展响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SingleMatrixProgressRsp implements Serializable {

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户姓名")
	private String userName;

	/**
	 * 用户头像
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "用户头像")
	private String userAvatar;

	/**
	 * 开始
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "训练是否开启")
	private Boolean started;

	/**
	 * 成功
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "成功数")
	private Integer successful;

	/**
	 * 失败
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "失败数")
	private Integer failed;

	/**
	 * 任务计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	/**
	 * 完成
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "任务已完成数")
	private Integer finished;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 时间成本
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	/**
	 * 矩阵
	 *
	 * @see List<MatrixStage>
	 */
	@ApiModelProperty(value = "任务矩阵")
	private List<MatrixStage> matrix = Lists.newArrayList();
}
