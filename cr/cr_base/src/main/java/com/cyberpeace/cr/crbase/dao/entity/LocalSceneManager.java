package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;
import lombok.Getter;

@Data
@TableName("local_scene_manager")
public class LocalSceneManager extends BaseTable {

	@TableId(type = IdType.UUID, value = "resource_id")
	private String resourceId;

	private Integer appId;

	private String topoId;

	private String sceneId;

	private Integer nodeCount;

	public enum AppId implements GenericEnum {

		TRAINING_SINGLE(211, "单兵训练"), VULN_VALIDATE(212, "漏洞库"), TRAINING_TEAM(213,
				"团队训练"), RED_VS_BLUE(214, "红蓝对抗"), WEAPON_ASSESS(216, "装备测试"), SYSTEM_ASSESS(217,
						"系统测试"), SOFTWARE_TESTING(221, "软件测试"), SCENE_TEST(222, "后台测试");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private AppId(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}
	}

}
