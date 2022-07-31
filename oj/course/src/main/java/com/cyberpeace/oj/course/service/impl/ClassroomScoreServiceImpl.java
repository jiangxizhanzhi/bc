package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.ClassroomScore;
import com.cyberpeace.cs.dao.mapper.ClassroomScoreMapper;
import com.cyberpeace.cs.dao.mapper.GxTaskMapper;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.service.ClassroomScoreService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassroomScoreServiceImpl extends ServiceImpl<ClassroomScoreMapper, ClassroomScore> implements ClassroomScoreService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomScoreMapper classroomScoreMapper;

	@Autowired
	private GxTaskMapper gxTaskMapper;

	@Override
	public Boolean initTaskClassroom(String taskId, String classroomId) {

		Classroom classroom = classroomMapper.selectById(classroomId);

		// 未找到课程表信息时报错
		if (classroom == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		String lessonId = classroom.getLessonId();

		// 获取当前课程表已有的分数数据
		List<ClassroomScore> originalScores = classroomScoreMapper
				.listByClassroomIdAndUserId(classroom.getResourceId(), null);
		Map<String, ClassroomScore> originalMap = Maps.newHashMap();
		originalScores.forEach(
				originalScore -> originalMap.put(originalScore.getUserId(), originalScore));
		List<String> classroomUserIds = JsonUtil.toJsonObjectArray(classroom.getUserIds(),
				String.class);
		List<ClassroomScore> classroomScores = Lists.newArrayList();

		classroomUserIds.forEach(classroomUserId -> {
			ClassroomScore classroomScore = new ClassroomScore(taskId, classroomId, lessonId,
					classroomUserId);
			ClassroomScore currentScore = originalMap.get(classroomUserId);

			// 保留原有记录
			if (currentScore != null) {
				classroomScore.setCount(currentScore.getCount());
				classroomScore.setScore(currentScore.getScore());
			}

			classroomScores.add(classroomScore);
		});

		// 清空原有记录
		classroomScoreMapper.clearByTaskId(taskId);

		saveBatch(classroomScores);

		return true;

	}

	@Override
	public void setLessonStatus(LessonInfoBo lessonInfoBo) {

		String userId = UserInfoContext.getUserContext().getUserId();
		List<ClassroomScore> classroomScores = classroomScoreMapper
				.selectByLessonIdAndUserId(lessonInfoBo.getResourceId(), userId);

		// 未找到记录时返回原值
		if (classroomScores.isEmpty()) {
			return;
		}

		// 获取当前仍活动的任务列表
		List<String> unFinishedTasks = gxTaskMapper.listActiveTaskIds(new Date());

		// 设置初始值
		Boolean isFinished = true;
		Boolean isTaskLesson = false;

		for (ClassroomScore classroomScore : classroomScores) {
			if (unFinishedTasks.contains(classroomScore.getTaskId())) {
				isTaskLesson = true;
				if (classroomScore.getScore().equals(0)) {
					isFinished = false;
					break;
				}
			}
		}

		lessonInfoBo.setIsLessonFinished(isFinished);
		lessonInfoBo.setIsTaskLesson(isTaskLesson);
	}

}
