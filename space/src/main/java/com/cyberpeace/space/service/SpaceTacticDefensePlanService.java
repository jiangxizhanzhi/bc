
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.DefensePlanLevelUpdateReq;

public interface SpaceTacticDefensePlanService extends IService<SpaceTacticDefensePlan> {

    /**
     * 获取框架水平
     *
     * @param plan 计划
     * @return {@code SpaceFrameLevel }
     */
    SpaceFrameLevel getFrameLevels(SpaceTacticDefensePlan plan);

    /**
     * 更新框架
     *
     * @param req 请求体
     * @return {@code SpaceFrameLevel }
     */
    SpaceFrameLevel renewFrame(DefensePlanLevelUpdateReq req);
}
