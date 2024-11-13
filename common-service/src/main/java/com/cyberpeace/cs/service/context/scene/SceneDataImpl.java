package com.cyberpeace.cs.service.context.scene;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.*;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.context.scene.ElementFlow;

import lombok.Data;

/**
 * 场景数据实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SceneDataImpl implements SceneData {

	/**
	 * 拓扑学
	 */
	private SceneTopology topology;

	/**
	 * 插件
	 */
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

/**
 * cp集合实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class CpcollectionImpl implements Cpcollection {

	/**
	 * 场景
	 */
	private SceneCollection scene;

}

/**
 * 元素流量列表实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class ElementFlowListImpl implements ElementFlowList {

	/**
	 * 流
	 */
	private List<ElementFlow> flows;
}

/**
 * 端口实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class PortImpl implements Port {

	/**
	 * ip地址
	 */
	private String ip;
}

/**
 * 场景收集实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class SceneCollectionImpl implements SceneCollection {

	/**
	 * 使流量收集
	 */
	private Boolean enable_flow_collect = false;

	/**
	 * 启用 Probe Collect
	 */
	private Boolean enable_probe_collect;
}

/**
 * 场景元素实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class SceneElementImpl implements SceneElement {

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 港口
	 */
	private List<Port> ports;
}

/**
 * 场景插件实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class ScenePluginsImpl implements ScenePlugins {

	/**
	 * CPflow
	 */
	private Cpflow cpflow;

	/**
	 * cp集合
	 */
	private Cpcollection cpcollection;
}

/**
 * 场景状态实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class SceneStatusImpl implements SceneStatus {

	/**
	 * 事件
	 */
	private Integer event;

	/**
	 * 事件状态
	 */
	private Integer event_status;
}

/**
 * 场景拓扑学实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class SceneTopologyImpl implements SceneTopology {

	/**
	 * 场景
	 */
	private SceneStatus scene;

	/**
	 * 元素
	 */
	private List<SceneElement> elements;

}

/**
 * CPflow实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
class CpflowImpl implements Cpflow {

	/**
	 * 元素
	 */
	private Map<String, ElementFlowList> elements;

}