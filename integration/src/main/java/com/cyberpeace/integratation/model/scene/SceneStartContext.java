package com.cyberpeace.integratation.model.scene;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Data;
import lombok.Getter;

@Data
public class SceneStartContext extends SceneOpsContext {

	private String referenceMetaData;

	/**
	 * 场景属性
	 */
	private Integer sceneAttr;

	/**
	 * 场景存活时间
	 */
	private Integer sceneTtl;

	/**
	 * 场景大json。
	 *
	 * 该字段传入的情况下，url中拓扑id不起作用。
	 */
	private Object cscene;

	public enum SceneAttrEnum implements GenericEnum {

		NULL(0, "无隔离"), SINGLE(1, "个人隔离"), TEAM(2, "团队隔离");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		private SceneAttrEnum(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}

}
