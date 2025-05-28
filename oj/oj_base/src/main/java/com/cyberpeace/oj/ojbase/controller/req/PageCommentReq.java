/**
 * 这段代码定义了一个名为 `PageCommentReq` 的 Java 类，它是 `PageReqBase` 类的一个子类。这个类主要用于处理分页查询评论请求的参数。以下是对代码的简要分析： 
 * ### 类和继承关系 
 * - **类名**: `PageCommentReq` 
 * - **包**: `com.cyberpeace.oj.ojbase.controller.req` 
 * - **继承**: 继承自 `PageReqBase` 类 
 * ### 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解，来自 Lombok 库，用于自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述 API 参数。 
 * ### 属性 
 * - **businessId**: 业务 ID，表示资源编号。类型为 `String`。 
 * - **businessType**: 业务类型，表示资源类型。类型为 `Integer`。 
 * ### 方法 
 * - `setBusiness_id(String businessId)`: 设置 `businessId` 属性的方法。 
 * - `setBusiness_type(Integer businessType)`: 设置 `businessType` 属性的方法。 
 * ### 功能描述 
 * - 这个类主要用于处理分页查询评论请求，包括业务 ID 和业务类型两个参数。 
 * - 通过继承 `PageReqBase` 类，可能还包含了其他通用的分页参数（如 `pageNo`、`pageSize` 等）。 
 * ### 总结 
 * - `PageCommentReq` 类是一个用于处理分页查询评论请求的模型类。 
 * - 它包含了两个主要属性：业务 ID 和业务类型，并提供了相应的 setter 方法。 
 * - 通过继承 `PageReqBase` 类，可以利用其提供的通用分页功能。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页评论请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
public class PageCommentReq extends PageReqBase {

    /**
     * 业务id
     *
     * @see String
     */
    @ApiModelProperty(name = "business_id", value = "资源编号")
    private String businessId;

    /**
     * 业务类型
     *
     * @see Integer
     */
    @ApiModelProperty(name = "business_type", value = "资源类型 1=课程 2=课时 3=实操题")
    private Integer businessType;

    public void setBusiness_id(String businessId) {
        this.businessId = businessId;
    }

    public void setBusiness_type(Integer businessType) {
        this.businessType = businessType;
    }
}
