package com.cyberpeace.oj.ojbase.service.bo;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class IndicatorBo {

	private Integer id;

	private String name;

	private List<IndicatorBo> children = Lists.newArrayList();

}
