package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * mp token校验返回的用户模型
 * @author yinqingrong
 */
@Data
public class MpAuthenticatedUser implements Serializable {

	private String birthday;

	private String identifier;

	private String address;

	private boolean audited;

	private String lastLogin;

	private String mobile;

	private String idCard;

	private String description;

	private Date expiredTime;

	private String avatar;

	private Integer initCheck;

	private boolean enabled;

	/**
	 * 用户实际姓名
	 */
	private String name;

	private String nickname;

	private String username;

	private String id;

	private Date dateJoined;

	private Boolean isTrialAccount;

}
