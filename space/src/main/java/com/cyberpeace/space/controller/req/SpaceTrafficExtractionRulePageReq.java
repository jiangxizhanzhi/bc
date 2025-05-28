package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;
import io.swagger.annotations.ApiModel;
import org.apache.commons.lang3.StringUtils;

/**
 * 空间流量萃取统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
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
