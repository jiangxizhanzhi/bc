package com.cyberpeace.bs.service.bo.demo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import lombok.Data;

@Data
public class GetDeviceReq implements Serializable {

	private List<String> brands = Lists.newArrayList();

	private List<Integer> deviceSystems = Lists.newArrayList();

	private Integer deviceType = 3;

	private Boolean onlyIdleDevice = false;

	private String ownership = "2";

	private Integer page = 1;

	private Integer projectId = 0;

	private List<String> resolution = Lists.newArrayList();

	private String searchWords = "";

	private Boolean showOfflineDevice = false;

	private Integer size = 20;

	private String status = "";

	private String systemVersion = "";

	private Integer total = 0;

	public GetDeviceReq() {
		this.deviceSystems.add(1);
		this.deviceSystems.add(4);
		this.deviceSystems.add(5);

	}
}
