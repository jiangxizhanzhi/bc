package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameOrderBo;
import lombok.Data;

import java.util.Map;

/**
 * 空间行动框架次序
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BaseTable
 * @since 1.0.0
 */
@Data
@TableName("space_action_frame_order")
public class SpaceActionFrameOrder extends BaseTable {

	@TableId(type = IdType.ASSIGN_UUID)
	private String resourceId;

	private String applicationId;

	private String rvbId;

	private String frameName;

	private String step;

	private String tacticId;

	private Integer tacticType;

	private String userId;

	private String userName;

	private String sender;

	private String senderName;

	private Integer status;

	public SpaceActionFrameOrderBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameOrderBo bo = BeanUtils.copy(this, SpaceActionFrameOrderBo.class);
		return bo;
	}
}
