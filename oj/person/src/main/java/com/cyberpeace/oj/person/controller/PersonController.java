package com.cyberpeace.oj.person.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.*;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.person.controller.req.ChartLineReq;
import com.cyberpeace.oj.person.controller.req.PageMistakeReq;
import com.cyberpeace.oj.person.controller.req.PageNoteReq;
import com.cyberpeace.oj.person.controller.req.TheoryReq;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import com.cyberpeace.oj.practice.controller.res.CategoryProgressRes;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.controller.res.PracticeLastWeekRes;
import com.cyberpeace.oj.practice.controller.res.ProgressStatisticsRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSetMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.ProgressProportion;
import com.cyberpeace.oj.practice.service.bo.WebOperationBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Api(tags = "oj-前台个人中心api")
@RestController
@RequestMapping("/oj/person")
@RequiresAuthentication
public class PersonController {

	@Autowired
	private PersonCenterService personCenterService;

	@Autowired
	private PracticeTheoryService practiceTheoryService;

	@Autowired
	private PracticeOperationService practiceOperationService;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private PracticeSolvedMapper practiceSolvedMapper;

	@Autowired
	private PracticeSetMapper practiceSetMapper;

	@Autowired
	RemoteUserService remoteUserService;

	@Autowired
	private PracticeMistakeService practiceMistakeService;

	@ApiOperation("当前用户所有评论")
	@GetMapping("/comments")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonComment>> listComments(@ApiParam @Validated PageReqBase pageReq) {
		Page<PersonComment> page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String parent = "";
		IPage<PersonComment> comments = personCenterService.pageComment(page, userId, parent,
				authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有回复")
	@GetMapping("/replies")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonComment>> listReplies(@ApiParam @Validated PageReqBase pageReq) {
		Page<PersonComment> page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		String parent = "parent";
		IPage<PersonComment> comments = personCenterService.pageComment(page, userId, parent,
				authContext);
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有笔记")
	@GetMapping("/notes")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonNote>> listNotes(@ApiParam @Validated PageNoteReq pageReq) {
		Page<PersonNote> page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		IPage<PersonNote> comments = personCenterService.pageNotes(page, userId, authContext,
				pageReq.getBusinessType(),
				pageReq.getPracticeType());
		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有收藏")
	@GetMapping("/collections")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonCollection>> listCollections(
			@ApiParam @Validated PageReqBase pageReq, @ApiParam @Validated String type,
			@RequestParam(value = "practiceType", required = false) Integer practiceType) {
		Page<PersonCollection> page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);

		IPage<PersonCollection> collections = personCenterService.pageCollections(page, userId,
				type, authContext, practiceType);
		return new BaseRsp<>(PageUtil.getListResult(collections));
	}

	@ApiOperation("当前用户学习课程和练习记录")
	@GetMapping("/study_records")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<StudyRecord> courseAndPracticeRecords() {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		return personCenterService.studyRecords(userId, authContext);
	}

	@ApiOperation("当前用户30天内课程、实验报告数量")
	@GetMapping("/study_period_records")
	@RequiresPermissions(
			value = { MenuConstant.WEB_PERSON_CENTER, MenuConstant.CMS_COURSE_STATISTICS },
			logical = Logical.OR)
	public BaseRsp<BrokenLine> courseAndPracticeRecordsByMonth(
			@RequestParam(value = "user_id", required = false) String userId,
			@RequestParam(value = "isWeb", required = false) Boolean isWeb) {
		Date endTime = new Date();
		Date startTime = DateUtils.addDays(endTime, -CommonConstant.THIRTY);
		if (StringUtils.isEmpty(userId)){
			userId = UserInfoContext.getUserContext().getUserId();
		} else {
			UserInfo userInfo = remoteUserService.getById(userId);
			UserInfoContext.setUserContext(userInfo);
		}
		if (null == isWeb) {
			isWeb = true;
		}
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				isWeb);
		return personCenterService.studyPeriodRecords(userId, startTime, endTime, authContext);
	}

	@ApiOperation("查询收藏习题列表")
	@GetMapping("/my_all_collection_practice")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<List<CollectionPracticeBo>> allCollectionPractice() {

		// 查询收藏的理论习题
		List<CollectionPracticeBo> theoryCollection = personCenterService.selectCollectionPractice(
				UserInfoContext.getUserContext().getUserId(),
				ResourceCollection.BusinessType.THEORY.getCode());

		// 查询收藏的实操习题
		List<CollectionPracticeBo> operationCollection = personCenterService
				.selectCollectionPractice(UserInfoContext.getUserContext().getUserId(),
						ResourceCollection.BusinessType.OPERATION.getCode());

		theoryCollection.addAll(operationCollection);

		// 重新排序
		String userId = UserInfoContext.getUserContext().getUserId();

		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		List<String> resourceIds = personCenterService.selectCollectionResourceIds(userId, authContext);
		List<CollectionPracticeBo> sortedCollections = resortCollections(theoryCollection, resourceIds);
		return new BaseRsp<>(sortedCollections);

	}

	@ApiOperation("收藏理论题详情页")
	@GetMapping("/collection_theory_practice_detail")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<WebTheoryBo> collectionTheoryRetrieve(@RequestParam(value = "resource_id") String resourceId) {
		WebTheoryBo theoryBo = practiceTheoryService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId(), Boolean.TRUE);
		return new BaseRsp<>(theoryBo);
	}

	@ApiOperation("收藏实操题详情页")
	@GetMapping("/collection_operate_practice_detail")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<WebOperationBo> collectionOperateRetrieve(@RequestParam(value = "resource_id") String resourceId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		WebOperationBo theoryBo = practiceOperationService.selectById(resourceId, authContext,
				Boolean.TRUE);
		return new BaseRsp<>(theoryBo);
	}

	@ApiOperation("查看理论题错题页面")
	@GetMapping("/mistake")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ListRsp<PersonalMistakeRes>> getPracticeMistake(PageMistakeReq pageReq) {
		if (StringUtils.isBlank(pageReq.getSort())) {
			pageReq.setSort("-update_time");
		}
		Page<PersonalMistakeRes> page = pageReq.toPageParam();

		List<PersonalMistakeRes> personalMistakeResList = practiceMistakeService.getPracticeMistake();
		ListRsp<PersonalMistakeRes> listResult = PageUtil.getListResult(personalMistakeResList, page, personalMistakeResList.size());

		return new BaseRsp<>(listResult);
	}

	@ApiOperation("批量移除错题")
	@DeleteMapping("/mistake")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<Boolean> bathDelete(@RequestBody @Validated DeleteReq deleteReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(practiceMistakeService.bathDelete(deleteReq.getIds(), userId));
	}

	@ApiOperation("获取错题集id列表")
	@GetMapping("/mistake_ids")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<List<String>> getPracticeMistakeIds() {
		List<PersonalMistakeRes> personalMistakeResList = practiceMistakeService.getPracticeMistake();
		return new BaseRsp<>(StreamApiUtils.map(personalMistakeResList, PersonalMistakeRes::getPracticeId));
	}

	@ApiOperation("习题饼图")
	@GetMapping("/practices_pie")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<TheoryReq> getPracticesToPie() {
		ProgressStatisticsRes progressStatistics = practiceTheoryService.getProgressStatistics(null);
		ProgressProportion sum = progressStatistics.getSum();
		List<CategoryProgressRes> categoryProgressResList = practiceOperationService.getOperationCategoryProgress();
		int operateSum = categoryProgressResList.stream().mapToInt(CategoryProgressRes::getSum).sum();
		int operateCnt = categoryProgressResList.stream().mapToInt(CategoryProgressRes::getCount).sum();
		TheoryReq theoryReq = new TheoryReq();
		theoryReq.setTotalTheories(sum.getSum());
		theoryReq.setTotalOperates(operateSum);
		theoryReq.setFinishedOperates(operateCnt);
		theoryReq.setFinishedTheories(sum.getCount());
		return new BaseRsp<>(theoryReq);
	}

	@ApiOperation("习题折线图")
	@GetMapping("/practices_lineChart")
	@RequiresPermissions(MenuConstant.WEB_PERSON_CENTER)
	public BaseRsp<ChartLineReq> getPracticesToLineChart() {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		ChartLineReq res = new ChartLineReq();
		// 过滤无权限习题集
		List<String> authSetIds = practiceSetMapper.selectByAuthContext(authContext);
		// 过滤非公开习题集
		List<PracticeSet> practiceSets = practiceSetMapper
				.selectByType(PracticeSet.PracticeType.THEORY.getCode(), authContext.getIsWeb());
		List<String> setIds = StreamApiUtils.map(practiceSets, PracticeSet::getResourceId);
		authSetIds.retainAll(setIds);
		if (CollectionUtils.isEmpty(authSetIds)) {
			return new BaseRsp<>(res);
		}

		List<PracticeLastWeekRes> practiceLastWeek = practiceSolvedMapper.getCntLastWeek(PracticeSet.PracticeType.THEORY.getCode(), setIds);
		Map<Integer, List<PracticeLastWeekRes>> group = StreamApiUtils.groupBy(practiceLastWeek, PracticeLastWeekRes::getPracticeType);

		List<String> date = practiceLastWeek.stream().map(item -> TimeUtil.format8(item.getDate())).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

		List<PracticeLastWeekRes> theoryLastWeekRes = group.getOrDefault(PracticeSet.PracticeType.THEORY.getCode(), Collections.emptyList());
		List<PracticeLastWeekRes> operateLastWeekRes = group.getOrDefault(PracticeSet.PracticeType.OPERATION.getCode(), Collections.emptyList());

		List<Integer> theoryCnt = getCountList(theoryLastWeekRes);
		List<Integer> operateCnt = getCountList(operateLastWeekRes);

		res.setDate(date);
		res.setFinishedTheories(theoryCnt);
		res.setFinishedOperates(operateCnt);

		return new BaseRsp<>(res);
	}

	private List<Integer> getCountList(List<PracticeLastWeekRes> list) {
		return list.stream().map(PracticeLastWeekRes::getDate).collect(Collectors.groupingBy(TimeUtil::format8, Collectors.counting()))
				.values().stream().map(Long::intValue).collect(Collectors.toList());
	}

	private List<CollectionPracticeBo> resortCollections(List<CollectionPracticeBo> collectionPracticeBos, List<String> resourceIds) {
		if(CollectionUtils.isEmpty(collectionPracticeBos) ||
				CollectionUtils.isEmpty(resourceIds)) {
			return new ArrayList<>();
		}
		List<CollectionPracticeBo> resortedCollections = new ArrayList<>();
		for(String resourceId : resourceIds) {
			for(CollectionPracticeBo collectionPracticeBo : collectionPracticeBos) {
				if(resourceId.equals(collectionPracticeBo.getResourceId())) {
					resortedCollections.add(collectionPracticeBo);
					break;
				}

			}
		}
		return resortedCollections;
	}
}
