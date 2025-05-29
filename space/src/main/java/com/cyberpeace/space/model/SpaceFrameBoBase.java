package com.cyberpeace.space.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 空间框架Bo基础
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个抽象类 `SpaceFrameBoBase`，它是空间框架业务对象（BO）的基础类。以下是代码的简要分析： 
 * 1. **类的定义**： 
 * - 类名为 `SpaceFrameBoBase`，位于包 `com.cyberpeace.space.model` 下。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **属性**： 
 * - 包含一个私有字符串类型的属性 `resourceId`，用于标识资源 ID。 
 * 3. **方法**： 
 * - 抽象方法 `bo2Entity()`：定义了将业务对象（BO）转换为实体对象（Entity）的方法，返回类型为 `SpaceFrameBaseTable`。 
 * 4. **用途**： 
 * - 作为基类，供具体的业务对象继承使用，提供通用的属性和方法，便于统一管理。 
 * 总结：这是一个抽象类，提供了基本的属性和方法，用于支持空间框架中的业务对象与实体对象之间的转换。 
**/
@Getter
@Setter
public abstract class SpaceFrameBoBase {

	private String resourceId;

	public abstract SpaceFrameBaseTable bo2Entity();
}

