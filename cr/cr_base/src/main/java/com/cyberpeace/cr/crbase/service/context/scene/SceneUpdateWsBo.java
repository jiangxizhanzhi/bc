package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.constant.SceneWsCode;

import lombok.Data;

/**
 * 场景更新ws
 * <p>
 * <p>
 * 需要发给前端提示刷新场景的
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/06/01
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneUpdateWsBo implements Serializable {

	/**
	 * 代码
	 * <p>
	 * <p>
	 * 更新场景
	 *
	 * @see Integer
	 */
	private Integer code = SceneWsCode.SCENE_UPDATED;

	/**
	 * 数据
	 *
	 * @see WsData
	 */
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

/**
 * WS 数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see Serializable
 * @since 1.0.0
 */
@Data
class WsData implements Serializable {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	private Integer type;
}
