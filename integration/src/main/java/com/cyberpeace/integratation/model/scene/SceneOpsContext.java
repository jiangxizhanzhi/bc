package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 操作场景实例的上下文。
 * 部分场景相关接口需要传入该数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class SceneOpsContext implements Serializable {

	/**
	 * 上下文
	 */
	private SceneMpContext context = new SceneMpContext();

	/**
	 * id
	 */
	private String id;

}
