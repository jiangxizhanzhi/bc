package com.cyberpeace.integratation.model.scene;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ResourceStatisticsRsp {

	private List<SceneCount> sceneStatics;

	private TargetCount targetStatics;

	public Integer getVirtual() {
		int count = 0;
		for (Integer value : this.targetStatics.getVirtual().values()) {
			count += value;
		}
		return count;
	}

	public Integer getVm() {
		return this.targetStatics.getAll().getVm();
	}

	public Integer getScene() {
		int count = 0;
		for (SceneCount sceneStatic : this.sceneStatics) {
			count += sceneStatic.getTotal();
		}
		return count;
	}
}

@Data
class SceneCount {

	private Integer referenceType;

	private Integer total;
}

@Data
class TargetCount {

	private Map<String, Integer> virtual;

	private AllCount all;

}

@Data
class AllCount {

	private Integer vm;
}
