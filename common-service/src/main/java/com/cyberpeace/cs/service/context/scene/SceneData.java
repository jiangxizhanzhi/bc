package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import static com.cyberpeace.common.constant.MpSceneStatusConstant.*;

/**
 * 场景数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `SceneData` 的 Java 类，用于表示场景相关的数据结构。以下是代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SceneData` 
 * - 包名：`com.cyberpeace.cs.service.context.scene` 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * - 使用了 Lombok 注解 `@Getter` 和 `@Setter`，自动生成属性的 getter 和 setter 方法。 
 * 2. **类的属性**： 
 * - `topology`：表示场景的拓扑结构，类型为 `SceneTopology`。 
 * - `plugins`：表示场景中使用的插件，类型为 `ScenePlugins`。 
 * 3. **方法功能**： 
 * - `isStopped()`：判断场景是否处于已删除状态（通过比较事件类型和事件状态）。 
 * - `isPause()`：判断场景是否处于暂停状态（通过比较事件类型）。 
 * - `isUnPause()`：判断场景是否从暂停状态恢复完成（通过比较事件类型和事件状态）。 
 * - `isFinish()`：判断场景是否执行完成（仅通过事件状态）。 
 * 4. **用途**： 
 * - 该类主要用于管理场景的状态，提供了一系列方法来检查场景的不同状态。 
 * 总体来说，这个类的设计目的是封装场景数据并提供状态查询的功能，便于在应用程序中管理和操作场景对象。 
**/
@Getter
@Setter
public class SceneData implements Serializable {

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

