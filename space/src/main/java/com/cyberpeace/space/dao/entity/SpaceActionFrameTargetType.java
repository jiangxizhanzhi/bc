package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameTargetTypeBo;
import lombok.Data;

import java.util.Map;


@Data
@TableName("space_action_frame_target_type")
public class SpaceActionFrameTargetType extends SpaceBaseTable {

	public SpaceActionFrameTargetTypeBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameTargetTypeBo bo = BeanUtils.copy(this, SpaceActionFrameTargetTypeBo.class);
		return bo;
    }
}
    