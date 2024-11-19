package com.cyberpeace.rtks.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * 岗位火柴记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
@TableName("rtks_role_match_record")
public class RoleMatchRecord implements Serializable {

	/**
	 * id
	 *
	 * @see Integer
	 */
	private Integer id;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	private String roleId;

	/**
	 * 火柴
	 *
	 * @see Integer
	 */
	private Integer match;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	private Date updateTime;
}
