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
 * @date 2024/11/14
 */
@Data
public class SingleMatrixProgressRsp implements Serializable {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private String userId;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
	private String userName;

	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = "用户头像")
	private String userAvatar;

	/**
	 * 场景是否开启
	 */
	@ApiModelProperty(value = "场景是否开启")
	private Boolean started;

	/**
	 * 训练是否正在运行
	 */
	@ApiModelProperty(value = "训练是否正在运行")
	private Boolean running;

	/**
	 * 成功数
	 */
	@ApiModelProperty(value = "成功数")
	private Integer successful;

	/**
	 * 失败数
	 */
	@ApiModelProperty(value = "失败数")
	private Integer failed;

	/**
	 * 任务总数
	 */
	@ApiModelProperty(value = "任务总数")
	private Integer missionCount;

	/**
	 * 任务已完成数
	 */
	@ApiModelProperty(value = "任务已完成数")
	private Integer finished;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "得分")
	private Integer score;

	/**
	 * 训练时长
	 */
	@ApiModelProperty(value = "训练时长")
	private Long timeCost;

	/**
	 * 任务矩阵
	 */
	@ApiModelProperty(value = "任务矩阵")
	private List<MatrixStage> matrix = Lists.newArrayList();
}
