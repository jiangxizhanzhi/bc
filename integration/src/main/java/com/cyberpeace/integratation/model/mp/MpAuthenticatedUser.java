package com.cyberpeace.integratation.model.mp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * mp token校验返回的用户模型
 *
 * @author yinqingrong@date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MpAuthenticatedUser` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示通过 MP Token 验证后返回的用户信息模型。以下是该类的主要特点和属性： 
 * ### 主要特点 
 * - **注解**：使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法。 
 * - **序列化**：实现了 `Serializable` 接口，这意味着该对象可以被序列化，便于在网络上传输或保存到磁盘。 
 * ### 属性 
 * - **生日 (`birthday`)**：字符串类型，表示用户的生日。 
 * - **标识符 (`identifier`)**：字符串类型，表示用户的唯一标识符。 
 * - **地址 (`address`)**：字符串类型，表示用户的地址。 
 * - **审计 (`audited`)**：布尔类型，表示用户是否已通过审核。 
 * - **上次登录 (`lastLogin`)**：字符串类型，表示用户上次登录的时间。 
 * - **移动 (`mobile`)**：字符串类型，表示用户的手机号码。 
 * - **ID卡 (`idCard`)**：字符串类型，表示用户的身份证号码。 
 * - **描述 (`description`)**：字符串类型，表示用户的一些描述信息。 
 * - **过期时间 (`expiredTime`)**：`Date` 类型，表示用户的认证令牌的过期时间。 
 * - **化身 (`avatar`)**：字符串类型，表示用户的头像链接。 
 * - **初始化检查 (`initCheck`)**：整数类型，表示初始化检查的状态。 
 * - **启用 (`enabled`)**：布尔类型，表示用户账户是否启用。 
 * - **用户实际姓名 (`name`)**：字符串类型，表示用户的真实姓名。 
 * - **昵称 (`nickname`)**：字符串类型，表示用户的昵称。 
 * - **用户名 (`username`)**：字符串类型，表示用户的登录名。 
 * - **ID (`id`)**：字符串类型，表示用户的唯一标识ID。 
 * - **加入日期 (`dateJoined`)**：`Date` 类型，表示用户注册的日期。 
 * ### 总结 
 * 这个类主要用于存储和传输通过 MP Token 验证后的用户信息。它包含了各种用户相关的属性，并且通过 Lombok 提供的注解简化了代码的编写。这个类的设计是围绕着用户的详细信息展开的，适合在需要用户身份验证和管理的应用场景中使用。 
**/
@Getter
@Setter
public class MpAuthenticatedUser implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 生日
	 */
	private String birthday;

	/**
	 * 标识符
	 */
	private String identifier;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 审计
	 */
	private boolean audited;

	/**
	 * 上次登录
	 */
	private String lastLogin;

	/**
	 * 移动
	 */
	private String mobile;

	/**
	 * id卡
	 */
	private String idCard;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 过期时间
	 */
	private Date expiredTime;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * init 检查
	 */
	private Integer initCheck;

	/**
	 * 启用
	 */
	private boolean enabled;

	/**
	 * 用户实际姓名
	 */
	private String name;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * id
	 */
	private String id;

	/**
	 * 加入日期
	 */
	private Date dateJoined;

	private String menusContent;
}

