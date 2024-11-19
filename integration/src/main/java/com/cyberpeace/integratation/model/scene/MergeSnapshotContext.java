package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 合并快照上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MergeSnapshotContext implements Serializable {

	/**
	 * 图像类型
	 *
	 * @see String
	 */
	private String imageType;

	/**
	 * 新增功能名称
	 *
	 * @see String
	 */
	private String newName;

	/**
	 * 快照名称
	 *
	 * @see String
	 */
	private String snapshotName;

	/**
	 * uuid
	 *
	 * @see String
	 */
	private String uuid;

}
