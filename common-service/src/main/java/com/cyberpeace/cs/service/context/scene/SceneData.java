package com.cyberpeace.cs.service.context.scene;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.MP_SCENE_DELETE_EVENT_CODE;
import static com.cyberpeace.common.constant.MpSceneStatusConstant.MP_SCENE_PROGRESS_FINISH_CODE;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景数据
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneData implements Serializable {

	private SceneTopology topology;

	private ScenePlugins plugins;

	/** 
	 * 是否已删除状态
	 * @return 
	 */
	public Boolean isStopped() {
		return this.topology.getScene().getEvent().equals(MP_SCENE_DELETE_EVENT_CODE)
				&& this.topology.getScene().getEventStatus().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}

}
