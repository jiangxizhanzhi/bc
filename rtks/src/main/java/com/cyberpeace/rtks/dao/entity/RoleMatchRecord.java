package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 角色匹配记录
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_role_match_record")
public class RoleMatchRecord implements Serializable {

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 角色id
	 */
	private String roleId;

	/**
	 * 火柴
	 */
	private Integer match;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
