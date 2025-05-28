package com.cyberpeace.space.service.context.frame;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间行动框架次序列表Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameOrderBo
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionFrameOrderListBo extends SpaceActionFrameOrderBo {

	private String description;

	private Date createTime;
}
