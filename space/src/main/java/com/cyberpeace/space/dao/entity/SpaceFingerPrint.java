package com.cyberpeace.space.dao.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.rule.SpaceFingerPrintBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("space_finger_print")
public class SpaceFingerPrint extends SpaceBaseTable {

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "指纹列表")
	private String content;

	public SpaceFingerPrintBo entity2Bo() {
		SpaceFingerPrintBo bo = BeanUtils.copy(this, SpaceFingerPrintBo.class);
		return bo;
	}
}
