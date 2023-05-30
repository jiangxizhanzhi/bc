package com.cyberpeace.oj.person.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.mdparser.convert.DifficultyConvert;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.ojbase.constant.Difficulty;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.dao.mapper.PersonCenterMapper;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.JcaCipherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cyberpeace.integratation.constant.LogContent.EXERCISE;
import static com.cyberpeace.integratation.constant.LogContent.THEORY;

@Service
@Slf4j
public class PersonCenterServiceImpl extends ServiceImpl<CommentMapper, Comment> implements PersonCenterService {

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private PersonCenterMapper personCenterMapper;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Override
	public IPage<PersonComment> pageComment(Page page, String userId, String parent,
			AuthContext authContext) {
		List<String> resourceIds = new ArrayList<>();
		if (null != authContext) {
			List<String> courseIds = courseMapper.selectByAuth(authContext);
			List<String> publicCourses = personCenterMapper.selectPublicCourse(courseIds);
			resourceIds.addAll(publicCourses);
			resourceIds.addAll(personCenterMapper.selectLessonByAuth(publicCourses));
			resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
		}
		IPage<PersonComment> iPage = personCenterMapper.selectCommentByUser(page, userId, parent,
				resourceIds);
		return iPage.convert(this::commentToBo);
	}

	@Override
	public IPage<PersonNote> pageNotes(Page page, String userId, AuthContext authContext, Integer businessType) {
		Integer noteType = Note.NoteType.NOTE.getCode();
		List<String> resourceIds = new ArrayList<>();
		if (null != authContext) {
			List<String> courseIds = courseMapper.selectByAuth(authContext);
			List<String> publicCourses = personCenterMapper.selectPublicCourse(courseIds);
			resourceIds.addAll(publicCourses);
			List<String> lessons = personCenterMapper.selectLessonByAuth(publicCourses);
			resourceIds.addAll(lessons);
			resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
			resourceIds.addAll(personCenterMapper.selectAfterclassPracticeByAuth(lessons));
			resourceIds.addAll(personCenterMapper.selectTheoryPracticeByAuth());
		}
		IPage<PersonNote> iPage = personCenterMapper.selectNoteByUser(page, userId, noteType,
				resourceIds, businessType);
		return iPage.convert(this::noteToBo);
	}

	/**
	 * @param page
	 * @param userId 用户ID
	 * @return
	 */
	@Override
	public IPage<PersonCollection> pageCollections(Page page, String userId, String type,
			AuthContext authContext) {
		List<String> resourceIds = new ArrayList<>();
		if (null != authContext) {
			List<String> courseIds = courseMapper.selectByAuth(authContext);
			List<String> publicCourses = personCenterMapper.selectPublicCourse(courseIds);
			resourceIds.addAll(publicCourses);
			resourceIds.addAll(personCenterMapper.selectLessonByAuth(publicCourses));
			resourceIds.addAll(personCenterMapper.selectTheoryPracticeByAuth());
			resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
		}
		return personCenterMapper.selectByUser(page, userId, type, resourceIds);
	}

	@Override
	public BaseRsp<StudyRecord> studyRecords(String userId, AuthContext authContext) {
		List<String> lessonIds = new ArrayList<>();
		List<String> expLessonIds = new ArrayList<>();
		if (null != authContext) {
			lessonIds = StreamApiUtils.getColumn(courseLessonMapper.selectList(null),CourseLesson::getResourceId);
		}
		// 获取用户课程学习数据
		StudyRecord studyRecord = personCenterMapper.selectRecordByUser(userId, lessonIds);
		// 获取用户提交的实验报告数量
		Integer reportCount = personCenterMapper.selectReportCount(userId, expLessonIds);
		studyRecord.setReportCount(reportCount);
		// 获取用户完成课后练习的排名、得分、数量
		List<String> users = personCenterMapper.selectDistinctUser();
		if (users.contains(userId)) {
			StudyRecord practiceRecord = personCenterMapper.selectPracticeByUser(userId, lessonIds);
			if (null != practiceRecord) {
				int practiceCount = Integer.parseInt(practiceRecord.getRecord());
				studyRecord.setPracticeFinishCount(practiceCount);
				double score = practiceRecord.getScore();
				Integer practiceRanking = practiceRecord.getPracticeRanking();
				studyRecord.setPracticeRanking(practiceRanking);
				studyRecord.setScore(score);
			}
		}
		// 获取当前用户解出的实操题和理论题个数
		StudyRecord practiceByUser = personCenterMapper.selectSlovedPracticeByUser(userId);
		Integer operationPracticeCount = practiceByUser.getOperationPracticeCount();
		Integer theoryPracticeCount = practiceByUser.getTheoryPracticeCount();
		studyRecord.setOperationPracticeCount(null == operationPracticeCount ? 0 : operationPracticeCount);
		studyRecord.setTheoryPracticeCount(null == theoryPracticeCount ? 0 : theoryPracticeCount);
		BaseRsp<StudyRecord> rsp = new BaseRsp<>();
		rsp.setData(studyRecord);
		return rsp;
	}

	@Override
	public BaseRsp<BrokenLine> studyPeriodRecords(String userId, Date startTime, Date endTime,
			AuthContext authContext) {
		// 资源过滤
		List<String> lessonIds = new ArrayList<>();
		List<String> expLessonIds = new ArrayList<>();
		if (null != authContext) {
			lessonIds = personCenterMapper.selectLessonByAdmin(null);
			expLessonIds = personCenterMapper.selectLessonByAdmin( CourseLesson.LessonType.EXPERIMENT.getCode());
		}
		List<LessonRecord> lessonRecord = personCenterMapper.selectLessonByTime(userId, startTime,
				endTime, lessonIds);
		List<Note> finishReports = personCenterMapper.selectReportByTime(userId, startTime, endTime,
				expLessonIds);
		List<NoteEvaluate> reportScores = personCenterMapper.selectReportAverageByTime(userId, startTime, endTime,
				expLessonIds);
		int daysBetween = TimeUtil.getDaysBetween(TimeUtil.getStartOfDay(startTime),
				TimeUtil.getStartOfDay(endTime));
		Map<String, List<LessonRecord>> courseRecordMap = lessonRecord.stream().distinct()
				.collect(Collectors.groupingBy(course -> TimeUtil.format8(course.getCreateTime())));
		Map<String, List<Note>> finishReportMap = finishReports.stream().collect(Collectors
				.groupingBy(finishPractice -> TimeUtil.format8(finishPractice.getCreateTime())));
		Map<String, List<NoteEvaluate>> reportAverageMap = reportScores.stream().collect(Collectors.groupingBy(score -> TimeUtil.format8(score.getEvaluateTime())));
		List<String> dates = Lists.newArrayList();
		List<Integer> courseRecords = Lists.newArrayList();
		List<Integer> reportRecords = Lists.newArrayList();
		List<Double> reportAverageRecords = Lists.newArrayList();
		for (int i = 0; i < daysBetween; i++) {
			Date date = TimeUtil.addDates(startTime, i);
			String currentDate = TimeUtil.format8(date);
			dates.add(currentDate);
			List<LessonRecord> courseRecordLists = courseRecordMap.get(currentDate);
			if (courseRecordLists == null || courseRecordLists.size() == 0) {
				courseRecords.add(0);
			} else {
				courseRecords.add(courseRecordLists.size());
			}
			List<Note> finishReportLists = finishReportMap.get(currentDate);
			if (finishReportLists == null || finishReportLists.size() == 0) {
				reportRecords.add(0);
			} else {
				reportRecords.add(finishReportLists.size());
			}
			List<NoteEvaluate> reportAverageLists = reportAverageMap.get(currentDate);
			if (reportAverageLists == null || reportAverageLists.size() == 0) {
				reportAverageRecords.add(0.0d);
			} else {
				double asDouble = reportAverageLists.stream().mapToDouble(NoteEvaluate::getScore).average().getAsDouble();
				reportAverageRecords.add(asDouble);
			}
		}
		BaseRsp<BrokenLine> baseRsp = new BaseRsp<>();
		BrokenLine res = new BrokenLine();
		res.setXNames(dates);
		res.setYFirstValues(courseRecords);
		res.setYSecondValues(reportRecords);
		res.setYThirdValues(reportAverageRecords);
		baseRsp.setData(res);
		return baseRsp;
	}

	@Override
	public List<CollectionPracticeBo> selectCollectionPractice(String userId,
			Integer practiceType) {

		List<ResourceCollection> practiceCollections = resourceCollectionService
				.selectByType(userId, practiceType);
		List<String> practiceIds = StreamApiUtils.map(practiceCollections,
				ResourceCollection::getBusinessId);

		if (CollectionUtils.isEmpty(practiceIds)) {
			return Lists.newArrayList();
		}

		if (practiceType.intValue() == ResourceCollection.BusinessType.THEORY.getCode()
				.intValue()) {
			List<PracticeTheory> theoryList = practiceTheoryMapper.selectBatchIds(practiceIds);

			return StreamApiUtils.map(theoryList, practiceTheory -> {
				CollectionPracticeBo practiceBo = BeanUtils.copy(practiceTheory,
						CollectionPracticeBo.class);
				practiceBo.setPracticeType(PracticeCategory.PracticeType.THEORY.getCode());
				PracticeCategory practiceCategory = practiceCategoryMapper
						.selectById(practiceBo.getCategoryId());
				if (null != practiceCategory) {
					practiceBo.setCategoryName(practiceCategory.getName());
				}

				return practiceBo;
			});
		}

		if (practiceType.intValue() == ResourceCollection.BusinessType.OPERATION.getCode()
				.intValue()) {
			List<PracticeOperation> operationList = practiceOperationMapper
					.selectBatchIds(practiceIds);

			return StreamApiUtils.map(operationList, practiceOperation -> {
				CollectionPracticeBo practiceBo = BeanUtils.copy(practiceOperation,
						CollectionPracticeBo.class);
				practiceBo.setPracticeType(PracticeCategory.PracticeType.OPERATION.getCode());
				PracticeCategory practiceCategory = practiceCategoryMapper
						.selectById(practiceBo.getCategoryId());
				if (null != practiceCategory) {
					practiceBo.setCategoryName(practiceCategory.getName());
				}

				return practiceBo;
			});
		}

		return Lists.newArrayList();
	}

	@Override
	public List<String> selectCollectionResourceIds(String userId, AuthContext authContext) {
		List<String> resourceIds = new ArrayList<>();
		if (null != authContext) {
			List<String> courseIds = courseMapper.selectByAuth(authContext);
			List<String> publicCourses = personCenterMapper.selectPublicCourse(courseIds);
			resourceIds.addAll(publicCourses);
			resourceIds.addAll(personCenterMapper.selectLessonByAuth(publicCourses));
			resourceIds.addAll(personCenterMapper.selectTheoryPracticeByAuth());
			resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
		}
		return personCenterMapper.selectResourceIdByUser(userId, null, resourceIds);
	}

	/**
	 * 数据格式转换
	 *
	 * @param comment
	 * @return
	 */
	private PersonComment commentToBo(PersonComment comment) {

		// 获取用户信息
		UserInfo userInfo = remoteUserService.getById(comment.getUserId());

		List<Comment> comments = commentMapper.selectByParent(comment.getResourceId());

		Comment rootComment = commentMapper.selectById(comment.getRootId());

		comment.setUsername(userInfo.getUserName());
		comment.setReplyNum(comments.size());
		comment.setRootComment(rootComment == null ? "" : rootComment.getContent());

		return comment;
	}

	/**
	 * 数据格式转换
	 *
	 * @param note
	 * @return
	 */
	private PersonNote noteToBo(PersonNote note) {
		UserInfo userInfo = remoteUserService.getById(note.getUserId());
		note.setUserName(userInfo.getUserName());
		if(note.getResourceType() != null) {
			String themeName = note.getThemeName();
			if(note.getResourceType() == Note.NoteResourceType.OPTION.getCode()) {
				note.setThemeName(EXERCISE.getMsg() + "-" + Difficulty.ofCode(note.getDifficulty()) + "-" + themeName);
			}
			if(note.getResourceType() == Note.NoteResourceType.THEORY.getCode()) {
				note.setThemeName(THEORY.getMsg() + "-" + Difficulty.ofCode(note.getDifficulty()) + "-" + themeName);
			}
		}
		return note;
	}

}
