package com.cyberpeace.cs.service;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.cyberpeace.cs.dao.entity.VideoProgress;
import com.cyberpeace.cs.service.context.video.VideoProgressReportReq;

public interface HandoutVideoService {

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
	Map<String, Set<Integer>> getProgressBatch(Collection<String> paths, String userId);

	/**
	 * 获取截图
	 *
	 * @param path
	 * @param sec
	 * @param resize
	 * @return {@code File}
	 */
	File getThumbnail(String path, Integer sec, Boolean resize);
}
