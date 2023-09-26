package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.controller.req.guide.ExecGuideHisReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;

import java.util.List;

public interface GuideHistoryService {

	PlaybackOptionRsp getAllOption(String roomId);

	List<GuideHistoryRsp> getRecord(ScreenGuideHistoryReq screenGuideHistoryReq);

	List<String> getEventTimeList(String roomId, Integer businessType);

	Boolean execute(ExecGuideHisReq execGuideHisReq);

}
