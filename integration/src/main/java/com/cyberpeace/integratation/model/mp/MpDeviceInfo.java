package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;

@Data
public class MpDeviceInfo implements Serializable {

	private String name;

	private String resourceId;
}
