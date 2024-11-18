package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Data;
import lombok.Getter;

/**
 * 用户看到的红点提示
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/06/07
 * @since 1.0.0
 */
@Data
public class CrBadge {

	/**
	 * id
	 *
	 * @see Integer
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	private String businessId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 额外
	 *
	 * @see String
	 */
	private String extra;

	/**
	 * 创建时间
	 *
	 * @see Date
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	public enum BadgeType implements GenericEnum {

		NOTICE(1, "notice"),
		MISSION_RELEASE(2, "任务下发提示"), NEW_SUBMIT(3, "待评估的提交");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		BadgeType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
