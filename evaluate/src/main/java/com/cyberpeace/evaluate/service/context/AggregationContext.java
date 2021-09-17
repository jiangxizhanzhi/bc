package com.cyberpeace.evaluate.service.context;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class AggregationContext {

    private String id;

    private List<String> aggregation = Lists.newArrayList();

}
