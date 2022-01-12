package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.crbase.service.context.StatisticalInfo;

import lombok.Data;

@Data
public class MissionDynamicRsp {

	private List<StatisticalInfo> statisticalInfo;

	private List<DynamicData> dynamicData;
}
