package com.cyberpeace.cs.service.context.scene;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.*;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.context.scene.ElementFlow;

import lombok.Data;

@Data
public class SceneDataImpl implements SceneData {

	private SceneTopology topology;

	private ScenePlugins plugins;

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

@Data
class CpcollectionImpl implements Cpcollection {

	private SceneCollection scene;

}

@Data
class ElementFlowListImpl implements ElementFlowList {

	private List<ElementFlow> flows;
}

@Data
class PortImpl implements Port {

	private String ip;
}

@Data
class SceneCollectionImpl implements SceneCollection {

	private Boolean enable_flow_collect = false;

	private Boolean enable_probe_collect;
}

@Data
class SceneElementImpl implements SceneElement {

	private String id;

	private String name;

	private List<Port> ports;
}

@Data
class ScenePluginsImpl implements ScenePlugins {

	private Cpflow cpflow;

	private Cpcollection cpcollection;
}

@Data
class SceneStatusImpl implements SceneStatus {

	private Integer event;

	private Integer event_status;
}

@Data
class SceneTopologyImpl implements SceneTopology {

	private SceneStatus scene;

	private List<SceneElement> elements;

}

@Data
class CpflowImpl implements Cpflow {

	private Map<String, ElementFlowList> elements;

}