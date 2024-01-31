package com.cyberpeace.oj.person.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.auth.controller.req.UserReq;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.mapper.SysUserMapper;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.auth.service.bo.UserRes;
import com.cyberpeace.common.annoations.AdminRequired;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ExcelHeader;
import com.cyberpeace.common.constant.FileSunffixConstant;
import com.cyberpeace.common.model.*;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.bo.OpsQuestionBo;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.bo.PersonAllStatsBo;
import com.cyberpeace.oj.course.service.bo.PersonStatsBo;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.person.controller.req.PageMistakeReq;
import com.cyberpeace.oj.person.controller.req.PageNoteReq;
import com.cyberpeace.oj.person.controller.res.StudyCalendarRes;
import com.cyberpeace.oj.person.controller.res.StudyStaticsRes;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.dao.mapper.PersonCenterMapper;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import com.cyberpeace.oj.practice.controller.res.PersonalMistakeRes;
import com.cyberpeace.oj.practice.controller.res.ProgressPracticeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;
import com.cyberpeace.oj.practice.dao.mapper.PracticeSolvedMapper;
import com.cyberpeace.oj.practice.service.PracticeMistakeService;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import com.cyberpeace.oj.practice.service.bo.WebOperationBo;
import com.cyberpeace.oj.practice.service.bo.WebTheoryBo;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-前台个人中心api")
@RestController
@RequestMapping("/oj/person")
public class PersonController {

    @Autowired
    private PersonCenterService personCenterService;

    @Autowired
    private PracticeTheoryService practiceTheoryService;

    @Autowired
    private PracticeOperationService practiceOperationService;

    @Autowired
    RemoteUserService remoteUserService;

    @Autowired
    private PracticeMistakeService practiceMistakeService;

    @Autowired
    LessonRecordMapper lessonRecordMapper;

    @Autowired
    PracticeSolvedMapper practiceSolvedMapper;

    @Autowired
    CourseLessonMapper courseLessonMapper;

    @Autowired
    PersonCenterMapper personCenterMapper;

    @Autowired
	CourseMapper courseMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RemoteAdService remoteAdService;


    @ApiOperation("课程学习统计")
    @GetMapping("/course-stats")
	@AdminRequired
    public BaseRsp<PersonAllStatsBo> personalStats() {
        PersonAllStatsBo res = new PersonAllStatsBo();

        List<StudyRecord> studyRecords = personCenterMapper.selectRecord();
        StudyStaticsRes allLesson = personCenterMapper.selectLessonAllCount();
        Integer lessonAllCount = allLesson.getLessonAllCount();
        long finishCourseUser = studyRecords.stream().filter(s -> s.getCourseCount().equals(lessonAllCount)).count();
		List<String> users = StreamApiUtils.getColumn(studyRecords, StudyRecord::getUserId);
        res.setStudyCount(users.isEmpty() ? 0 : users.size());
		res.setCompleteCount((int) finishCourseUser);

		LambdaQueryWrapper<PracticeSolved> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(PracticeSolved::getSolved,true );
		List<PracticeSolved> solvedList = practiceSolvedMapper.selectList(wrapper);
		List<String> practiceUsers = solvedList.stream().map(PracticeSolved::getSubmitUserId).distinct().collect(Collectors.toList());
		res.setPracticePersonCount(practiceUsers.size());
		res.setPracticeCount(solvedList.size());
		res.setPracticeRate(solvedList.size() * practiceUsers.size() == 0 ? 0 : (solvedList.size() / practiceUsers.size()));
        // TODO 模拟考试数据待统计
        return new BaseRsp<>(res);
    }

    @ApiOperation("学习数据")
    @GetMapping("/person-stats")
	@AdminRequired
    public BaseRsp<ListRsp<PersonStatsBo>> personalStatsDetail4Course(
            UserReq req) {

        ListRsp<UserRes> userResListRsp = sysUserService.pageUserFilterAdmin(req);
        List<UserRes> userInfos = userResListRsp.getList();
		userInfos = userInfos.stream().filter(s-> !s.getAdmin()).collect(Collectors.toList());
        ListRsp<PersonStatsBo> rsp = new ListRsp<>();
        long total = userResListRsp.getTotal();
        if (userInfos.isEmpty()) {
            return new BaseRsp<>(rsp);
        }
        List<PersonStatsBo> res;

        res = getPersonStatsBos(userInfos);
        rsp.setList(res);
        rsp.setTotal(total);
        return new BaseRsp<>(rsp);
    }

    private List<PersonStatsBo> getPersonStatsBos(List<UserRes> userInfos) {
        List<PersonStatsBo> res;
        List<String> userIds = StreamApiUtils.getColumn(userInfos, UserRes::getResourceId);
		List<LessonRecord> lessonRecords = personCenterMapper.selectByUsers(userIds);
		Map<String, Integer> lessonMap = StreamApiUtils.toMap(lessonRecords, LessonRecord::getUserId, LessonRecord::getLearnedType);
		Integer lessonAllCount = courseLessonMapper.selectCount(new QueryWrapper<>());

        List<StudyRecord> studyRecord = personCenterMapper.selectSlovedPractice(userIds);
        Map<String, StudyRecord> map = StreamApiUtils.toMap(studyRecord, StudyRecord::getUserId, s -> s);

        int theoryPracticeAllCount = personCenterMapper.selectTheoryPracticeByAuth().size();
        int operationPracticeAllCount = personCenterMapper.selectOperationPracticeByAuth().size();

		Map<String, JSONObject> userExamPassMap = remoteAdService.examPassList(userIds);

        res = userInfos.stream().map(s -> {
            StudyRecord record = map.get(s.getResourceId());
            PersonStatsBo personStatsBo = new PersonStatsBo();
            if (null != record) {
                personStatsBo.setTheoryCorrectRate(record.getTheoryPracticeCount() * theoryPracticeAllCount == 0 ? "0%": record.getTheoryPracticeCount()*100 / theoryPracticeAllCount+"%");
                personStatsBo.setOperateCorrectRate(record.getOperationPracticeCount() * operationPracticeAllCount == 0 ?"0%" : record.getOperationPracticeCount()*100 / operationPracticeAllCount+"%");
            }

            if (lessonMap.containsKey(s.getResourceId())) {
                personStatsBo.setCourseProgress(lessonMap.get(s.getResourceId()) * lessonAllCount == 0 ?"0%": lessonMap.get(s.getResourceId())*100 / lessonAllCount+"%");
            }
            if(userExamPassMap.containsKey(s.getResourceId())){
				String passRate = userExamPassMap.getOrDefault(s.getResourceId(), new JSONObject()).getString("pass_rate");
				personStatsBo.setExamPassRate(passRate==null?"0%":(int)Double.parseDouble(passRate)+"%");
			}
            personStatsBo.setUsername(s.getUsername());
            personStatsBo.setName(s.getName());
            return personStatsBo;
        }).collect(Collectors.toList());
        return res;
    }

    @ApiOperation("学习数据-全部数据-导出")
    @GetMapping(value = "/user-stats-data-export")
	@AdminRequired
    public void userStatsDataExport(HttpServletResponse response) {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        List<UserRes> userInfos = sysUsers.stream().map(s -> {
            UserRes userRes = new UserRes();
            BeanUtils.copyProperties(s, userRes);
            return userRes;
        }).collect(Collectors.toList());
		userInfos = userInfos.stream().filter(s-> !s.getAdmin()).collect(Collectors.toList());
        List<PersonStatsBo> res;
        res = getPersonStatsBos(userInfos);

        List<String> passFields = Lists.newArrayList("resourceId");
        String sheetName = ExcelHeader.SHEETNAME.getHeader()[0];
        String filePath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID()
                + CommonConstant.PERIOD + FileSunffixConstant.XLS.getDesc();
        File file = ExcelDataHandler.exportExcel(res, filePath, sheetName,
                passFields, ExcelHeader.PERSON_STUDY_STATISTIC);
        FileUtil.setResponse(file, true, response);
    }


    @ApiOperation("当前用户所有评论")
	@GetMapping("/comments")
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
	public BaseRsp<ListRsp<PersonNote>> listNotes(@ApiParam @Validated PageNoteReq pageReq) {
		Page<PersonNote> page = pageReq.toPageParam();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 权限数据获取
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		IPage<PersonNote> comments = personCenterService.pageNotes(page, userId, authContext,
				pageReq.getBusinessType(),
				pageReq.getPracticeType());

		// 查询运维题的比赛id
		List<OpsQuestionBo> opsQuestionBos = remoteAdService.queryQuestionList();
		Map<String, String> opsMap = opsQuestionBos.stream().collect(Collectors
				.toMap(OpsQuestionBo::getResourceId, OpsQuestionBo::getRaceId, (k1, k2) -> k1));
		comments.getRecords().forEach(
				note -> note.setRaceId(opsMap.getOrDefault(note.getResourceId(), null)));

		return new BaseRsp<>(PageUtil.getListResult(comments));
	}

	@ApiOperation("当前用户所有收藏")
	@GetMapping("/collections")
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

		// 查询运维题的比赛id
		List<OpsQuestionBo> opsQuestionBos = remoteAdService.queryQuestionList();
		Map<String, String> opsMap = opsQuestionBos.stream().collect(Collectors
				.toMap(OpsQuestionBo::getResourceId, OpsQuestionBo::getRaceId, (k1, k2) -> k1));
		collections.getRecords().forEach(
				collect -> collect.setRaceId(opsMap.getOrDefault(collect.getResourceId(), null)));

		return new BaseRsp<>(PageUtil.getListResult(collections));
	}


	@ApiOperation("学习统计")
	@GetMapping("/study_statics")
	public BaseRsp<StudyStaticsRes> studyStatics() {
		return new BaseRsp<>(personCenterService.studyStatics());
	}

	@ApiOperation("日程表")
	@GetMapping("/study_calendar")
	public BaseRsp<List<StudyCalendarRes>> studyCalendar(@RequestParam(value = "startTime") String  startTime,
														 @RequestParam(value = "endTime") String  endTime, HttpServletRequest request) {
		List<StudyCalendarRes> studyCalendarRes = personCenterService.studyCalendar(startTime, endTime);
		return new BaseRsp<>(studyCalendarRes);
	}

	@ApiOperation("能力框架")
	@GetMapping("/ability_framework")
	public BaseRsp<List<ProgressPracticeRes>>  abilityFramework() {
		return new BaseRsp<>(personCenterService.abilityFramework());
	}

	@ApiOperation("当前用户30天内课程、实验报告数量")
	@GetMapping("/study_period_records")
	public BaseRsp<BrokenLine> courseAndPracticeRecordsByMonth(
			@RequestParam(value = "user_id", required = false) String userId,
			@RequestParam(value = "isWeb", required = false) Boolean isWeb) {
		Date endTime = new Date();
		Date startTime = DateUtils.addDays(endTime, -CommonConstant.THIRTY);
		if (StringUtils.isEmpty(userId)){
			userId = UserInfoContext.getUserContext().getUserId();
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
	public BaseRsp<WebTheoryBo> collectionTheoryRetrieve(@RequestParam(value = "resource_id") String resourceId) {
		WebTheoryBo theoryBo = practiceTheoryService.selectById(resourceId,
				UserInfoContext.getUserContext().getUserId(), Boolean.TRUE);
		return new BaseRsp<>(theoryBo);
	}

	@ApiOperation("收藏实操题详情页")
	@GetMapping("/collection_operate_practice_detail")
	public BaseRsp<WebOperationBo> collectionOperateRetrieve(@RequestParam(value = "resource_id") String resourceId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		WebOperationBo theoryBo = practiceOperationService.selectById(resourceId, authContext,
				Boolean.TRUE);
		return new BaseRsp<>(theoryBo);
	}

	@ApiOperation("查看理论题错题页面")
	@GetMapping("/mistake")
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
	public BaseRsp<Boolean> bathDelete(@RequestBody @Validated DeleteReq deleteReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		return new BaseRsp<>(practiceMistakeService.bathDelete(deleteReq.getIds(), userId));
	}

	@ApiOperation("获取错题集id列表")
	@GetMapping("/mistake_ids")
	public BaseRsp<List<String>> getPracticeMistakeIds() {
		List<PersonalMistakeRes> personalMistakeResList = practiceMistakeService.getPracticeMistake();
		return new BaseRsp<>(StreamApiUtils.map(personalMistakeResList, PersonalMistakeRes::getPracticeId));
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
