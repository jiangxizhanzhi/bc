package com.cyberpeace.cr.crbase.service.context.score;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StageNameScore extends NameScore implements Serializable {

	@ApiModelProperty("任务得分")
	private List<NameScore> missions = Lists.newArrayList();

	public void sum() {
		Integer sum = missions.stream().mapToInt(NameScore::getScore).sum();
		this.setScore(sum);
	}
}
