package com.cyberpeace.rtks.controller.res.skill;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.rtks.service.bo.skill.MissionPathBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练路径结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TrainingPathRes implements Serializable {

	/**
	 * 路径
	 */
	@ApiModelProperty(value = "训练路径")
	private List<MissionPathBo> path;

	/**
	 * 相关
	 */
	@ApiModelProperty(value = "全部训练")
	private List<RelatedTrainingRes> related;
}
