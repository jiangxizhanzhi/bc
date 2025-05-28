
package com.cyberpeace.space.service.context.rule;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间手指打印Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceFingerPrintBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;
    

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "指纹列表")
	private String content;

	public SpaceFingerPrint bo2Entity() {
		SpaceFingerPrint entity = BeanUtils.copy(this, SpaceFingerPrint.class);
		return entity;
	}

}
    