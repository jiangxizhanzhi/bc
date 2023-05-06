package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景状态
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneStatus implements Serializable {

	private Integer event;

	private Integer eventStatus;
}
