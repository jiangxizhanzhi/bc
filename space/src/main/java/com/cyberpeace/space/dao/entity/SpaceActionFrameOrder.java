package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
@TableName("space_action_frame_order")
public class SpaceActionFrameOrder extends BaseTable {

	@ApiModelProperty("自增ID")
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String applicationId;

	private String applicationName;

	private String stage;

	private String tacticId;

	private Integer tacticType;

	private String tacticName;

	private String userId;

	private String userName;

	private Integer status;

	public SpaceActionFrameOrderBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameOrderBo bo = BeanUtils.copy(this, SpaceActionFrameOrderBo.class);
		return bo;
	}
}
