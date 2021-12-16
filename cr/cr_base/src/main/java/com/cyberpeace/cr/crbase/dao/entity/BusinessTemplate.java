package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.cs.dao.entity.LocalScene;

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

	private String localSceneId;

	public enum BusinessType implements GenericEnum {

		TRAINING_SINGLE(LocalScene.BusinessCode.SINGLE_TRAINING),
		VULN_VALIDATE(LocalScene.BusinessCode.LIVE_VULN),
		TRAINING_TEAM(LocalScene.BusinessCode.TEAM_TRAINING),
		RED_VS_BLUE(LocalScene.BusinessCode.RED_VS_BLUE),
		WEAPON_ASSESS(LocalScene.BusinessCode.WEAPON_TEST),
		SYSTEM_ASSESS(LocalScene.BusinessCode.SYSTEM_TEST);


		@Getter
		private Integer code;

		@Getter
		private String desc;

		private BusinessType(LocalScene.BusinessCode businessCode) {
			this.code = businessCode.getCode();
			this.desc = businessCode.getDesc();
		}
	}
}
