package com.cyberpeace.space.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceIocThreatBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_ioc_threat")
public class SpaceIocThreat extends SpaceBaseTable {
	@ApiModelProperty(value = "类型")
	private Integer type;
	@ApiModelProperty(value = "IOC列表")
	private String ioc;


	public SpaceIocThreatBo entity2Bo() {
		SpaceIocThreatBo bo = BeanUtils.copy(this, SpaceIocThreatBo.class);
		return bo;
	}
}
