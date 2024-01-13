package com.cyberpeace.scene.service.bo;

import lombok.Data;

@Data
public class NetAccessMode {

	private Integer port;

	private String protocol;

	private String ip_address;

	private String username;

	private String password;

	private Boolean enable_port_mapping;

	private String port_mapping_protocol = "tcp";

	private PortMapping port_mapping;
}
