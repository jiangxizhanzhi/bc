/**
 * 这段代码定义了一个名为 `CourseAdminBo` 的类，它是 `CourseWebBo` 类的子类。以下是对其主要特性和功能的简要分析： 
 * ### 包声明和导入 
 * - 该类位于 `com.cyberpeace.oj.course.service.bo` 包下。 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 引入了 Swagger 注解 (`@ApiModel` 和 `@ApiModelProperty`) 来描述 API 模型。 
 * ### 类注释 
 * - 类使用 Javadoc 风格的注释进行文档化，说明了这是一个“后台课程信息”对象。 
 * - 注释还包含了作者、版本、创建日期等元数据。 
 * ### 属性 
 * - `auth`: 一个整数类型的变量，通过 `@ApiModelProperty` 注解描述为“授权访问类型”。 
 * - `authCount`: 一个整数类型的变量，描述为“授权访问数量”。 
 * - `share`: 一个整数类型的变量，描述为“授权操作类型”。 
 * - `shareCount`: 一个整数类型的变量，描述为“授权操作数量”。 
 * - `builtin`: 一个布尔类型的变量，描述为“是否内置”。 
 * ### 继承 
 * - `CourseAdminBo` 继承自 `CourseWebBo` 类，这意味着它可能包含 `CourseWebBo` 中定义的所有公共属性和方法。 
 * ### 总结 
 * 这个类主要用于表示后台管理系统中的课程信息，特别是与权限管理和共享操作相关的属性。通过继承 `CourseWebBo` 类，它可以扩展或覆盖其父类的功能。Swagger 注解使得这个类在生成 API 文档时更加清晰易懂。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程管理员 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseWebBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("后台课程信息")
public class CourseAdminBo extends CourseWebBo {

    /**
     * 认证
     *
     * @see Integer
     */
    @ApiModelProperty("授权访问类型")
    private Integer auth;

    /**
     * 认证计数
     *
     * @see Integer
     */
    @ApiModelProperty("授权访问数量")
    private Integer authCount;

    /**
     * 共享
     *
     * @see Integer
     */
    @ApiModelProperty("授权操作类型")
    private Integer share;

    /**
     * 共享计数
     *
     * @see Integer
     */
    @ApiModelProperty("授权操作数量")
    private Integer shareCount;
    /**
     * 内置
     *
     * @see Boolean
     */
    @ApiModelProperty("是否内置")
    private Boolean builtin;
}
