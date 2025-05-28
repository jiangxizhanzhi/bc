/**
 * 这段代码定义了一个名为 `OrgInfo` 的 Java 类，用于表示组织信息。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `OrgInfo` 类用于描述组织的信息，包括组织的 ID、名称、子组织列表、父组织 ID 和完整路径。 
 * 2. **关键字段**： 
 * - `id`：组织的唯一标识符，类型为字符串。 
 * - `name`：组织的名称。 
 * - `children`：存储该组织的所有子组织，是一个 `List<OrgInfo>` 类型的集合，默认初始化为一个空列表。 
 * - `parent`：指向父组织的 ID。 
 * - `path`：组织的完整路径。 
 * 3. **注解与特性**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化代码。 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于在网络传输或持久化时使用。 
 * 4. **设计特点**： 
 * - 使用嵌套结构（`children` 字段）来表示组织之间的层级关系，适合构建树形结构的数据模型。 
 * - 提供了默认的集合初始化（`Lists.newArrayList()`），避免了手动初始化集合的麻烦。 
 * 总结：这是一个简单的数据模型类，用于表示组织的层级信息，支持序列化，并通过 Lombok 减少了样板代码。 
**/
package com.cyberpeace.common.model.user;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 组织信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class OrgInfo implements Serializable {

    /**
     * 组织id，不要使用自增数字编号（必须）
     */
    private String id;

    /**
     * 组织名
     */
    private String name;

    /**
     * 子级组织
     */
    private List<OrgInfo> children = Lists.newArrayList();

    /**
     * 父级组织id
     */
    private String parent;

    /**
     * 组织全程
     */
    private String path;
}
