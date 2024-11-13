package com.cyberpeace.cs.service.impl;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.dao.entity.VideoProgress;
import com.cyberpeace.cs.dao.mapper.VideoProgressMapper;
import com.cyberpeace.cs.service.HandoutVideoService;
import com.cyberpeace.cs.service.context.video.VideoProgressReportReq;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 讲义视频Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j
@Service
public class HandoutVideoServiceImpl implements HandoutVideoService {

	/**
	 * 视频进展Mapper
	 */
	@Autowired
	private VideoProgressMapper videoProgressMapper;

	/**
	 * 捕获文件夹
	 */
	private String CAPTURE_FOLDER;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		CAPTURE_FOLDER = ConfigUtil.getContextProperty("capture.folder");
		if (!new File(CAPTURE_FOLDER).exists()) {
			FileUtil.mkdirs(CAPTURE_FOLDER);
		}
	}

	/**
	 * 获取进展
	 *
	 * @param videoPath 视频路径
	 * @param userId    用户id
	 * @return {@code VideoProgress }
	 */
	@Override
	public VideoProgress getProgress(String videoPath, String userId) {
		VideoProgress progress = videoProgressMapper.selectByPathAndUserId(videoPath, userId);
		if (null == progress) {
			progress = new VideoProgress();
			progress.setTime(0);
		}
		return progress;
	}

	/**
	 * 更新进度
	 *
	 * @param req 请求体
	 */
	@Override
	public void updateProgress(VideoProgressReportReq req) {
		String userId = UserInfoContext.getUserContext().getUserId();
		VideoProgress progress = videoProgressMapper.selectByPathAndUserId(req.getPath(), userId);
		boolean insertFlag = null == progress;
		if (insertFlag) {
			progress = new VideoProgress();
			progress.setPath(req.getPath());
			progress.setUserId(userId);
			progress.setBusinessId(req.getBusinessId());
		}
		List<Integer> watchedPoints = JsonUtil.toJsonObjectArray(progress.getWatchedPoint(),
				Integer.class);
		if (!watchedPoints.contains(req.getTime())) {
			watchedPoints.add(req.getTime());
			progress.setWatchedPoint(JsonUtil.arrayToString(watchedPoints));
		}
		progress.setTime(req.getTime());
		if (insertFlag) {
			videoProgressMapper.insert(progress);
		} else {
			videoProgressMapper.updateById(progress);
		}
	}

	/**
	 * 获取进展批量
	 *
	 * @param paths  路径
	 * @param userId 用户id
	 * @return {@code Map<String, Set<Integer>> }
	 */
	@Override
	public Map<String, Set<Integer>> getProgressBatch(Collection<String> paths, String userId) {
		if (CollectionUtils.isEmpty(paths) || StringUtils.isBlank(userId)) {
			return Maps.newHashMap();
		}
		List<VideoProgress> progressList = videoProgressMapper.selectByUserIdAndPaths(userId,
				paths);
		return StreamApiUtils.toMap(progressList, VideoProgress::getPath, p -> Sets
				.newHashSet(JsonUtil.toJsonObjectArray(p.getWatchedPoint(), Integer.class)));
	}

	/**
	 * 获取缩略图
	 *
	 * @param path   路径
	 * @param sec    秒
	 * @param resize 调整
	 * @return {@code File }
	 */
	@Override
	public File getThumbnail(String path, Integer sec, Boolean resize) {
		File video = new File(path);
		if (!video.exists() || !video.canRead()) {
			log.error("video file not exist or not readable: [{}]", path);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		}
		String imageName = path.replace("/", "_") + "_" + sec + ".jpg";
		String imagePath = CAPTURE_FOLDER + imageName;
		File image = new File(imagePath);
		if (!image.exists()) {
			VideoUtil.getFramePic(video, sec, image, resize);
		}
		return image;
	}
}
