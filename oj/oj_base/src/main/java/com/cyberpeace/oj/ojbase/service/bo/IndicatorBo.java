/**
 * 这段代码定义了一个名为 `IndicatorBo` 的 Java 类，该类位于包 `com.cyberpeace.oj.ojbase.service.bo` 中。下面是对其主要特性和功能的简要分析： 
 * 1. **包声明**： 
 * - 代码的第一行指定了该类所在的包为 `com.cyberpeace.oj.ojbase.service.bo`。 
 * 2. **导入语句**： 
 * - 导入了 `com.google.common.collect.Lists`，这表明该类使用了 Google Guava 库中的 `Lists` 工具类来创建列表。 
 * - 同时，导入了 Lombok 的 `@Getter` 和 `@Setter` 注解，这些注解将自动生成属性的 getter 和 setter 方法，从而简化代码并减少样板代码。 
 * 3. **类定义**： 
 * - 类 `IndicatorBo` 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，这些注解会自动生成公共的 getter 和 setter 方法来访问私有字段。 
 * - 类中定义了两个私有字段：`id`（整数类型）和 `name`（字符串类型），用于存储指标的唯一标识符和名称。 
 * - 类中还定义了一个私有字段 `children`，它是一个 `IndicatorBo` 对象的列表。这个字段用来表示当前指标下的子指标集合，这表明 `IndicatorBo` 类可能被用于构建指标树结构的数据模型。 
 * 4. **数据结构**： 
 * - `IndicatorBo` 类的设计使得它可以用于表示一个具有层次结构的数据模型，其中每个 `IndicatorBo` 实例可以包含多个子实例，形成树形结构。 
 * 总结来说，这段代码定义了一个简单的、带有嵌套关系的指标对象模型。通过使用 Lombok 的注解，减少了手动编写 getter 和 setter 方法的工作量，并且通过定义 `children` 字段，支持了指标之间的父子关系，适合用于实现具有层级结构的数据处理场景。 
**/
package com.cyberpeace.oj.ojbase.service.bo;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class IndicatorBo {

	private Integer id;

	private String name;

	private List<IndicatorBo> children = Lists.newArrayList();

}
