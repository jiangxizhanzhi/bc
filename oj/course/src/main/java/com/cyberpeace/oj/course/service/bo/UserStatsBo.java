/**
 * 这段代码定义了一个名为 `UserStatsBo` 的 Java 类，该类用于表示用户的学习统计信息。以下是对代码的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **继承关系**：`UserStatsBo` 继承自 `PublicStatsBo`。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：为类添加描述，说明这是一个“用户学习统计信息”。 
 * - `@ApiModelProperty`：为每个属性添加描述，方便 Swagger 文档生成。 
 * ### 属性 
 * - **resourceId**：字符串类型，表示资源 ID。 
 * - **username**：字符串类型，表示用户名。 
 * - **name**：字符串类型，表示用户姓名。 
 * - **organizationData**：字符串类型，表示用户组织信息。 
 * - **roles**：`List<JSONObject>` 类型，表示用户的角色列表。 
 * - **avatar**：字符串类型，表示用户头像。 
 * ### 总结 
 * - **用途**：`UserStatsBo` 类主要用于存储和传输用户的统计信息，如资源 ID、用户名、姓名、组织信息、角色和头像等。 
 * - **设计**：使用了 Lombok 和 Swagger 注解来简化代码和文档生成。 
 * - **继承**：从 `PublicStatsBo` 继承，可能包含一些公共的统计信息字段。 
 * 这个类的设计简洁明了，适合在用户统计相关的场景中使用。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 用户统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicStatsBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("用户学习统计信息")
public class UserStatsBo extends PublicStatsBo {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("用户resource_id")
    private String resourceId;

    /**
     * 用户名
     *
     * @see String
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("用户姓名")
    private String name;

    /**
     * 组织数据
     *
     * @see String
     */
    @ApiModelProperty("用户组织信息")
    private String organizationData;

    /**
     * 角色
     *
     * @see List<JSONObject>
     */
    @ApiModelProperty("用户角色")
    private List<JSONObject> roles;

    /**
     * 化身
     *
     * @see String
     */
    @ApiModelProperty("用户头像")
    private String avatar;


}
