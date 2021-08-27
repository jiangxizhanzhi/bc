package com.cyberpeace.common.util;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class ModelA implements Serializable {

	private String name;

	private Integer num;

	private Date time;

	private String a;
}
