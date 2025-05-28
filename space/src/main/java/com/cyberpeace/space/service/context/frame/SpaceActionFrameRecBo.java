/**
 * 这段代码定义了一个名为 `SpaceActionFrameRecBo` 的 Java 类，它是类 `SpaceActionFrameBo` 的子类。以下是代码的主要分析： 
 * 1. **类的功能**： 
 * - 该类是一个业务对象（BO），用于表示空间行动框架中的娱乐相关数据。 
 * - 它继承自 `SpaceActionFrameBo`，可能扩展了其功能或属性。 
 * 2. **字段说明**： 
 * - `frame`：通过 `ActionFrameArrangeBo` 对象表示框架编排。 
 * - `strategies`：存储一组 `SpaceStrategyBo` 对象的列表，表示策略列表。 
 * - `tactics`：存储一组 `SpaceTacticBo` 对象的列表，表示战法列表。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供元数据描述。 
 * 4. **设计意图**： 
 * - 该类可能是为了封装与空间行动相关的娱乐功能，包括框架编排、策略和战法的管理。 
 * 总结来说，这个类是一个数据传输对象（DTO），主要用于存储和传递空间行动框架的相关信息。 
**/
package com.cyberpeace.space.service.context.frame;

import com.cyberpeace.space.service.context.strategy.SpaceStrategyBo;
import com.cyberpeace.space.service.context.tactic.SpaceTacticBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 空间行动框架娱乐Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameBo
 * @since 1.0.0
 */
@Setter
@Getter
public class SpaceActionFrameRecBo extends SpaceActionFrameBo {

	@ApiModelProperty(value = "框架编排")
	private ActionFrameArrangeBo frame;

	@ApiModelProperty(value = "策略列表")
	private List<SpaceStrategyBo> strategies;

	@ApiModelProperty(value = "战法列表")
	private List<SpaceTacticBo> tactics;

}
