package com.cyberpeace.space.controller.req;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.model.req.SpaceToolPageReq;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 空间事件集成统治分页请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceToolPageReq
 * @since 1.0.0
 */
@ApiModel
public class SpaceEventIntegrationRulePageReq extends SpaceToolPageReq {

    @Getter
    @Setter
    private String type;
    @Override
    public LambdaQueryWrapper<SpaceEventIntegrationRule> req2QueryWrapper() {
        LambdaQueryWrapper<SpaceEventIntegrationRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SpaceEventIntegrationRule::getUpdateTime);

        if (StringUtils.isNotBlank(this.getTool())) {
            wrapper.like(SpaceEventIntegrationRule::getTool, this.getTool());
        }

        if (StringUtils.isNotBlank(this.getType())) {
            wrapper.like(SpaceEventIntegrationRule::getType, this.getType());
        }
        if (null != this.getStage()) {
            wrapper.eq(SpaceEventIntegrationRule::getStage, this.getStage());
        }
        wrapper.setEntityClass(SpaceEventIntegrationRule.class);
        return wrapper;
    }
}
