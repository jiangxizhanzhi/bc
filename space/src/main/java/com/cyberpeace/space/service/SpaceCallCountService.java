package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceCallCount;

/**
 * 调用计数Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/13
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceCallCountService` 的接口，继承自 `IService<SpaceCallCount>`。以下是代码的简要分析： 
 * 1. **接口定义**： 
 * - `SpaceCallCountService` 是一个服务接口，扩展了 `IService<SpaceCallCount>`，表明它是一个基于 MyBatis-Plus 的服务层接口。 
 * 2. **方法说明**： 
 * - `addCount(String name)`：用于新增计数，参数为名称。 
 * - `getCountByName(String name)`：根据名称获取计数，返回值为 `Integer`。 
 * - `addCount(String name, int num)`：新增计数，参数包括名称和数量。 
 * 3. **注释**： 
 * - 每个方法都有详细的注释，描述了方法的功能、参数和返回值。 
 * 4. **用途**： 
 * - 该接口主要用于管理与 `SpaceCallCount` 实体相关的计数操作，适用于需要统计和更新特定名称调用次数的场景。 
 * 总体来说，这是一个清晰且功能明确的服务接口，适合用于处理与调用计数相关的需求。 
**/
public interface SpaceCallCountService extends IService<SpaceCallCount> {
    /**
     * 新增计数
     *
     * @param name 名称
     */
    void addCount(String name);

    /**
     * 获取计数由名称
     *
     * @param name 名称
     * @return {@code Integer }
     */
    Integer getCountByName(String name);

    /**
     * 新增计数
     *
     * @param name 名称
     * @param num  数量
     * @throws
     * @see String
     * @see int
     */
    void addCount(String name, int num);
}

