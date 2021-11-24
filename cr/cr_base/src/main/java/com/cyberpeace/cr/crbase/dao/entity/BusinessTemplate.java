package com.cyberpeace.cr.crbase.dao.entity;

import java.util.Arrays;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;

import lombok.Data;
import lombok.Getter;

/**
 * 靶场模板公共信息模型
 * */
@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	private String topoId;

	private String name;

	private String description;

	private Integer businessType;

	private Integer difficulty;

	private String createUserId;

	public enum BusinessType implements GenericEnum {

		TRAINING_SINGLE(1, "单兵训练", 211), VULN_VALIDATE(2, "漏洞库", 212), TRAINING_TEAM(3, "团队训练",
				213), RED_VS_BLUE(4, "红蓝对抗",
						214), WEAPON_ASSESS(6, "装备测试", 215), SYSTEM_ASSESS(7, "系统测试", 216);

		@Getter
		private Integer code;

		@Getter
		private Integer appId;

		@Getter
		private String desc;

		private BusinessType(Integer code, String desc, Integer appId) {
			this.code = code;
			this.desc = desc;
			this.appId = appId;
		}

		public static BusinessType getByAppId(Integer appId) {
			return Arrays.stream(BusinessType.values()).filter(t -> t.getAppId().equals(appId))
					.findFirst().orElse(null);
		}

	}

}
