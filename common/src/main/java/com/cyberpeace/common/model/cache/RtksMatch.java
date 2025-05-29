package com.cyberpeace.common.model.cache;

import com.cyberpeace.common.model.user.UserInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * rtks 匹配度缓存
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `RtksMatch` 的 Java 类，用于表示 RTKS 匹配度缓存。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `RtksMatch` 类的主要功能是存储与用户相关的匹配度信息，包括用户 ID、组织 ID、角色 ID、角色名称、匹配度、姓名、组织名称等。 
 * 2. **属性**： 
 * - 类中有多个私有属性，如 `userId`, `orgId`, `roleId`, `roleName`, `match`, `name`, `orgName`，这些属性通过 Lombok 注解 `@Getter` 和 `@Setter` 自动生成了 getter 和 setter 方法。 
 * 3. **构造函数**： 
 * - 提供了一个构造函数 `RtksMatch(UserInfo userInfo)`，用于根据 `UserInfo` 对象初始化 `RtksMatch` 实例。该构造函数会设置用户的 ID、姓名、组织 ID 和组织名称。 
 * 4. **辅助方法**： 
 * - `getOrgName(List<List<String>> orgPath)` 是一个私有方法，用于从 `UserInfo` 对象中的组织路径中提取组织名称。 
 * 5. **序列化支持**： 
 * - 通过实现 `Serializable` 接口，该类可以被序列化，便于在网络中传输或持久化存储。 
 * 6. **注释**： 
 * - 类和方法都有详细的注释，说明了其用途和参数含义，有助于代码的理解和维护。 
 * 总结来说，这个类主要用于封装和管理用户在 RTKS 系统中的匹配度相关信息，并提供了便捷的方法来初始化和获取数据。 
**/
@Getter
@Setter
public class RtksMatch implements Serializable {

	private String userId;

	private String orgId;

	private String roleId;

	private String roleName;

	private Integer match;

	private String name;

	private String orgName;

	/**
	 * RTKS 比赛
	 *
	 * @param userInfo 用户信息
	 */
	public RtksMatch(UserInfo userInfo) {
		this.userId = userInfo.getUserId();
		this.name = userInfo.getName();
		this.orgId = userInfo.getOrganizationId();
		this.orgName = getOrgName(userInfo.getOrgpaths());
	}

	/**
	 * 获取组织名称
	 *
	 * @param orgPath 组织路径
	 * @return {@code String }
	 */
	private String getOrgName(List<List<String>> orgPath) {
		int size = orgPath.get(0).size();
		return orgPath.get(0).get(size - 1);
	}

}

