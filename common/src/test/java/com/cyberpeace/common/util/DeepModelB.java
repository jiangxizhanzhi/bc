package com.cyberpeace.common.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DeepModelB implements Serializable {

	private String name;

	private Integer num;

	private Date time;

	private String b;

	private List<ModelB> list;

	private Map<String, ModelB> map;
}
