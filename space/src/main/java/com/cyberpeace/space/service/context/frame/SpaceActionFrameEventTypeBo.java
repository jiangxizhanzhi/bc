
package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import com.cyberpeace.space.model.SpaceBoBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间行动框架事件类型Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBoBase
 * @since 1.0.0
 */
@Data
public class SpaceActionFrameEventTypeBo extends SpaceBoBase {

	@ApiModelProperty(value = "资源id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	public SpaceActionFrameEventType bo2Entity() {
		SpaceActionFrameEventType entity = BeanUtils.copy(this, SpaceActionFrameEventType.class);
		return entity;
	}

}
    