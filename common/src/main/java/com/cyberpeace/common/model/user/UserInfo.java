package com.cyberpeace.common.model.user;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 这段代码定义了一个名为 `UserInfo` 的 Java 类，用于表示用户的信息。以下是代码的主要特点和功能分析： 
 * 1. **类的基本信息**： 
 * - 类名为 `UserInfo`，位于包 `com.cyberpeace.common.model.user` 下。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成属性的 getter 和 setter 方法，减少了样板代码。 
 * 2. **实现的功能**： 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化，通常用于在不同层之间传递数据或存储到文件中。 
 * - 提供了多个字段来描述用户的基本信息，如 `userId`、`name`、`userName`、`avatarUrl` 等。 
 * 3. **字段说明**： 
 * - `userId`：用户的唯一标识符，类型为字符串。 
 * - `name`：用户的姓名。 
 * - `userName`：用于前端显示和登录的用户名。 
 * - `avatarUrl`：用户的头像链接。 
 * - `organizationId` 和 `organizationData`：分别表示组织ID和组织名称。 
 * - `roles`：用户的角色列表，使用 `JSONObject` 存储，方便灵活扩展。 
 * - `positionId` 和 `positionName`：当前岗位的相关信息。 
 * - `admin` 和 `superuser`：布尔值，分别表示用户是否为管理员或超级用户。 
 * - `roleId` 和 `roleName`：岗位编号和名称。 
 * - `orgpaths`：组织结构信息，以嵌套列表的形式存储。 
 * - `permissions`：用户拥有的菜单权限集合。 
 * 4. **优点**： 
 * - 使用了 Lombok 注解，简化了代码量。 
 * - 字段注释清晰，便于理解和维护。 
 * - 支持序列化，适合在分布式系统中传递对象。 
 * 总体来说，这个类是一个典型的用户信息模型，适用于需要管理用户详细信息的应用场景。 
**/
@Getter
@Setter
public class UserInfo implements Serializable {

    /**
     * 用户id，不要使用自增数字编号（必须）
     */
    private String userId;
    
	/**
	 * 姓名（文字显示）
	 */
	private String name;

	/**
	 * 用户名（对前端显示，登录使用）
	 */
    private String userName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 组织名
     */
    private String organizationData;

    /**
     * 用户角色(用来做权限校验，和rtks的岗位角色不相关)
     */
    private List<JSONObject> roles = Lists.newArrayList();

    private String positionId;

    private String positionName;

    /**
     * true表示用户为管理员
     */
    private boolean admin;

    /**
     * true表示用户是超级用户
     */
    private boolean superuser;

    /**
     * 岗位编号
     */
    private String roleId;

	/**
	 * 当前岗位名称（rtks相关，与训练的角色无关）
	 */
	private String roleName;

    /**
     * 组织结构信息
     */
    private List<List<String>> orgpaths;

    /**
     * 用户菜单权限
     */
    private Set<String> permissions;

    private String menusContent;

}

