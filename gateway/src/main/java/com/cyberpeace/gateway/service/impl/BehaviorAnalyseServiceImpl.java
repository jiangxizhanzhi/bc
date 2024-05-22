package com.cyberpeace.gateway.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorResultEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.BehaviorRecordEntity;
import com.cyberpeace.cs.dao.mapper.BehaviorRecordMapper;
import com.cyberpeace.cs.service.context.behavior.BehaviorAnalyseResult;
import com.cyberpeace.cs.service.context.behavior.PracticeResult;
import com.cyberpeace.gateway.service.BehaviorAnalyseService;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.google.common.collect.Maps;

public class BehaviorAnalyseServiceImpl implements BehaviorAnalyseService {

	@Autowired
	private BehaviorRecordMapper behaviorRecordMapper;

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	private Map<String, String> nameCache = Maps.newConcurrentMap();

	@Override
	public BehaviorAnalyseResult getAnalyseResult() {
		BehaviorAnalyseResult res = new BehaviorAnalyseResult();
		// most popular course
		List<BehaviorRecordEntity> courseRecord = behaviorRecordMapper.selectByModuleAndAction(
				BehaviorModuleEnum.COURSE.getCode(), BehaviorTypeEnum.ACCESS.getCode());
		String mostPopularCourseId = getMostId(courseRecord);
		Course course = courseService.getById(mostPopularCourseId);
		res.setMostPopularCourse(course.getName());
		// most popular lesson
		List<BehaviorRecordEntity> lessonRecord = behaviorRecordMapper.selectByModuleAndAction(
				BehaviorModuleEnum.COURSE.getCode(), BehaviorTypeEnum.ACCESS_LESSON.getCode());
		String mostPopularLessonId = getMostId(lessonRecord);
		CourseLesson lesson = courseLessonService.getById(mostPopularLessonId);
		res.setMostPopularLesson(lesson.getName());
		// theory
		PracticeResult theoryResult = new PracticeResult();
		List<BehaviorRecordEntity> theoryFavorite = behaviorRecordMapper.selectByModuleAndAction(
				BehaviorModuleEnum.THEORY.getCode(), BehaviorTypeEnum.ADD_TO_FAVORITE.getCode());
		String mostFavoriteTheoryId = getMostId(theoryFavorite);
		List<BehaviorRecordEntity> theoryAnswer = behaviorRecordMapper.selectByModuleAndAction(
				BehaviorModuleEnum.THEORY.getCode(), BehaviorTypeEnum.ANSWER.getCode());
		String mostAnsweredTheoryId = getMostId(theoryAnswer);
		String mostCorrectTheoryId = getMostId(StreamApiUtils.filter(theoryAnswer,
				r -> BehaviorResultEnum.CORRECT.getCode().equals(r.getResult())));
		String mostWrongTheoryId = getMostId(StreamApiUtils.filter(theoryAnswer,
				r -> BehaviorResultEnum.WRONG.getCode().equals(r.getResult())));
		theoryResult.setMostFavorite(getTheoryName(mostFavoriteTheoryId));
		theoryResult.setMostAnswered(getTheoryName(mostAnsweredTheoryId));
		theoryResult.setMostCorrect(getTheoryName(mostCorrectTheoryId));
		theoryResult.setMostWrong(getTheoryName(mostWrongTheoryId));

		res.setTheory(theoryResult);

		// practice
		PracticeResult operationResult = new PracticeResult();
		List<BehaviorRecordEntity> operationFavorite = behaviorRecordMapper.selectByModuleAndAction(
				BehaviorModuleEnum.OPERATION.getCode(), BehaviorTypeEnum.ADD_TO_FAVORITE.getCode());
		String mostFavoriteOperationId = getMostId(operationFavorite);
		List<BehaviorRecordEntity> operationAnswer = behaviorRecordMapper.selectByModuleAndAction(
				BehaviorModuleEnum.OPERATION.getCode(), BehaviorTypeEnum.ANSWER.getCode());
		String mostAnsweredOperationId = getMostId(operationAnswer);
		String mostCorrectOperationId = getMostId(StreamApiUtils.filter(operationAnswer,
				r -> BehaviorResultEnum.CORRECT.getCode().equals(r.getResult())));
		String mostWrongOperationId = getMostId(StreamApiUtils.filter(operationAnswer,
				r -> BehaviorResultEnum.WRONG.getCode().equals(r.getResult())));
		operationResult.setMostFavorite(getOperationName(mostFavoriteOperationId));
		operationResult.setMostAnswered(getOperationName(mostAnsweredOperationId));
		operationResult.setMostCorrect(getOperationName(mostCorrectOperationId));
		operationResult.setMostWrong(getOperationName(mostWrongOperationId));

		res.setPractice(operationResult);
		return res;
	}

	private String getTheoryName(String id) {
		if (null == id || "".equals(id)) {
			return "None";
		}
		if (nameCache.containsKey(id)) {
			return nameCache.get(id);
		}
		PracticeTheory theory = practiceTheoryService.getById(id);
		if (null == theory) {
			nameCache.put(id, "DELETED");
			return "DELETED";
		}
		nameCache.put(id, theory.getTitle());
		return theory.getTitle();
	}

	private String getOperationName(String id) {
		if (null == id || "".equals(id)) {
			return "None";
		}
		if (nameCache.containsKey(id)) {
			return nameCache.get(id);
		}
		PracticeOperation operation = practiceOperationService.getById(id);
		if (null == operation) {
			nameCache.put(id, "DELETED");
			return "DELETED";
		}
		nameCache.put(id, operation.getTitle());
		return operation.getTitle();
	}

	private String getMostId(List<BehaviorRecordEntity> recordList) {
		Map<String, List<BehaviorRecordEntity>> map = StreamApiUtils.groupBy(recordList,
				BehaviorRecordEntity::getTarget);
		int tmp = 0;
		String id = "";
		for (String target : map.keySet()) {
			int size = map.get(target).size();
			if (size > tmp) {
				tmp = size;
				id = target;
			}
		}

		return id;
	}
}
