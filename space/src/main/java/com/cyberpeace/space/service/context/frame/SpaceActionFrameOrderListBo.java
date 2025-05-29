package com.cyberpeace.space.service.context.frame;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间行动框架次序列表Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameOrderBo
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceActionFrameOrderListBo` 的 Java 类，它是 `SpaceActionFrameOrderBo` 的子类。以下是对其的简要分析： 
 * 1. **类的继承**： 
 * - `SpaceActionFrameOrderListBo` 继承自 `SpaceActionFrameOrderBo`，表明它扩展了父类的功能。 
 * 2. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成类中所有字段的 getter 和 setter 方法，简化了代码书写。 
 * 3. **字段说明**： 
 * - `description`：一个字符串类型的字段，可能用于描述某些内容。 
 * - `createTime`：一个 `Date` 类型的字段，用于记录创建时间。 
 * 4. **类的用途**： 
 * - 根据类的注释，该类是“空间行动框架次序列表Bo”，可能是用于存储或表示与空间行动框架相关的次序列表数据。 
 * 总结来说，这是一个简单的数据传输对象（DTO），主要用于封装与空间行动框架次序列表相关的信息，并且通过继承和注解提高了代码的简洁性。 
**/
@Getter
@Setter
public class SpaceActionFrameOrderListBo extends SpaceActionFrameOrderBo {

	private String description;

	private Date createTime;
}

