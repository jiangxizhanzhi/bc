package com.cyberpeace.integratation.model.scene;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Getter;

/**
 * 场景启动上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see SceneOpsContext
 * @since 1.0.0
 */
public class SceneStartContext extends SceneOpsContext {

	/**
	 * 引用元数据
	 *
	 * @see String
	 */
	private String reference_meta_data;

	/**
	 * 场景属性
	 *
	 * @see Integer
	 */
	private Integer scene_attr;

	/**
	 * 场景存活时间
	 *
	 * @see Integer
	 */
	private Integer scene_ttl;

	public void setReferenceMetaData(String reference_meta_data) {
		this.reference_meta_data = reference_meta_data;
	}

	public void setSceneAttr(Integer scene_attr) {
		this.scene_attr = scene_attr;
	}

	public void setSceneTtl(Integer scene_ttl) {
		this.scene_ttl = scene_ttl;
	}

	public void setCscene(Object cscene) {
		this.cscene = cscene;
	}

	public String getReference_meta_data() {
		return reference_meta_data;
	}

	public Integer getScene_attr() {
		return scene_attr;
	}

	public Integer getScene_ttl() {
		return scene_ttl;
	}

	public Object getCscene() {
		return cscene;
	}

	/**
	 * 场景大json。
	 * 该字段传入的情况下，url中拓扑id不起作用。
	 *
	 * @see Object
	 */
	private Object cscene;

	public void setReferenceMetaData(ReferenceMetaData referenceMetaData) {
		this.reference_meta_data = JsonUtil.toJsonString(referenceMetaData);
	}

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
