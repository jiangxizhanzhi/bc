package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练分数
 *
 * @author SN
 * @date 2023/05/23
 */
@Getter
@Setter
public class TrainingScore implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 得分
	 */
	@ApiModelProperty(value = "训练得分")
	private Integer score = 0;

	/**
	 * 总
	 */
	@ApiModelProperty(value = "训练总分")
	private Integer total = 0;

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "各阶段")
	private List<StageNameScore> stages = Lists.newArrayList();

	public void addScore(Integer addend) {
		this.score += addend;
	}

	public void addTotal(Integer addend) {
		this.total += addend;
	}
}
