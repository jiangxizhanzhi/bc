package com.cyberpeace.space.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceBaseTable;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameEventTypeBo;
import lombok.Data;

import java.util.Map;


@Data
@TableName("space_action_frame_event_type")
public class SpaceActionFrameEventType extends SpaceBaseTable {

	public SpaceActionFrameEventTypeBo entity2Bo(Map<String, IdAndName> relatedMap) {
		SpaceActionFrameEventTypeBo bo = BeanUtils.copy(this, SpaceActionFrameEventTypeBo.class);
		return bo;
    }
}
    