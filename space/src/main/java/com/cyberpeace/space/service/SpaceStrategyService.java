package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.service.context.strategy.*;

import java.util.List;

public interface SpaceStrategyService extends IService<SpaceStrategy> {
    SpaceStrategyFrameBo getFrame();

    void addFrame(SpaceStrategyFrameAddReq req);

    void modify(SpaceStrategyFrameModifyReq req);

    void deleteFrame(String resourceId);

    List<SpaceStrategySimple> listStrategiesByFrame(String frameId, String name);

    void addStrategy(SpaceStrategyAddReq req);

    void updateStrategy(SpaceStrategyModifyReq req);

    void deleteStrategy(String resourceId);

    SpaceStrategyBo getDetail(String resourceId);
}
