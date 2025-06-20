package com.cyberpeace.integratation.model.mp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 中台认证许可
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpAuthPermission` 的 Java 类，用于表示用户菜单权限。以下是对其主要特性和功能的简要分析： 
 * ### 包与导入 
 * - **包声明**：`com.cyberpeace.integratation.model.mp` 表示这个类位于 `com.cyberpeace.integratation.model.mp` 包中。 
 * - **导入**： 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 用于生成 Swagger 文档，描述 API 模型和模型属性。 
 * - `lombok.Getter` 和 `lombok.Setter` 注解由 Lombok 库提供，用于自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - **类名**：`MpAuthPermission` 
 * - **实现接口**：`Serializable`，表示该类的对象可以被序列化（即可以转换为字节流进行存储或传输）。 
 * ### 类注释 
 * - 类注释使用了 Javadoc 格式，说明这是一个“中台认证许可”类，并指明了作者和创建日期。 
 * ### 属性 
 * - `pk`：主键，类型为 `Integer`。 
 * - `id`：唯一标识符，类型为 `String`。 
 * - `createTime`：创建时间，类型为 `Date`。 
 * - `updateTime`：更新时间，类型为 `Date`。 
 * - `parent`：父级菜单，类型为 `String`。 
 * - `identifier`：菜单标识符，类型为 `String`。 
 * - `tenant`：租户（可能指用户所属的组织或项目），类型为 `String`。 
 * - `name`：菜单名称，类型为 `String`。 
 * - `path`：菜单路径，类型为 `String`。 
 * - `type`：菜单类型，类型为 `String`。 
 * - `visible`：菜单是否可见，类型为 `String`。 
 * ### 注解 
 * - `@ApiModelProperty`：用于在 Swagger 文档中添加额外的描述信息，例如“父级菜单”、“菜单标识符”、“菜单名称”和“菜单是否可见”。 
 * ### 总结 
 * `MpAuthPermission` 类是一个数据传输对象（DTO），用于表示用户菜单权限相关的数据。它包含了多个属性，每个属性都有相应的注释和 Swagger 文档描述。Lombok 注解简化了代码，减少了样板代码的编写。 
**/
@Getter
@Setter
@ApiModel("用户菜单权限")
public class MpAuthPermission implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * PK
     */
    private Integer pk;

    /**
     * id
     */
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 父母
     */
    @ApiModelProperty("父级菜单")
    private String parent;

    /**
     * 标识符
     */
    @ApiModelProperty("菜单标识符")
    private String identifier;

    /**
     * 房客
     */
    private String tenant;

    /**
     * 名称
     */
    @ApiModelProperty("菜单名称")
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 类型
     */
    private String type;

    /**
     * 可见
     */
    @ApiModelProperty("菜单是否可见")
    private String visible;
}

