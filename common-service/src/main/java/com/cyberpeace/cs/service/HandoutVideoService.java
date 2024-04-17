package com.cyberpeace.cs.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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

	/**
	 * 获取progress批量
	 *
	 * @param paths
	 * @param userId
	 * @return {@code List<VideoProgress>}
	 */
	Map<String, Integer> getProgressBatch(Collection<String> paths, String userId);
}
