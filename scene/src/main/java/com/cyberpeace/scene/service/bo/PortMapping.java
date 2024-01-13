package com.cyberpeace.scene.service.bo;

import lombok.Data;

@Data
public class PortMapping {

	private String network_id;

	private Integer instance_port;

	private String instance_ip;

	private String proto;

	private Integer host_port;

	private String id;

	private String host;
}
