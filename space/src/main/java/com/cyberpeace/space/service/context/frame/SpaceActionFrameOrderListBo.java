package com.cyberpeace.space.service.context.frame;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SpaceActionFrameOrderListBo extends SpaceActionFrameOrderBo {

	private String description;

	private Date createTime;
}
