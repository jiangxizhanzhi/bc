package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * mp token校验返回的用户模型
 *
 * @author yinqingrong@date 2024/11/11
 */
@Data
public class MpAuthenticatedUser implements Serializable {

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
}
