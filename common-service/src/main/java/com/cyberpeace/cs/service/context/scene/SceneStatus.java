package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

/**
 * 场景状态
 *
 * @author SN
 * @date 2024/11/11
 */
public interface SceneStatus extends Serializable {

	Integer getEvent();

	Integer getEvent_status();
}

