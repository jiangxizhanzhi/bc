package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页子级评论请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PageChildrenCommentReq` 的 Java 类，该类继承自 `PageReqBase`。以下是对其的简要分析： 
 * 1. **包声明**： 
 * - 这个类位于 `com.cyberpeace.oj.ojbase.controller.req` 包下。 
 * 2. **引入的依赖**： 
 * - 引入了 `com.cyberpeace.common.model.PageReqBase`，说明 `PageChildrenCommentReq` 是 `PageReqBase` 的子类。 
 * - 引入了 `io.swagger.annotations.ApiModelProperty`，用于描述 API 模型属性。 
 * - 引入了 `lombok.Getter` 和 `lombok.Setter` 注解，用于简化 getter 和 setter 方法的生成。 
 * 3. **类定义**： 
 * - `PageChildrenCommentReq` 类使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - 类通过继承 `PageReqBase` 来扩展其功能。 
 * 4. **属性**： 
 * - `resourceId`：一个 `String` 类型的属性，表示资源 ID（即一级留言编号）。 
 * - 使用 `@ApiModelProperty` 注解来描述 `resourceId` 属性，用于 API 文档生成。 
 * 5. **方法**： 
 * - `setResource_id` 方法：虽然 `resourceId` 属性已经通过 `@Setter` 注解自动生成了 setter 方法，这里又手动定义了一个同名的方法。然而，由于方法名不同（`setResource_id` vs `setResourceId`），这个手动定义的方法不会覆盖 Lombok 自动生成的 setter 方法。 
 * 总结： 
 * - `PageChildrenCommentReq` 是一个用于分页查询子级评论的请求体类。 
 * - 它继承了 `PageReqBase` 类，并添加了一个额外的 `resourceId` 属性来标识资源 ID。 
 * - 使用 Lombok 简化了 getter 和 setter 方法的编写。 
 * - 通过 Swagger 注解描述了 API 中的模型属性。 
**/
@Getter
@Setter
public class PageChildrenCommentReq extends PageReqBase {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty(name= "resource_id",value = "一级留言编号")
    private String resourceId;

    public void setResource_id(String resourceId){
        this.resourceId = resourceId;
    }
}

