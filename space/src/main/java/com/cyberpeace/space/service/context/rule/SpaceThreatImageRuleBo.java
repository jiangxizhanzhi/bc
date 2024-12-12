package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间威胁图像规则 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Getter
@Setter
public class SpaceThreatImageRuleBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 类型
	 */
	@ApiModelProperty(value = "威胁类型, 1:僵尸攻击,2:木马攻击，3:蠕虫攻击")
	private Integer type;

	/**
	 * 水平
	 */
	@ApiModelProperty(value = "威胁等级，1:低威胁，2:中威胁，3:高威胁")
	private Integer level;

	/**
	 * 资产类型
	 */
	@ApiModelProperty(value = "受害资产类型, 1:终端设备,2:网络设备,3:服务器设备")
	private Integer assetType;

	@ApiModelProperty(value = "威胁情报")
	private String information;

	@ApiModelProperty(value = "结果，1:尝试,2:成功,3:失败")
	private Integer result;

	@ApiModelProperty(value = "标签")
	private String tag;

	/**
	 * BO2 实体
	 *
	 * @return {@code SpaceThreatImageRule }
	 */
	public SpaceThreatImageRule bo2Entity() {
		SpaceThreatImageRule entity = BeanUtils.copy(this, SpaceThreatImageRule.class);
		return entity;
	}

}
    