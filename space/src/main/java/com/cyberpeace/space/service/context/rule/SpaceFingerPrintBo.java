
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
	@ApiModelProperty(value = "名称")
	private String name;


	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "类型")
	private Integer type;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "指纹列表")
	private String content;

	/**
	 * BO2 实体
	 *
	 * @return 隔间指纹
	 * @throws
	 * @see SpaceFingerPrint
	 */
	public SpaceFingerPrint bo2Entity() {
		SpaceFingerPrint entity = BeanUtils.copy(this, SpaceFingerPrint.class);
		return entity;
	}

}
    