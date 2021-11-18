package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.DynamicData;
import com.cyberpeace.cr.teamtrain.service.context.StatisticalInfo;

import lombok.Data;

@Data
public class MissionDynamicRsp {

	private List<StatisticalInfo> statisticalInfo;

	private List<DynamicData> dynamicData;
}
