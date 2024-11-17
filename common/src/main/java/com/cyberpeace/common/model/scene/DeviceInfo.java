package com.cyberpeace.common.model.scene;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DeviceInfo implements Serializable {

	private String resourceId;

	private String name;

	private List<String> vulns;
}
