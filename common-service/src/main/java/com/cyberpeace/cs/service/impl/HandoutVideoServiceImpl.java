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

@Slf4j
@Service
public class HandoutVideoServiceImpl implements HandoutVideoService {

	@Autowired
	private VideoProgressMapper videoProgressMapper;

	private String CAPTURE_FOLDER;

	@PostConstruct
	public void init() {
		CAPTURE_FOLDER = ConfigUtil.getContextProperty("capture.folder");
		if (!new File(CAPTURE_FOLDER).exists()) {
			FileUtil.mkdirs(CAPTURE_FOLDER);
		}
	}

	@Override
	public VideoProgress getProgress(String videoPath, String userId) {
		VideoProgress progress = videoProgressMapper.selectByPathAndUserId(videoPath, userId);
		if (null == progress) {
			progress = new VideoProgress();
			progress.setTime(0);
		}
		return progress;
	}

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
