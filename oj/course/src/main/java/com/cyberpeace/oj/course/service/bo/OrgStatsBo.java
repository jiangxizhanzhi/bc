/**
 * 这段代码定义了一个名为 `OrgStatsBo` 的 Java 类，该类位于 `com.cyberpeace.oj.course.service.bo` 包中。以下是对此代码的简要分析： 
 * ### 类定义和继承 
 * - **类名**: `OrgStatsBo` 
 * - **包**: `com.cyberpeace.oj.course.service.bo` 
 * - **继承**: 该类继承自 `PublicStatsBo`。 
 * - **注解**: 
 * - `@Getter`: 自动生成所有属性的 getter 方法。 
 * - `@Setter`: 自动生成所有属性的 setter 方法。 
 * - `@ApiModel("组织学习统计信息")`: 使用 Swagger 注解来描述这个 BO（业务对象）。 
 * ### 属性 
 * - `private String resourceId;`: 用于存储资源 ID，注释说明它表示用户的 resource_id。 
 * - `private String organizationData;`: 用于存储组织数据，注释说明它表示组织名。 
 * - `private Integer orgUserCount;`: 用于存储组织用户数量，注释说明它表示组织人数。 
 * ### 注解 
 * - `@ApiModelProperty("用户resource_id")`: 描述 `resourceId` 属性。 
 * - `@ApiModelProperty("组织名")`: 描述 `organizationData` 属性。 
 * - `@ApiModelProperty("组织人数")`: 描述 `orgUserCount` 属性。 
 * ### 总结 
 * - `OrgStatsBo` 是一个用于组织学习统计的业务对象。 
 * - 它包含三个主要属性：`resourceId`、`organizationData` 和 `orgUserCount`，分别用于存储资源 ID、组织名和组织人数。 
 * - 该类通过 Lombok 注解简化了 getter 和 setter 方法的生成。 
 * - 使用 Swagger 注解来提供 API 文档中的描述信息。 
 * 这段代码主要用于在系统中表示和处理组织学习相关的统计数据。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 组织统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicStatsBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("组织学习统计信息")
public class OrgStatsBo extends PublicStatsBo {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("用户resource_id")
    private String resourceId;

    /**
     * 组织数据
     *
     * @see String
     */
    @ApiModelProperty("组织名")
    private String organizationData;

    /**
     * 组织用户计数
     *
     * @see Integer
     */
    @ApiModelProperty("组织人数")
    private Integer orgUserCount;

}
