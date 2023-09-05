package com.cyberpeace.oj.ojbase.service.bo.check;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import lombok.Data;

@Data
public class CheckPointDo implements Serializable {

	private String name;

	private String time;

	private List<String> theory = Lists.newArrayList();
}
