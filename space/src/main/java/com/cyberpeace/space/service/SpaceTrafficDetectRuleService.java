/**
 * 这段代码定义了一个接口 `SpaceTrafficDetectRuleService`，它继承自 `IService<SpaceTrafficDetectRule>`。以下是对代码的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.space.service` 包中。 
 * 2. **接口定义**：`SpaceTrafficDetectRuleService` 是一个接口，扩展了 `IService<SpaceTrafficDetectRule>` 接口，表明该服务接口主要用于操作 `SpaceTrafficDetectRule` 实体类。 
 * 3. **注释说明**： 
 * - 接口的功能是关于“空间流量检测规则”的服务。 
 * - 提供了作者和版本信息。 
 * - 指定了依赖的其他类和接口，如 `KnowledgeChart` 和 `IService`。 
 * 4. **方法声明**：接口中定义了一个方法 `getRuleNetworkChart()`，返回类型为 `KnowledgeChart`，用于获取规则网络图表。 
 * 总体而言，这段代码是一个服务接口的定义，用于处理与空间流量检测规则相关的业务逻辑。 
**/

package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间流量检测统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceTrafficDetectRuleService extends IService<SpaceTrafficDetectRule>

{
    KnowledgeChart getRuleNetworkChart();

}