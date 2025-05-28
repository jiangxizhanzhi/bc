/**
 * 这段代码定义了一个名为 `SpaceTrafficExtractionRulePageReq` 的类，它是 `SpaceToolPageReq` 的子类。该类的主要功能是将请求参数转换为数据库查询所需的 `LambdaQueryWrapper` 对象，用于从数据库中筛选和排序数据。 
 * ### 主要功能： 
 * 1. **继承与注解**： 
 * - 类通过继承 `SpaceToolPageReq` 来复用其部分逻辑。 
 * - 使用了 `@ApiModel` 注解，表明这是一个模型类，通常用于 Swagger 文档生成。 
 * 2. **核心方法 `req2QueryWrapper`**： 
 * - 方法将请求参数转换为 `LambdaQueryWrapper`，用于构建数据库查询条件。 
 * - 默认按 `updateTime` 字段降序排序。 
 * - 如果请求中包含非空的 `tool` 参数，则添加基于 `tool` 字段的模糊匹配条件。 
 * - 最终返回一个配置好的 `LambdaQueryWrapper` 对象，确保查询条件与目标实体类 `SpaceTrafficExtractionRule` 匹配。 
 * ### 关键点总结： 
 * - **继承与扩展**：继承自 `SpaceToolPageReq`，可能包含一些通用的分页或基础查询逻辑。 
 * - **动态查询条件**：通过判断请求参数（如 `tool`），灵活地构造查询条件。 
 * - **排序机制**：默认按更新时间倒序排列，便于获取最新的记录。 
 * - **工具类集成**：使用 Apache Commons Lang 提供的 `StringUtils` 检查字符串是否为空。 
 * 此代码体现了面向对象设计中的继承与封装思想，同时结合 MyBatis-Plus 的查询能力，实现了灵活且可扩展的分页查询功能。 
**/
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
