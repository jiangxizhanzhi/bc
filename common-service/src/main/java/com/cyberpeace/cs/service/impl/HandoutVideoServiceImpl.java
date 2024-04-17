package com.cyberpeace.cs.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.Mp4Util;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.VideoCheckPoint;
import com.cyberpeace.cs.dao.entity.VideoProgress;
import com.cyberpeace.cs.dao.mapper.VideoCheckPointMapper;
import com.cyberpeace.cs.dao.mapper.VideoProgressMapper;
import com.cyberpeace.cs.service.HandoutVideoService;
import com.cyberpeace.cs.service.context.video.VideoCheckPointBo;
import com.cyberpeace.cs.service.context.video.VideoProgressReportReq;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HandoutVideoServiceImpl implements HandoutVideoService {

	@Autowired
	private VideoProgressMapper videoProgressMapper;

	@Autowired
	private VideoCheckPointMapper videoCheckPointMapper;

	@Override
	public List<VideoCheckPointBo> readCheckPoints(String videoPath) {
		// whether recorded
		VideoCheckPoint checkPoint = videoCheckPointMapper.selectByPath(videoPath);
		if (null == checkPoint) {
			checkPoint = saveCheckPoints(videoPath);
		}
		return JsonUtil.toJsonObjectArray(checkPoint.getCheckPoint(), VideoCheckPointBo.class);
	}

	@Override
	public VideoProgress getProgress(String videoPath, String userId) {
		VideoProgress progress = videoProgressMapper.selectByPathAndUserId(videoPath, userId);
		if (null == progress) {
			progress = new VideoProgress();
			progress.setProgress(0);
			progress.setTime(0);
		}
		return progress;
	}

	@Override
	public void updateProgress(VideoProgressReportReq req) {
		String userId = UserInfoContext.getUserContext().getUserId();
		VideoProgress progress = videoProgressMapper.selectByPathAndUserId(req.getPath(), userId);
		boolean insertFlag = null == progress;
		boolean updateFlag = false;
		if (insertFlag) {
			progress = new VideoProgress();
			progress.setPath(req.getPath());
			progress.setUserId(userId);
			progress.setBusinessId(req.getBusinessId());
		} else if (req.getProgress() > progress.getProgress()) {
			// 只有新进度大于旧进度才更新
			progress.setProgress(req.getProgress());
			progress.setTime(req.getTime());
			updateFlag = true;
		}
		if (insertFlag) {
			videoProgressMapper.insert(progress);
		} else if (updateFlag) {
			videoProgressMapper.updateById(progress);
		}
	}

	@Override
	public Map<String, Integer> getProgressBatch(Collection<String> paths, String userId) {
		List<VideoProgress> progressList = videoProgressMapper.selectByUserIdAndPaths(userId,
				paths);
		return StreamApiUtils.toMap(progressList, VideoProgress::getPath,
				VideoProgress::getProgress);
	}

	private VideoCheckPoint saveCheckPoints(String videoPath) {
		VideoCheckPoint videoCheckPoint = new VideoCheckPoint();
		videoCheckPoint.setPath(videoPath);

		List<VideoCheckPointBo> pointBos = Lists.newArrayList();
		// time unit: ms -> s
		int duration = (int) (Mp4Util.readDuration(videoPath) / 1000);
		videoCheckPoint.setDuration(duration);

		// 分成20份, 每份5%
		int n = 20;
		int p = 5;
		if (duration != 0) {
			int tmp = 0;
			int time;
			for (int i = 0; i < n; i++) {
				time = duration * (i + 1) / n;
				if (time != tmp) {
					VideoCheckPointBo bo = new VideoCheckPointBo();
					bo.setTime(time);
					bo.setProgress(p * i);
					pointBos.add(bo);
					tmp = time;
				}
			}
			// 最后一个检查点进度为100
			pointBos.get(pointBos.size() - 1).setProgress(100);
		}
		videoCheckPoint.setCheckPoint(JsonUtil.arrayToString(pointBos));
		videoCheckPointMapper.insert(videoCheckPoint);
		return videoCheckPoint;
	}
}
