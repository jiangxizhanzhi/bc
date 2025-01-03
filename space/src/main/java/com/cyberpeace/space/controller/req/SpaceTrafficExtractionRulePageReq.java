package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;

@ApiModel
public class SpaceTrafficExtractionRulePageReq extends SpaceToolPageReq {
    @Override
    public LambdaQueryWrapper<SpaceTrafficExtractionRule> req2QueryWrapper() {
        LambdaQueryWrapper<SpaceTrafficExtractionRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SpaceTrafficExtractionRule::getUpdateTime);

        if (StringUtils.isNotBlank(this.getTool())) {
            wrapper.like(SpaceTrafficExtractionRule::getTool, this.getTool());
        }
        wrapper.setEntityClass(SpaceTrafficExtractionRule.class);
        return wrapper;
    }

}
