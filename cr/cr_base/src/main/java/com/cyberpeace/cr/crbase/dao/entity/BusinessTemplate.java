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
 * @date 2024/11/07
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
	 * 难度
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

	/**
	 * 业务类型
	 *
	 * @author SN
	 * @date 2024/11/07
	 */
	public enum BusinessType implements GenericEnum {

		/**
		 * 训练单
		 */
		TRAINING_SINGLE(1, "单兵训练"),
		/**
		 * 漏洞验证
		 */
		VULN_VALIDATE(2, "漏洞库"),
		/**
		 * 训练团队
		 */
		TRAINING_TEAM(3,
				"团队训练"),
		/**
		 * 红色 vs 蓝色
		 */
		RED_VS_BLUE(4, "红蓝对抗"),
		/**
		 * 武器评估
		 */
		WEAPON_ASSESS(6, "装备测试"),
		/**
		 * 系统评估
		 */
		SYSTEM_ASSESS(7, "系统测试");

		/**
		 * 代号
		 */
		@Getter
		private Integer code;

		/**
		 * 描述
		 */
		@Getter
		private String desc;

		private BusinessType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}
}
