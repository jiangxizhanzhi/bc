/**
 * 这段代码定义了一个名为 `BusinessTemplateData` 的 Java 类，它继承自 `BusinessTemplate` 类。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * `BusinessTemplateData` 是一个用于表示靶场模板公共信息的实体类。它扩展了 `BusinessTemplate` 类，并添加了两个新的字段：`nodesNum` 和 `resourceOccupiedNums`。 
 * 2. **字段说明**： 
 * - `nodesNum`：表示节点的数量。 
 * - `resourceOccupiedNums`：表示资源占用的数量。 
 * 3. **注解**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法，简化了代码编写。 
 * 4. **继承关系**： 
 * 该类继承自 `BusinessTemplate`，表明它可能复用了 `BusinessTemplate` 中的一些通用属性或方法。 
 * 5. **用途**： 
 * 这个类可能是用于描述靶场环境中模板相关的具体数据，例如节点和资源的使用情况。 
 * 总结：这是一个简单的实体类，通过继承和新增字段来扩展功能，便于在业务逻辑中存储和操作靶场模板的相关信息。 
**/
package com.cyberpeace.cr.crbase.dao.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 靶场模板公共信息类
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class BusinessTemplateData extends BusinessTemplate {

	/**
	 * 节点数量
	 */
	private Integer nodesNum;

	/**
	 * 资源占用数
	 */
	private Integer resourceOccupiedNums;

}
