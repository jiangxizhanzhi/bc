package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceAptReportBo;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_apt_report")
public class SpaceAptReport extends SpaceBaseTable {

	private String aptOrg;

	private String publicTime;

	private String publicCompany;

	private Integer type;

	private String reportPath;

	@Override
	public SpaceAptReportBo entity2Bo(Map<String, IdAndName> relatedMap) {
        SpaceAptReportBo bo = BeanUtils.copy(this, SpaceAptReportBo.class);
        return bo;
    }
}
