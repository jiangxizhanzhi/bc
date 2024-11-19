package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 操作场景实例的上下文。
 * 部分场景相关接口需要传入该数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneOpsContext implements Serializable {

	/**
	 * 上下文
	 *
	 * @see SceneMpContext
	 */
	private SceneMpContext context = new SceneMpContext();

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

}
