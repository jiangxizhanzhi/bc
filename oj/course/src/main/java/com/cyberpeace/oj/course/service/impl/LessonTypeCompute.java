package com.cyberpeace.oj.course.service.impl;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.baseinterface.Compute;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.integratation.model.mp.MpTopoStatistic;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import lombok.Setter;

public class LessonTypeCompute implements Compute {

	@Setter
	private RemoteTopoService remoteTopoService;

	public LessonTypeCompute(RemoteTopoService remoteTopoService) {
		this.remoteTopoService = remoteTopoService;
	}

	public LessonTypeCompute() {}

	@Override
	public GenericEnum compute(Object obj) {
		if (!(obj instanceof CourseLesson)) {
			throw new BusinessException(OjBusinessErrorCode.PARAM_ERROR);
		}

		CourseLesson lesson = (CourseLesson) obj;
		if (StringUtils.isNotBlank(lesson.getTopoId())) {
			if (null == remoteTopoService) {
				throw new BusinessException(OjBusinessErrorCode.PARAM_MISSION);
			}

			// 计算单机和网络
			MpTopoStatistic statistic = remoteTopoService.getTopoStatistic(lesson.getTopoId());
			Integer nodeCount = statistic.getTerminalCount();
			return nodeCount > CommonConstant.ONE ? CourseLesson.Type.MULTIPLE
					: CourseLesson.Type.SINGLE;
		}

		return StringUtils.isNotBlank(lesson.getVideoUrl()) ? CourseLesson.Type.VIDEO
				: CourseLesson.Type.DOCUMENT;
	}

}
