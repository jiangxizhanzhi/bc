/**
 * 这段代码定义了一个名为 `IndexScene` 的 Java 类，用于表示某种编号场景。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `IndexScene` 类主要用于描述不同场景的计数情况，例如人工智能（AI）、怪胎（极客闯关）、RVB（红蓝对抗）、模拟推演等。 
 * 2. **字段说明**： 
 * - `ai`：表示人机对抗场景，初始值为 0。 
 * - `geek`：表示极客闯关场景，初始值为 0。 
 * - `rvb`：表示红蓝对抗场景，初始值为 0。 
 * - `simulation`：表示模拟推演场景，初始值为 0。 
 * - `sum`：表示所有场景的总数，初始值为 0。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加了描述信息，便于生成 API 文档。 
 * 4. **设计意图**： 
 * - 这个类可能用于统计或管理不同场景的运行次数或状态，方便后续处理和展示。 
 * 总结：这是一个简单的数据模型类，用于封装与场景相关的计数信息，并通过注解增强了代码的可读性和可维护性。 
**/
package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号场景
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexScene {

    /**
     * 人工智能
     *
     * @see Integer
     */
    @ApiModelProperty(value = "人机对抗场景")
    private Integer ai = 0;
    /**
     * 怪胎
     *
     * @see Integer
     */
    @ApiModelProperty(value = "极客闯关场景")
    private Integer geek = 0;
    /**
     * RVB
     *
     * @see Integer
     */
    @ApiModelProperty(value = "红蓝对抗场景")
    private Integer rvb = 0;
    /**
     * 模拟
     *
     * @see Integer
     */
    @ApiModelProperty(value = "模拟推演场景")
    private Integer simulation = 0;
    /**
     * 和
     *
     * @see Integer
     */
    @ApiModelProperty(value = "")
    private Integer sum = 0;

}
