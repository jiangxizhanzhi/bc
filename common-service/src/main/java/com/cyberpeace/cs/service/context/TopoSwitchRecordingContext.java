package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于设置中台拓扑的录屏选项
 */
@Data
public class TopoSwitchRecordingContext implements Serializable {

	private String id;

	private Boolean enable_recording;

	public TopoSwitchRecordingContext(String topoId, Boolean enableRecording) {
		this.id = topoId;
		this.enable_recording = enableRecording;
	}

}
