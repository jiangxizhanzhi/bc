package com.cyberpeace.evaluate.controller.req;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合模型
 */
@Data
public class AggregationReq implements Serializable {

	private String id;

	private List<String> aggregation = Lists.newArrayList();
}
