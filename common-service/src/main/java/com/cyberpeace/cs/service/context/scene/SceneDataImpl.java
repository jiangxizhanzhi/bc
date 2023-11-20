package com.cyberpeace.cs.service.context.scene;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.*;

import lombok.Data;

@Data
public class SceneDataImpl implements SceneData {

	private SceneTopologyImpl topology;

	private ScenePluginsImpl plugins;

	/**
	 * 是否已删除状态
	 * @return
	 */
	@Override
	public Boolean isStopped() {
		return this.getTopology().getScene().getEvent().equals(MP_SCENE_DELETE_EVENT_CODE) && this
				.getTopology().getScene().getEvent_status().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

	@Override
	public Boolean isPause() {
		return this.getTopology().getScene().getEvent().equals(MP_SCENE_PAUSE_EVENT_CODE);
	}

	@Override
	public Boolean isUnPause() {
		return this.getTopology().getScene().getEvent().equals(MP_SCENE_UNPAUSE_EVENT_CODE) && this
				.getTopology().getScene().getEvent_status().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

	/**
	 * 执行中
	 * @return
	 */
	@Override
	public Boolean isFinish() {
		return this.getTopology().getScene().getEvent_status().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

}

