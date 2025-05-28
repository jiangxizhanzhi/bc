/**
 * 该代码定义了一个名为 `ListKReq` 的 Java 类，继承自 `PageReqBase`，用于描述课时列表查询的请求参数。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `ListKReq` 是一个用于课时列表查询的请求对象，扩展了 `PageReqBase`，通常用于分页查询。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModel` 和 `@ApiModelProperty` 注解，为类和字段提供 API 文档描述。 
 * 3. **属性说明**： 
 * - `job`：表示岗位，类型为 `Integer`，用于筛选特定岗位的课时。 
 * - `handicap`：表示难度，类型为 `Integer`，用于筛选特定难度的课时。 
 * 4. **继承关系**： 
 * - 继承自 `PageReqBase`，可能包含分页相关的通用字段（如 `pageNum` 和 `pageSize`）。 
 * 5. **文档注释**： 
 * - 类和字段都有详细的 Javadoc 注释，便于开发者理解其用途。 
 * 总结：这是一个用于课时列表查询的请求类，支持分页和筛选功能，适合在前后端交互中使用。 
**/
package com.cyberpeace.space.service.context;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 列出 KREQ
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("课时列表查询")
public class ListKReq extends PageReqBase {

    /**
     * 工作
     *
     * @see Integer
     */
    @ApiModelProperty(value = "岗位")
    private Integer job;

    /**
     * 障碍
     *
     * @see Integer
     */
    @ApiModelProperty(value = "难度")
    private Integer handicap;

}
