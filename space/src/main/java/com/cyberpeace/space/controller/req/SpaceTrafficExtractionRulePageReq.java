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
    /**
     * 将请求转换为查询包装器
     *
     * 此方法用于将查询请求转换为LambdaQueryWrapper对象，以便进行数据库查询
     * 它会根据请求中的条件设置查询规则，并对结果进行排序
     *
     * @return LambdaQueryWrapper<SpaceTrafficExtractionRule>: 初始化并配置好的查询包装器
     */
    @Override
    public LambdaQueryWrapper<SpaceTrafficExtractionRule> req2QueryWrapper() {
        // 创建一个新的LambdaQueryWrapper对象，用于构建查询条件
        LambdaQueryWrapper<SpaceTrafficExtractionRule> wrapper = new LambdaQueryWrapper<>();
        // 设置查询结果按照更新时间降序排序
        wrapper.orderByDesc(SpaceTrafficExtractionRule::getUpdateTime);

        // 如果请求中的工具字段不为空，则添加模糊查询条件
        if (StringUtils.isNotBlank(this.getTool())) {
            wrapper.like(SpaceTrafficExtractionRule::getTool, this.getTool());
        }
        // 设置实体类，以确保查询条件与数据库表结构一致
        wrapper.setEntityClass(SpaceTrafficExtractionRule.class);
        // 返回配置好的查询包装器
        return wrapper;
    }

}
