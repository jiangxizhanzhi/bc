package com.cyberpeace.space.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceBaseAlgorithmBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_base_algorithm")
public class SpaceBaseAlgorithm extends SpaceBaseTable {

	@ApiModelProperty(value = "简述")
	private String description;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "代码语言类型")
	private Integer codeType;

	@ApiModelProperty(value = "代码")
	private String code;

	@ApiModelProperty(value = "参数")
	private String parameter;


	public SpaceBaseAlgorithmBo entity2Bo() {
		SpaceBaseAlgorithmBo bo = BeanUtils.copy(this, SpaceBaseAlgorithmBo.class);
		return bo;
	}
}
