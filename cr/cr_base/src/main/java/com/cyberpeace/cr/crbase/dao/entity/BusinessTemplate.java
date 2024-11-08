package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.AuthTable;

import lombok.Data;
import lombok.Getter;

/**
 * 靶场模板公共信息模型
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@TableName("cr_business_template")
public class BusinessTemplate extends AuthTable {

	/**
	 * 资源id
	 */
	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

	/**
	 * 拓扑id
	 */
	private String topoId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 业务类型
	 */
	private Integer businessType;

	/**
	 * 困难
	 */
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	private String createUserId;

	/**
	 * 图标
	 */
	private String logo;

	/**
	 * 用于关联后台测试场景
	 */
	private String localSceneId;

	public enum BusinessType implements GenericEnum {

		TRAINING_SINGLE(1, "单兵训练"), VULN_VALIDATE(2, "漏洞库"), TRAINING_TEAM(3,
				"团队训练"), RED_VS_BLUE(4, "红蓝对抗"), WEAPON_ASSESS(6, "装备测试"), SYSTEM_ASSESS(7, "系统测试");

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
