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
    /**
     * 将请求转换为查询包装器
     *
     * 此方法用于将查询请求对象转换为LambdaQueryWrapper查询包装器对象，以便进行后续的数据库操作
     * 它根据请求对象中的条件，动态生成SQL查询条件，实现灵活的查询需求
     *
     * @return LambdaQueryWrapper<SpaceEventIntegrationRule> 返回一个LambdaQueryWrapper对象，用于执行数据库查询
     */
    @Override
    public LambdaQueryWrapper<SpaceEventIntegrationRule> req2QueryWrapper() {
        // 创建一个LambdaQueryWrapper对象，用于构建查询条件
        LambdaQueryWrapper<SpaceEventIntegrationRule> wrapper = new LambdaQueryWrapper<>();
        // 添加排序条件，根据更新时间降序排列查询结果
        wrapper.orderByDesc(SpaceEventIntegrationRule::getUpdateTime);

        // 如果工具名称不为空，添加模糊查询条件
        if (StringUtils.isNotBlank(this.getTool())) {
            wrapper.like(SpaceEventIntegrationRule::getTool, this.getTool());
        }

        // 如果事件类型不为空，添加模糊查询条件
        if (StringUtils.isNotBlank(this.getType())) {
            wrapper.like(SpaceEventIntegrationRule::getType, this.getType());
        }
        // 如果阶段不为空，添加精确查询条件
        if (null != this.getStage()) {
            wrapper.eq(SpaceEventIntegrationRule::getStage, this.getStage());
        }
        // 设置实体类，指定查询的表
        wrapper.setEntityClass(SpaceEventIntegrationRule.class);
        // 返回构建好的查询包装器
        return wrapper;
    }
}
