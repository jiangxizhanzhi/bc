package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.constant.MissionWsCode;

import lombok.Data;

/**
 * 场景更新ws
 * 需要发给前端提示刷新场景的
 *
 * @author SN
 * @date 2023/06/01
 */
@Data
public class SceneUpdateWsBo implements Serializable {

	/**
	 * 代码
	 * 更新场景
	 */
	private Integer code = MissionWsCode.SCENE_UPDATED;

	/**
	 * 类型
	 */
	private Integer type;

	public SceneUpdateWsBo(Integer type) {
		this.type = type;
	}

	public static class SceneUpdateType {

		private SceneUpdateType() {}

		/**
		 * 资产更新
		 */
		public static final Integer ASSET_UPDATED = 1;
	}
}
