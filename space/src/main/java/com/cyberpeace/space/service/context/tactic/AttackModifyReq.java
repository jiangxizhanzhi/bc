package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 攻击修改请求体
 *
 * @author SN
 * @date 2024/11/07
 */
/**
 * 这段代码定义了一个名为 `AttackModifyReq` 的类，它是 `AttackAddReq` 类的子类。以下是简要分析： 
 * 1. **类的功能**： 
 * `AttackModifyReq` 是一个用于处理攻击修改请求的类，继承自 `AttackAddReq`，表示它可能包含一些通用的攻击相关字段，并在此基础上增加了特定于修改操作的属性。 
 * 2. **关键字段**： 
 * - `resourceId`：表示资源 ID，用于标识需要修改的具体资源。 
 * 3. **注解功能**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，为类中的字段自动生成 getter 和 setter 方法，简化了代码编写。 
 * 4. **继承关系**： 
 * - `AttackModifyReq` 继承自 `AttackAddReq`，表明它可能复用了一些与攻击相关的通用逻辑或字段。 
 * 5. **设计意图**： 
 * - 该类的设计目的是为了支持攻击修改操作，通过扩展父类来满足特定需求（如新增 `resourceId` 字段）。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装攻击修改请求的相关信息，继承自通用的攻击添加请求类，并新增了资源 ID 字段以满足修改场景的需求。 
**/
@Getter
@Setter
public class AttackModifyReq extends AttackAddReq {

	/**
	 * 资源id
	 */
	private String resourceId;
}

