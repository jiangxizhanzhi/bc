package com.cyberpeace.common.model.evaluation;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class ProbeOriginPageData implements Serializable {

	private int total;

	private List<ProbeOriginData> data = Lists.newArrayList();
}
