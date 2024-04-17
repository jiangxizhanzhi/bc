package com.cyberpeace.cs.service;

import java.util.List;

import com.cyberpeace.cs.dao.entity.VideoProgress;
import com.cyberpeace.cs.service.context.video.VideoCheckPointBo;
import com.cyberpeace.cs.service.context.video.VideoProgressReportReq;

public interface HandoutVideoService {

	/**
	 * 获取视频检查点
	 * @param videoPath
	 * @return {@code List<VideoCheckPointBo>}
	 */
	List<VideoCheckPointBo> readCheckPoints(String videoPath);

	/**
	 * 获取某用户的观看进度
	 *
	 * @param videoPath
	 * @param userId
	 * @return {@code String}
	 */
	VideoProgress getProgress(String videoPath, String userId);

	/**
	 * 更新进度
	 * @param req 请求体
	 */
	void updateProgress(VideoProgressReportReq req);
}
