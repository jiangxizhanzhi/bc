package com.cyberpeace.oj.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.MyRepository;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.MyRepositoryMapper;
import com.cyberpeace.oj.course.service.SharedRepositoryService;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.ojbase.dao.mapper.NoteMapper;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheoryRef;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryRefMapper;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SharedRepositoryServiceImpl implements SharedRepositoryService {

	@Autowired
	private MyRepositoryMapper myRepositoryMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	@Autowired
	private PracticeTheoryRefMapper practiceTheoryRefMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private UtilService utilService;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private NoteMapper noteMapper;

	@Override
	public ListRsp<AdminLessonListBo> getSharedRepository(PageReqBase req, String user) {
		Page page = req.toPageParam();
		String pattern = req.getPattern();
		List<String> userIds = Lists.newArrayList();
		if (StringUtils.isNotBlank(user)) {
			UserPageContext pageContext = UserPageContext.builder().username(user).build();
			List<UserInfo> userList = remoteUserService.getFuzzyQueryUserList(pageContext);

			userIds = StreamApiUtils.map(userList, UserInfo::getUserId);
		}
		IPage<CourseLesson> iPage = courseLessonMapper.pageShared(page, pattern, userIds);

		return this.convert(iPage);
	}

	@Override
	public ListRsp<AdminLessonListBo> getMyRepository(PageReqBase req) {
		String userId = UserInfoContext.getUserContext().getUserId();
		MyRepository myRepository = myRepositoryMapper.selectById(userId);
		if (null == myRepository) {
			myRepository = this.initMy(userId);
		}
		List<String> myIds = JsonUtil.toJsonObjectArray(myRepository.getMy(), String.class);
		Page page = req.toPageParam();
		if (myIds.isEmpty()) {
			return PageUtil.getListResult(Lists.newArrayList(), page, 0);
		}
		// keep order
		List<String> existLessonIds = courseLessonMapper.selectIdsByIds(myIds);
		List<String> ordered = ListUtil.mergeWithOldOrder(myIds, existLessonIds);
		ListRsp<String> idRes = PageUtil.pageList(ordered, req.getPage(), req.getPerPage());

		IPage<CourseLesson> iPage = courseLessonMapper.pageMy(page, idRes.getList());
		ListRsp<AdminLessonListBo> converted = this.convert(iPage);
		List<AdminLessonListBo> sorted = this.sortById(converted.getList(), idRes.getList());

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<String> courseIds = courseMapper.selectByAuth(authContext);
		for (AdminLessonListBo bo : sorted) {
			bo.setEditable(courseIds.contains(bo.getCourseId()));
		}
		ListRsp<AdminLessonListBo> res = new ListRsp<>();
		res.setTotal(idRes.getTotal());
		res.setLastPage(idRes.getLastPage());
		res.setCurrentPage(idRes.getCurrentPage());
		res.setList(sorted);

		return res;
	}

	@Override
	public void add2My(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(ErrorCode.BLANK_ID);
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		MyRepository myRepository = myRepositoryMapper.selectById(userId);
		if (null == myRepository) {
			myRepository = this.initMy(userId);
		}
		List<String> myIds = JsonUtil.toJsonObjectArray(myRepository.getMy(), String.class);
		if (!myIds.contains(resourceId)) {
			myIds.add(resourceId);
			myRepository.setMy(JsonUtil.arrayToString(myIds));
			myRepositoryMapper.updateById(myRepository);
		}
	}

	@Override
	public void add2My(List<CourseLesson> lessons) {
		if (lessons.isEmpty()) {
			return;
		}
		String userId = lessons.get(0).getCreateUserId();
		MyRepository myRepository = myRepositoryMapper.selectById(userId);
		if (null == myRepository) {
			myRepository = this.initMy(userId);
		}
		List<String> myIds = JsonUtil.toJsonObjectArray(myRepository.getMy(), String.class);
		int size = myIds.size();
		for (CourseLesson lesson : lessons) {
			if (!myIds.contains(lesson.getResourceId())
					&& CourseLesson.LessonType.EXPERIMENT.getCode().equals(lesson.getType())) {
				myIds.add(lesson.getResourceId());
			}
		}
		if (myIds.size() > size) {
			myRepository.setMy(JsonUtil.arrayToString(myIds));
			myRepositoryMapper.updateById(myRepository);
		}
	}

	@Override
	public void share(List<String> ids) {
		courseLessonMapper.updateShare(ids, UserInfoContext.getUserContext().getUserId(), 1);
	}

	@Override
	public void privatize(List<String> ids) {
		courseLessonMapper.updateShare(ids, UserInfoContext.getUserContext().getUserId(), 0);
	}

	@Override
	public void delete(List<String> ids) {
		MyRepository myRepository = myRepositoryMapper
				.selectById(UserInfoContext.getUserContext().getUserId());
		List<String> myIds = JsonUtil.toJsonObjectArray(myRepository.getMy(), String.class);
		if (myIds.removeAll(ids)) {
			myRepository.setMy(JsonUtil.arrayToString(myIds));
			myRepositoryMapper.updateById(myRepository);
		}
	}

	@Override
	public List<AdminLessonListBo> getAllMy() {
		MyRepository myRepository = myRepositoryMapper
				.selectById(UserInfoContext.getUserContext().getUserId());
		if (null == myRepository) {
			myRepository = this.initMy(UserInfoContext.getUserContext().getUserId());
		}
		List<String> myIds = JsonUtil.toJsonObjectArray(myRepository.getMy(), String.class);
		if (myIds.isEmpty()) {
			return Lists.newArrayList();
		}
		List<AdminLessonListBo> unsorted = courseLessonMapper.selectByIds(myIds);
		List<AdminLessonListBo> res = this.sortById(unsorted, myIds);
		return res;
	}

	@Override
	public void saveArrangement(List<String> ids) {
		MyRepository myRepository = myRepositoryMapper
				.selectById(UserInfoContext.getUserContext().getUserId());
		myRepository.setMy(JsonUtil.arrayToString(ids));
		myRepositoryMapper.updateById(myRepository);
	}

	@Override
	public List<AdminLessonListBo> lesson2AdminBo(List<CourseLesson> lessonList) {
		List<AdminLessonListBo> res = Lists.newArrayList();
		if (lessonList.isEmpty()) {
			return res;
		}
		List<String> paperIds = StreamApiUtils.getColumn(lessonList, CourseLesson::getPaperId);
		LambdaQueryWrapper<PracticeOperationRef> filterOperation = new LambdaQueryWrapper<>();
		LambdaQueryWrapper<PracticeTheoryRef> filterTheory = new LambdaQueryWrapper<>();
		if (!paperIds.isEmpty()) {
			filterOperation.in(PracticeOperationRef::getPaperId, paperIds);
			filterTheory.in(PracticeTheoryRef::getPaperId, paperIds);
		}
		List<PracticeOperationRef> practiceOperationRefs = practiceOperationRefMapper
				.selectList(filterOperation);
		Map<String, List<PracticeOperationRef>> operationMap = StreamApiUtils
				.groupBy(practiceOperationRefs, PracticeOperationRef::getPaperId);
		List<PracticeTheoryRef> practiceTheoryRefs = practiceTheoryRefMapper
				.selectList(filterTheory);
		Map<String, List<PracticeTheoryRef>> theoryMap = StreamApiUtils.groupBy(practiceTheoryRefs,
				PracticeTheoryRef::getPaperId);
		List<String> userIds = StreamApiUtils.map(lessonList, CourseLesson::getCreateUserId);
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(userIds);
		List<String> courseIds = StreamApiUtils.map(lessonList, CourseLesson::getCourseId);
		List<Course> courseList = courseMapper.selectBatchIds(courseIds);
		Map<String, Course> courseMap = StreamApiUtils.toMap(courseList, Course::getResourceId,
				c -> c);
		List<Paper> papers = paperMapper.selectBatchIds(paperIds);
		Map<String, Paper> paperMap = StreamApiUtils.toMap(papers, Paper::getResourceId, p -> p);
		for (CourseLesson courseLesson : lessonList) {
			// 前台没有课程列表查询 不用管是否存在课堂场景 修改sceneShareMode
			AdminLessonListBo adminLessonListBo = BeanUtils.copy(courseLesson,
					AdminLessonListBo.class);

			// 附件数据相关
			if (StringUtils.isNotBlank(courseLesson.getAttachmentIds())) {
				List<AttachmentBo> attachmentBoList = attachmentService
						.selectByIds(courseLesson.getAttachmentIds(), false);
				adminLessonListBo.setLessonFile(attachmentBoList);
			}

			// 文件相关处理
			if (StringUtils.isNotBlank(courseLesson.getHandoutUrl())
					&& courseLesson.getHandoutType().intValue() == CourseLesson.HandoutType.MD
							.getCode().intValue()) {
				adminLessonListBo.setHandoutUrl(courseLesson.getHandoutUrl());
			} else {
				adminLessonListBo
						.setHandoutUrl(utilService.handlerFileUrl(courseLesson.getHandoutUrl()));
			}
			adminLessonListBo.setVideoUrl(utilService.handlerFileUrl(courseLesson.getVideoUrl()));

			// 试卷相关
			Paper paper = paperMap.get(courseLesson.getPaperId());

			String paperId = courseLesson.getPaperId();
			List<PracticeOperationRef> operations = operationMap.getOrDefault(paperId,
					new ArrayList<>());
			List<PracticeTheoryRef> theories = theoryMap.getOrDefault(paperId, new ArrayList<>());
			adminLessonListBo.setPracticeCount(operations.size() + theories.size());
			adminLessonListBo.setPracticeCount(paper.getPracticeCount());
			adminLessonListBo.setPaperPublic(paper.getIsPublic());

			// 课程相关
			Course course = courseMap.get(courseLesson.getCourseId());
			adminLessonListBo.setCourseName(course.getName());

			// 用户相关
			adminLessonListBo.setCreateUserName(
					userNameMap.getOrDefault(courseLesson.getCreateUserId(), ""));

			res.add(adminLessonListBo);
		}
		return res;
	}

	private MyRepository initMy(String userId) {
		List<String> lessonIds = courseLessonMapper.selectMyExp(userId);
		MyRepository myRepository = new MyRepository();
		myRepository.setUserId(userId);
		myRepository.setMy(JsonUtil.arrayToString(lessonIds));
		myRepositoryMapper.insert(myRepository);
		return myRepository;
	}

	private ListRsp<AdminLessonListBo> convert(IPage<CourseLesson> iPage) {
		List<CourseLesson> lessonList = iPage.getRecords();
		List<AdminLessonListBo> adminLessonListBos = lesson2AdminBo(lessonList);
		ListRsp<AdminLessonListBo> res = PageUtil.getListResult(iPage);
		res.setList(adminLessonListBos);
		return res;
	}

	private List<AdminLessonListBo> sortById(List<AdminLessonListBo> boList, List<String> idList) {
		Map<String, AdminLessonListBo> boMap = StreamApiUtils.toMap(boList,
				AdminLessonListBo::getResourceId, bo -> bo);
		List<AdminLessonListBo> res = Lists.newArrayList();
		for (String id : idList) {
			if (boMap.containsKey(id)) {
				res.add(boMap.get(id));
			}
		}
		return res;
	}

}
