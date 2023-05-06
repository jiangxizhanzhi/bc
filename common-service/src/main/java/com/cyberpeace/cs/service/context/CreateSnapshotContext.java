package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 创建快照上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class CreateSnapshotContext implements Serializable {

	private String nodeId;

	private String snapshotName;

	private String description;

}
