package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 合并快照上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MergeSnapshotContext implements Serializable {

	/**
	 * 图像类型
	 */
	private String imageType;

	/**
	 * 新增功能名称
	 */
	private String newName;

	/**
	 * 快照名称
	 */
	private String snapshotName;

	/**
	 * uuid
	 */
	private String uuid;

}
