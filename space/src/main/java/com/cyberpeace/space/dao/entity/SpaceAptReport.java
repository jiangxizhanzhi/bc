package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;
import lombok.Data;

import java.util.Map;

/**
 * Space apt 报告
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Data
@TableName("space_apt_report")
public class SpaceAptReport extends SpaceBaseTable {

	private String aptOrg;

	private String publicTime;

	private String publicCompany;

	private Integer type;

	private String reportPath;

	private Boolean builtin;
	@Override
	public SpaceAptReportBo entity2Bo(Map<String, IdAndName> relatedMap) {
        SpaceAptReportBo bo = BeanUtils.copy(this, SpaceAptReportBo.class);
        return bo;
    }
}
