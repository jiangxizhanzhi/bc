
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间得分排名统治Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceScoreRankingRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    
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

	public SpaceScoreRankingRule bo2Entity() {
		SpaceScoreRankingRule entity = BeanUtils.copy(this, SpaceScoreRankingRule.class);
		return entity;
	}

}
    