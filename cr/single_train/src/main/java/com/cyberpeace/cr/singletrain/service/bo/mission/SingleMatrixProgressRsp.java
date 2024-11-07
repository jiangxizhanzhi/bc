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
 * @date 2024/11/07
 */
@Data
public class SingleMatrixProgressRsp implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;

	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户姓名")
	private String userName;

	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "用户头像")
	private String userAvatar;

	/**
	 * 开始
	 */
	@ApiModelProperty(value = "训练是否开启")
	private Boolean started;

	/**
	 * 成功
	 */
	@ApiModelProperty(value = "成功数")
	private Integer successful;

	/**
	 * 失败
	 */
	@ApiModelProperty(value = "失败数")
	private Integer failed;

	/**
	 * 任务计数
	 */
	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	/**
	 * 完成
	 */
	@ApiModelProperty(value = "任务已完成数")
	private Integer finished;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 时间成本
	 */
	@ApiModelProperty(value = "训练时长")
	private String timeCost;

	/**
	 * 矩阵
	 */
	@ApiModelProperty(value = "任务矩阵")
	private List<MatrixStage> matrix = Lists.newArrayList();
}
