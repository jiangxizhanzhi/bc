package com.cyberpeace.evaluate.dao.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class NameValueItem implements Serializable {

	private String name;

	private Integer value;

	List<NameValueItem> links = Lists.newArrayList();
}
