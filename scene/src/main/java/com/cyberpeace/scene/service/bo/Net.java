package com.cyberpeace.scene.service.bo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * @author :   SN
 * @date :   2023/12/21 20:01
 * @desc :
 **/
@Data
public class Net implements Serializable {

    @JsonProperty("network_id")
    private String network_id;

	private String fixed_ip;

	private List<NetAccessMode> access_modes = Lists.newArrayList();

	public Net() {}

	public Net(String network_id) {
		this.network_id = network_id;
	}
}
