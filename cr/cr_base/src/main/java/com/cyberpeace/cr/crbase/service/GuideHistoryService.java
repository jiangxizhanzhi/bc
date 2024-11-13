package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.controller.req.guide.ExecGuideHisReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryReq;
import com.cyberpeace.cr.crbase.controller.req.guide.ScreenGuideHistoryTimeLineReq;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.guide.GuideHistoryRsp;

import java.util.List;

/**
 * 导调历史Service
 *
 * @author SN
 * @date 2024/11/13
 */
public interface GuideHistoryService {

	/**
	 * 获取全部选项
	 *
	 * @param roomId 房间id
	 * @return {@code PlaybackOptionRsp }
	 */
	PlaybackOptionRsp getAllOption(String roomId);

	/**
	 * 获取记录
	 *
	 * @param screenGuideHistoryReq 屏幕导调历史请求体
	 * @return {@code List<GuideHistoryRsp> }
	 */
	List<GuideHistoryRsp> getRecord(ScreenGuideHistoryReq screenGuideHistoryReq);

	/**
	 * 获取事件时间列表
	 *
	 * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
	 * @return {@code List<String> }
	 */
	List<String> getEventTimeList(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq);

	/**
	 * 执行
	 *
	 * @param execGuideHisReq 执行导调历史请求体
	 * @return {@code Boolean }
	 */
	Boolean execute(ExecGuideHisReq execGuideHisReq);

	/**
	 * 获取开始时间
	 *
	 * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
	 * @return {@code String }
	 */
	String getStartTime(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq);

	/**
	 * 获取结束时间
	 *
	 * @param screenGuideHistoryTimeLineReq 屏幕导调历史时间线请求体
	 * @return {@code String }
	 */
	String getEndTime(ScreenGuideHistoryTimeLineReq screenGuideHistoryTimeLineReq);

}
