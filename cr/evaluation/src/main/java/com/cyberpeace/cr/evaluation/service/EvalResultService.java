package com.cyberpeace.cr.evaluation.service;

import com.cyberpeace.cr.crbase.controller.rsp.EvalEventCountBar;

import java.util.List;

public interface EvalResultService {

    List<EvalEventCountBar> getEventCount(String redVsBlueId, Integer roleType);

    List<EvalEventCountBar> getEventPercent(String redVsBlueId, Integer roleType);
}
