package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 分阶段防御类别
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `StagedDefenseCategory` 的 Java 类，用于描述分阶段防御的相关信息。以下是简要分析： 
 * 1. **类的作用**： 
 * 这个类表示一个“分阶段防御类别”，主要用于划分防御的不同阶段（如事前、事中、事后）以及对应的防御类别。 
 * 2. **字段说明**： 
 * - `stage`：表示防御的阶段，用整数表示（1:事前阶段，2:事中阶段，3:事后阶段）。通过 `@ApiModelProperty` 注解描述了其含义。 
 * - `categories`：是一个 `List<TacticDefenseCategoryBo>` 类型的字段，表示每个阶段下的具体防御类别。 
 * 3. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * - `@ApiModelProperty` 是 Swagger 提供的注解，用于生成 API 文档时描述字段的含义。 
 * 4. **用途**： 
 * 此类可能在需要分阶段处理防御策略的场景中使用，例如网络安全或风险管理领域。 
 * 总结：这是一个简单的数据传输对象（DTO），用于封装分阶段防御的阶段和对应类别信息。 
**/
@Getter
@Setter
public class StagedDefenseCategory {

	/**
	 * 阶段
	 */
	@ApiModelProperty(value = "1:事前阶段，2:事中阶段，3:事后阶段")
	private Integer stage;

    /**
     * 类别
     */
    private List<TacticDefenseCategoryBo> categories;
}

