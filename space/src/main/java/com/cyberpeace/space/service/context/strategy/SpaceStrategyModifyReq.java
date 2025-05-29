package com.cyberpeace.space.service.context.strategy;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间策略修改请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceStrategyAddReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceStrategyModifyReq` 的类，它是 `SpaceStrategyAddReq` 类的子类。以下是对代码的简要分析： 
 * 1. **类的继承关系**： 
 * `SpaceStrategyModifyReq` 继承自 `SpaceStrategyAddReq`，这意味着它继承了父类的所有属性和方法，并可以在此基础上添加或修改功能。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成类中所有字段的 getter 和 setter 方法，简化了代码书写。 
 * 3. **类的功能**： 
 * - 该类主要用于表示“空间策略修改请求体”，扩展了 `SpaceStrategyAddReq` 的功能。 
 * - 新增了一个字段 `resourceId`，用于存储资源 ID。这个字段通过 `private String resourceId;` 声明，并通过 Lombok 自动生成访问器（getter 和 setter）。 
 * 4. **注释**： 
 * - 类和字段都有详细的注释，说明了类的作用以及 `resourceId` 的含义，便于开发者理解和维护。 
 * 总结：这是一个简单的 Java 类，用于封装“空间策略修改”的请求数据，新增了一个关键字段 `resourceId`，并利用 Lombok 减少了样板代码。 
**/
@Getter
@Setter
public class SpaceStrategyModifyReq extends SpaceStrategyAddReq{
    /**
     * 资源id
     *
     * @see String
     */
    private String resourceId;
}

