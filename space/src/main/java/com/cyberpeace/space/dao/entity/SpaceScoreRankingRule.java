package com.cyberpeace.space.dao.entity;

import java.util.Map;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceScoreRankingRuleBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_score_ranking_rule")
public class SpaceScoreRankingRule extends SpaceBaseTable {
    @ApiModelProperty(value = "规则简述")
	private String description;
    @ApiModelProperty(value = "单任务记分")
	private Integer taskScoreType;
    @ApiModelProperty(value = "单轮次记分")
	private Integer roundScoreType;
    @ApiModelProperty(value = "累计记分")
	private Integer sumScoreType;
    @ApiModelProperty(value = "一血奖励")
	private Integer firstBlood;
    @ApiModelProperty(value = "二血奖励")
	private Integer secondBlood;
    @ApiModelProperty(value = "三血奖励")
	private Integer thirdBlood;


	public SpaceScoreRankingRuleBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceScoreRankingRuleBo bo = BeanUtils.copy(this, SpaceScoreRankingRuleBo.class);
		return bo;
	}
}
