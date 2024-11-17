package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MpDeviceInfo implements Serializable {

	private String name;

	private String resourceId;

	private List<String> vulns;
}
