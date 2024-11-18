package com.cyberpeace.cr.teamtrain.service.context;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.TemplateMissionId;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;

import lombok.Data;
import lombok.Getter;

/**
 * 团队训练模板角色
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class TeamTrainTemplateRole {

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	private Integer roleGroup;

	/**
	 * 任务计划
	 *
	 * @see ColorMission<TemplateMissionId>
	 */
	private ColorMission<TemplateMissionId> missionsScheme;

	/**
	 * 资产节点
	 *
	 * @see List<CrRoleNode>
	 */
	private List<CrRoleNode> assetNodes;

	/**
	 * 可见节点
	 *
	 * @see List<CrRoleNode>
	 */
	private List<CrRoleNode> visibleNodes;

	public enum RoleName {

		TEACHER(1, "授权所有"), STUDENT(2, "自定义");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		RoleName(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}