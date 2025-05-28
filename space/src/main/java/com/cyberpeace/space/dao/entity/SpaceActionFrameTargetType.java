package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameTargetTypeBo;
import lombok.Data;

import java.util.Map;


/**
 * 空间行动框架目标类型
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceBaseTable
 * @since 1.0.0
 */
@Data
@TableName("space_action_frame_target_type")
public class SpaceActionFrameTargetType extends SpaceBaseTable {

	private String keyWords;

	private String keyWordsPrimary;

	public SpaceActionFrameTargetTypeBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameTargetTypeBo bo = BeanUtils.copy(this, SpaceActionFrameTargetTypeBo.class);
		return bo;
    }
}
    