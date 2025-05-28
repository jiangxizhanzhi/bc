/**
 * 这段代码定义了一个用于分页查询空间事件集成规则的请求体类 `SpaceEventIntegrationRulePageReq`，继承自 `SpaceToolPageReq`。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **封装分页查询参数**： 
 * 类中包含了分页查询所需的通用字段（如 `type`）以及一些特定的查询条件字段（如 `tool`, `stage`），用于动态生成查询条件。 
 * 2. **动态生成查询条件**： 
 * 提供了 `req2QueryWrapper()` 方法，将请求体中的查询条件转化为 `LambdaQueryWrapper` 对象，用于后续的数据库查询操作。 
 * 3. **支持多种查询方式**： 
 * - 支持模糊匹配（如 `tool` 和 `type` 字段）。 
 * - 支持精确匹配（如 `stage` 字段）。 
 * - 默认按 `updateTime` 字段降序排序。 
 * 4. **基于 Lombok 的简化**： 
 * 使用了 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，减少冗余代码。 
 * ### 关键点分析： 
 * - **继承关系**： 
 * 继承自 `SpaceToolPageReq`，可能包含了一些通用的分页参数（如 `pageNo`, `pageSize`）。 
 * - **方法逻辑**： 
 * - `req2QueryWrapper()` 方法的核心是动态拼接查询条件，利用 `LambdaQueryWrapper` 的链式调用构建查询逻辑。 
 * - 检查字段是否为空或非空，决定是否添加对应的查询条件。 
 * - 设置默认排序规则（按 `updateTime` 降序）。 
 * - **依赖库**： 
 * - 使用了 `com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper` 进行查询条件构建。 
 * - 使用了 `org.apache.commons.lang3.StringUtils` 判断字符串是否为空。 
 * - 使用了 `io.swagger.annotations.ApiModel` 标记该类为 Swagger API 文档的一部分。 
 * ### 总结： 
 * 该类的设计目的是为了提供一个灵活且可扩展的分页查询请求体，能够根据不同的查询条件动态生成数据库查询条件，并支持多种查询方式（模糊匹配、精确匹配）。代码结构清晰，使用了现代 Java 编程的最佳实践（如 Lombok 和 Lambda 表达式）。 
**/
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
