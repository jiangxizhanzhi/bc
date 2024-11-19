package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * mp token校验返回的用户模型
 *
 * @author yinqingrong@date 2024/11/14@see Serializable
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
@Data
public class MpAuthenticatedUser implements Serializable {

	/**
	 * 生日
	 *
	 * @see String
	 */
	private String birthday;

	/**
	 * 标识符
	 *
	 * @see String
	 */
	private String identifier;

	/**
	 * 地址
	 *
	 * @see String
	 */
	private String address;

	/**
	 * 审计
	 */
	private boolean audited;

	/**
	 * 上次登录
	 *
	 * @see String
	 */
	private String lastLogin;

	/**
	 * 移动
	 *
	 * @see String
	 */
	private String mobile;

	/**
	 * id卡
	 *
	 * @see String
	 */
	private String idCard;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 过期时间
	 *
	 * @see Date
	 */
	private Date expiredTime;

	/**
	 * 化身
	 *
	 * @see String
	 */
	private String avatar;

	/**
	 * init 检查
	 *
	 * @see Integer
	 */
	private Integer initCheck;

	/**
	 * 启用
	 */
	private boolean enabled;

	/**
	 * 用户实际姓名
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 昵称
	 *
	 * @see String
	 */
	private String nickname;

	/**
	 * 用户名
	 *
	 * @see String
	 */
	private String username;

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 加入日期
	 *
	 * @see Date
	 */
	private Date dateJoined;
}
