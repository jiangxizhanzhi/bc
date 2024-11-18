package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 阶段名称得分
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see NameScore
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class StageNameScore extends NameScore implements Serializable {

	/**
	 * 任务
	 *
	 * @see List<NameScore>
	 */
	@ApiModelProperty("任务得分")
	private List<NameScore> missions = Lists.newArrayList();

	public void sum() {
		Integer sum = missions.stream().mapToInt(NameScore::getScore).sum();
		this.setScore(sum);
	}
}
