package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import lombok.Data;

@Data
public class XYData {

	private List<String> xData;

	private List<Integer> yData;

	public XYData(List<String> xData, List<Integer> yData) {
		this.xData = xData;
		this.yData = yData;

	}
}
