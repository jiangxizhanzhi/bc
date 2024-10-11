package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 训练分数
 *
 * @author SN
 * @date 2023/05/23
 */
@Data
public class TrainingScore implements Serializable {

	@ApiModelProperty(value = "训练得分")
	private Integer score = 0;

	@ApiModelProperty(value = "训练总分")
	private Integer total = 0;

	@ApiModelProperty(value = "各阶段")
	private List<StageNameScore> stages = Lists.newArrayList();

	public void addScore(Integer addend) {
		this.score += addend;
	}

	public void addTotal(Integer addend) {
		this.total += addend;
	}
}
