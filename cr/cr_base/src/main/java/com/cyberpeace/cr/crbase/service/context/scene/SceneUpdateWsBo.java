package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.constant.SceneWsCode;

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
	private Integer code = SceneWsCode.SCENE_UPDATED;

	private WsData data = new WsData();

	public SceneUpdateWsBo(Integer type) {
		this.data.setType(type);
	}

	public static class SceneUpdateType {

		private SceneUpdateType() {}

		/**
		 * 资产更新
		 */
		public static final Integer ASSET_UPDATED = 1;

		/**
		 * 场景停止
		 */
		public static final Integer SCENE_STOPPED = 2;

		/**
		 * 节点动态删除
		 */
		public static final Integer NODE_DELETED = 3;
	}
}

@Data
class WsData implements Serializable {

	/**
	 * 类型
	 */
	private Integer type;
}
