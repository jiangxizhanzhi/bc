package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;

import java.util.List;

public interface GuideHistoryService {

	PlaybackOptionRsp getAllOption(String roomId);

//	void refresh(ScreenGuideHistoryReq screenGuideHistoryReq);

	List<GuideHistoryRsp> getLocalRecord(ScreenGuideHistoryReq screenGuideHistoryReq);

}
