package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class SceneMetaData implements Serializable {

	/** 业务数据编号*/
	private String businessInstanceId;

	/** 场景名称*/
	private String sceneName;

}
