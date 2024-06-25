package com.cyberpeace.integratation.service.bo;

import lombok.Data;

@Data
public class AccessConnectionRsp {

	private Boolean connection_enable_recording;

	private String connection_id;

	private String connection_recording_name;

	private String connection_url;

	private String node_id;

	private String node_name;

	private String protocol;

	private String scene_id;

	private String scene_name;

	private String user;

}
