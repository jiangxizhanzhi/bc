package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;
import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountRingGraph;

import java.util.List;

public interface EvalResultService {

    List<EvalEventCountBar> getEventCount(String redVsBlueId, Integer roleType);

    List<EvalEventCountRingGraph> getEventPercent(String redVsBlueId, Integer roleType);
}
