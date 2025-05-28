package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceAptReport;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间容易报告Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceAptReportBo extends SpaceBoBase {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "报告名称")
	private String name;

	/**
	 * apt 组织
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "APT组织")
	private String aptOrg;

	/**
	 * 公共时间
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "发现时间")
	private String publicTime;

	/**
	 * 上市公司
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "发布厂商")
	private String publicCompany;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "报告类型， 1：APT组织分析， 2：典型攻击行动分析")
	private Integer type;

	/**
	 * 报告路径
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "报告pdf路径")
	private String reportPath;
	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否内置")
	private Boolean builtin;

	/**
	 * BO2 实体
	 *
	 * @return Spaceapt报告
	 * @throws
	 * @see SpaceAptReport
	 */
	@Override
	public SpaceAptReport bo2Entity() {
        SpaceAptReport entity = BeanUtils.copy(this, SpaceAptReport.class);
        return entity;
	}
}
