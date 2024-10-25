package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameLevel;

public interface SpaceActionFrameService extends IService<SpaceActionFrame> {

		Integer countByType(Integer code);

    SpaceActionFrameLevel getFrameLevels(String resourceId);
}