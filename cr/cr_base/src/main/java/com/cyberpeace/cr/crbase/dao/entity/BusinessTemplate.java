package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

	private String logo;

	/**
	 * 用于关联后台测试场景
	 */
	private String localSceneId;

	public enum BusinessType implements GenericEnum {

		TRAINING_SINGLE(1, "单兵训练"), VULN_VALIDATE(2, "漏洞库"), TRAINING_TEAM(3,
				"团队训练"), RED_VS_BLUE(4, "红蓝对抗"), WEAPON_ASSESS(6, "装备测试"), SYSTEM_ASSESS(7, "系统测试");

		public static final List<Integer> BUSINESS_TYPE_LIST = new ArrayList<>(
				Arrays.asList(TRAINING_SINGLE.code, TRAINING_TEAM.code, RED_VS_BLUE.code));

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private BusinessType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
