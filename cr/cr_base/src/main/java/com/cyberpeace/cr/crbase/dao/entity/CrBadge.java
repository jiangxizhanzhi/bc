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
 * @date 2023/06/07
 */
@Data
public class CrBadge {

	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer type;

	private String businessId;

	private String userId;

	private String extra;

	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	public enum BadgeType implements GenericEnum {

		NOTICE(1, "notice"),
        MISSION_RELEASE(2, "任务下发提示");

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
