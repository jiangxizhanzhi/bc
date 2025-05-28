/**
 * 这段代码定义了一个名为 `SpaceTacticDefensePlanService` 的接口，它继承了 MyBatis-Plus 提供的通用服务接口 `IService`，并扩展了两个自定义方法。以下是详细的分析： 
 * ### **1. 接口定义与继承** 
 * - **继承自 `IService`**： 
 * - `IService` 是 MyBatis-Plus 框架中的一个通用服务接口，通常用于提供基本的 CRUD 操作。 
 * - 这里通过继承 `IService<SpaceTacticDefensePlan>`，表明该服务将针对 `SpaceTacticDefensePlan` 实体类提供增删改查等基础功能。 
 * ### **2. 自定义方法** 
 * #### 方法 1: `getFrameLevels` 
 * - **功能**：根据传入的 `SpaceTacticDefensePlan` 对象，获取其对应的 `SpaceFrameLevel`。 
 * - **参数**： 
 * - `plan`：类型为 `SpaceTacticDefensePlan`，表示需要分析的防御计划对象。 
 * - **返回值**： 
 * - 返回值类型为 `SpaceFrameLevel`，可能是某种框架级别的信息或状态。 
 * #### 方法 2: `renewFrame` 
 * - **功能**：根据传入的请求体对象 `DefensePlanLevelUpdateReq`，更新框架并返回新的 `SpaceFrameLevel`。 
 * - **参数**： 
 * - `req`：类型为 `DefensePlanLevelUpdateReq`，包含更新所需的数据。 
 * - **返回值**： 
 * - 返回值类型为 `SpaceFrameLevel`，表示更新后的框架级别。 
 * ### **3. 注解与文档** 
 * - **注释**： 
 * - 类上的 Javadoc 注释清晰地描述了该接口的作用，即“空间战法防御计划Service”。 
 * - 每个方法都有详细的 Javadoc 注释，说明了方法的功能、参数和返回值。 
 * - **引用**： 
 * - 接口依赖了多个其他类和上下文对象（如 `SpaceFrameLevel` 和 `DefensePlanLevelUpdateReq`），表明该服务可能涉及复杂的业务逻辑。 
 * ### **4. 总结** 
 * 该接口是一个典型的业务服务接口，主要负责处理与“空间战法防御计划”相关的业务逻辑。它不仅继承了通用的 CRUD 功能，还通过自定义方法扩展了特定的业务需求，如框架级别计算和更新。代码结构清晰，注释详尽，便于理解和维护。 
**/

package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.DefensePlanLevelUpdateReq;

/**
 * 空间战法防御计划Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
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
