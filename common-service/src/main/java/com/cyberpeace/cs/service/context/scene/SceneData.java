package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.*;

/**
 * 场景数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneData implements Serializable {

	/**
	 * 拓扑学
	 *
	 * @see SceneTopology
	 */
	private SceneTopology topology;

	/**
	 * 插件
	 *
	 * @see ScenePlugins
	 */
	private ScenePlugins plugins;

	/** 
	 * 是否已删除状态
	 * @return 
	 */
	public Boolean isStopped() {
		return this.topology.getScene().getEvent().equals(MP_SCENE_DELETE_EVENT_CODE)
				&& this.topology.getScene().getEventStatus().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}


	public Boolean isPause() {
		return this.topology.getScene().getEvent().equals(MP_SCENE_PAUSE_EVENT_CODE);
	}

	public Boolean isUnPause() {
		return this.topology.getScene().getEvent().equals(MP_SCENE_UNPAUSE_EVENT_CODE)
				&& this.topology.getScene().getEventStatus().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

	/**
	 * 执行中
	 * @return
	 */
	public Boolean isFinish() {
		return  this.topology.getScene().getEventStatus().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

}
