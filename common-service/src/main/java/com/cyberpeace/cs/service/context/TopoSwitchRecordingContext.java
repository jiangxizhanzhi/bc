package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于设置中台拓扑的录屏选项
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TopoSwitchRecordingContext implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 启用录制
	 *
	 * @see Boolean
	 */
	private Boolean enable_recording;

	public TopoSwitchRecordingContext(String topoId, Boolean enableRecording) {
		this.id = topoId;
		this.enable_recording = enableRecording;
	}

}
