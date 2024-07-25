package com.cyberpeace.oj.person.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.dao.entity.NoteEvaluate;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.CommentMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.person.controller.req.IndexCategoryReq;
import com.cyberpeace.oj.person.controller.req.IndexCourseReq;
import com.cyberpeace.oj.person.controller.res.StudyCalendarRes;
import com.cyberpeace.oj.person.controller.res.StudyHistoryRes;
import com.cyberpeace.oj.person.controller.res.StudyStaticsRes;
import com.cyberpeace.oj.person.dao.entity.*;
import com.cyberpeace.oj.person.dao.mapper.PersonCenterMapper;
import com.cyberpeace.oj.person.service.IndexInfoService;
import com.cyberpeace.oj.person.service.PersonCenterService;
import com.cyberpeace.oj.person.service.bo.CollectionPracticeBo;
import com.cyberpeace.oj.practice.controller.res.OperationRecordRes;
import com.cyberpeace.oj.practice.controller.res.ProgressPracticeRes;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

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

    @Autowired
    IndexInfoService indexInfoService;

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
    public IPage<PersonNote> pageNotes(Page page, String userId, AuthContext authContext,
                                       Integer businessType, Integer practiceType) {
        Integer noteType = Note.NoteType.NOTE.getCode();
        List<String> resourceIds = new ArrayList<>();
        IPage<PersonNote> iPage = personCenterMapper.selectNoteByUser(page, userId, noteType,
                resourceIds, businessType, practiceType);
        return iPage.convert(this::noteToBo);
    }

    /**
     * @param page
     * @param userId 用户ID
     * @return
     */
    @Override
    public IPage<PersonCollection> pageCollections(Page page, String userId, String type,
                                                   AuthContext authContext, Integer practiceType) {
        List<String> resourceIds = new ArrayList<>();
        if (null != authContext) {
            List<String> courseIds = courseMapper.selectByAuth(authContext);
            List<String> publicCourses = personCenterMapper.selectPublicCourse(courseIds);
            resourceIds.addAll(publicCourses);
            resourceIds.addAll(personCenterMapper.selectLessonByAuth(publicCourses));
            resourceIds.addAll(personCenterMapper.selectTheoryPracticeByAuth());
            resourceIds.addAll(personCenterMapper.selectOperationPracticeByAuth());
        }
        return personCenterMapper.selectByUser(page, userId, type, resourceIds, practiceType);
    }

    @Override
    public BaseRsp<StudyRecord> studyRecords(String userId, AuthContext authContext) {
        List<String> lessonIds = new ArrayList<>();
        List<String> expLessonIds = new ArrayList<>();
        if (null != authContext) {
            lessonIds = StreamApiUtils.getColumn(courseLessonMapper.selectList(null), CourseLesson::getResourceId);
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
            expLessonIds = personCenterMapper.selectLessonByAdmin(CourseLesson.LessonType.EXPERIMENT.getCode());
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
            if (CollectionUtils.isEmpty(courseRecordLists)) {
                courseRecords.add(0);
            } else {
                courseRecords.add(courseRecordLists.size());
            }
            List<Note> finishReportLists = finishReportMap.get(currentDate);
            if (CollectionUtils.isEmpty(finishReportLists)) {
                reportRecords.add(0);
            } else {
                reportRecords.add(finishReportLists.size());
            }
            List<NoteEvaluate> reportAverageLists = reportAverageMap.get(currentDate);
            if (CollectionUtils.isEmpty(reportAverageLists)) {
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

    @Override
    public StudyStaticsRes studyStatics() {
        StudyStaticsRes res = new StudyStaticsRes();
        List<String> lessonIds = new ArrayList<>();
        String userId = UserInfoContext.getUserContext().getUserId();

        // 获取课程学习进度
        StudyRecord studyRecord = personCenterMapper.selectRecordByUser(userId, lessonIds);
        res.setTheoryLessonCount(studyRecord.getTheoryCount()==null?0:studyRecord.getTheoryCount());
        res.setExpLessonCount(studyRecord.getExpCount()==null?0:studyRecord.getExpCount());
        res.setLessonCount(res.getExpLessonCount()+res.getTheoryLessonCount());
        StudyStaticsRes lessonCount = personCenterMapper.selectLessonAllCount();
        res.setLessonAllCount(lessonCount.getLessonAllCount());
        res.setTheoryLessonAllCount(lessonCount.getTheoryLessonAllCount());
        res.setExpLessonAllCount(lessonCount.getExpLessonAllCount());
        //获取练习正确率
        StudyRecord practiceByUser = personCenterMapper.selectSlovedPracticeByUser(userId);
        Integer operationPracticeCount = practiceByUser.getOperationPracticeCount();
        Integer theoryPracticeCount = practiceByUser.getTheoryPracticeCount();
        res.setOperationPracticeCount(null == operationPracticeCount ? 0 : operationPracticeCount);
        res.setTheoryPracticeCount(null == theoryPracticeCount ? 0 : theoryPracticeCount);
        res.setPracticeCount(res.getOperationPracticeCount()+res.getTheoryPracticeCount());
        int theoryPracticeAllCount = personCenterMapper.selectTheoryPracticeByAuth().size();
        int operationPracticeAllCount = personCenterMapper.selectOperationPracticeByAuth().size();
        res.setOperationPracticeAllCount(operationPracticeAllCount);
        res.setTheoryPracticeAllCount(theoryPracticeAllCount);
        res.setPracticeAllCount(res.getOperationPracticeAllCount()+res.getTheoryPracticeAllCount());

        return res;
    }

    @Override
    public List<StudyCalendarRes> studyCalendar(String startTime, String endTime) {
        List<StudyCalendarRes> res = new ArrayList<>();
        String userId = UserInfoContext.getUserContext().getUserId();
        //课时
        List<StudyHistoryRes>  lessonList= personCenterMapper.selectLessonStudyCalendar(startTime, endTime,userId);
        //练习
        List<StudyHistoryRes>  parcticeList= personCenterMapper.selectParcticeStudyCalendar(startTime, endTime,userId);

        lessonList.addAll(parcticeList);
        lessonList.stream().collect(Collectors.groupingBy(StudyHistoryRes::getDate)).forEach((k,v)->{
            StudyCalendarRes studyHistoryRes = new StudyCalendarRes();
            studyHistoryRes.setDate(k);
            studyHistoryRes.setHistoryList(v);
            res.add(studyHistoryRes);
        });
        for (StudyCalendarRes re : res) {
            List<StudyHistoryRes> theoryList = re.getHistoryList().stream().filter(a -> StudyHistoryRes.TypeEnum.PRACTICE.getCode().equals(a.getType())&&1==a.getBusinessType()).collect(Collectors.toList());
            List<StudyHistoryRes> operationList = re.getHistoryList().stream().filter(a -> StudyHistoryRes.TypeEnum.PRACTICE.getCode().equals(a.getType())&&1!=a.getBusinessType()).collect(Collectors.toList());
            re.setHistoryList(re.getHistoryList().stream().filter(a -> !StudyHistoryRes.TypeEnum.PRACTICE.getCode().equals(a.getType())).collect(Collectors.toList()));
            if (CollectionUtils.isNotEmpty(theoryList)) {
                StudyHistoryRes studyHistoryRes = theoryList.get(0);
                studyHistoryRes.setName(studyHistoryRes.getName()+"等"+theoryList.size()+"道理论题");
                re.getHistoryList().add(studyHistoryRes);
            }
            if (CollectionUtils.isNotEmpty(operationList)) {
                StudyHistoryRes studyHistoryRes = operationList.get(0);
                studyHistoryRes.setName(studyHistoryRes.getName()+"等"+operationList.size()+"道实操题");
                re.getHistoryList().add(studyHistoryRes);
            }
        }
        res.sort(Comparator.comparing(StudyCalendarRes::getDate));
        return res;
    }

    private void dealAdHistory(JSONArray adHistoryList,List<StudyHistoryRes>  lessonList,StudyHistoryRes.AdEnum adEnum) {
        for (Object o : adHistoryList) {
            JSONObject jsonObject = (JSONObject) o;
            StudyCalendarRes studyHistoryRes = new StudyCalendarRes();
            studyHistoryRes.setDate(jsonObject.getString("key"));
            JSONArray value = jsonObject.getJSONArray("value");
            if (value==null) {
                continue;
            }
            for (Object o1 : value) {
                JSONObject studyHistory = (JSONObject) o1;
                StudyHistoryRes adStudyHistoryRes = new StudyHistoryRes();
                adStudyHistoryRes.setResourceId(studyHistory.getString(adEnum.getResourceIdName()));
                adStudyHistoryRes.setName(studyHistory.getString(adEnum.getName()));
                adStudyHistoryRes.setType(adEnum.getType());
                adStudyHistoryRes.setBusinessType(adEnum.getBusinessType());
                adStudyHistoryRes.setRaceId(studyHistory.getString(adEnum.getRaceIdName()));
                adStudyHistoryRes.setDate(jsonObject.getString("key"));
                lessonList.add(adStudyHistoryRes);
            }
        }
    }

    @Override
    public List<ProgressPracticeRes> abilityFramework() {
        List<PracticeCategory> practiceCategories = practiceCategoryMapper.selectList(new QueryWrapper<>());
        //理论题 30%
        List<ProgressPracticeRes> theoryList = practiceTheoryMapper.getProgressCategoryStatistics(UserInfoContext.getUserContext().getUserId());
        Map<String, ProgressPracticeRes> theoryMap = theoryList.stream().filter(a->a.getCategoryName()!=null).collect(Collectors.toMap(ProgressPracticeRes::getCategoryName, Function.identity(), (a, b) -> a));
        //实操题 30%
        List<OperationRecordRes> operationList = practiceOperationMapper.getOperationRecordList(UserInfoContext.getUserContext().getUserId());
        //覆盖度 40%
        List<IndexCategoryReq> categoryList = indexInfoService.getIndexInfo().getCategoryList();
        Map<String, List<IndexCourseReq>> categoryCoverageMap = categoryList.stream().collect(Collectors.toMap(IndexCategoryReq::getCategoryName, IndexCategoryReq::getCourses));

        List<ProgressPracticeRes> res = new ArrayList<>();
        for (PracticeCategory practiceCategory : practiceCategories) {
            ProgressPracticeRes re = theoryMap.getOrDefault(practiceCategory.getName(), new ProgressPracticeRes(0,0));
            if(null == re.getCategoryName()){
                continue;
            }
            BigDecimal theoryRate =re.getSum()==0?BigDecimal.ZERO: BigDecimal.valueOf(re.getCount()*30.0/re.getSum());
            List<IndexCourseReq> coverageReqList = categoryCoverageMap.getOrDefault(re.getCategoryName(), Lists.newArrayList());
            int courseStudyCoverage = coverageReqList.stream().mapToInt(req-> req.getStudyCount() == null ? 0: req.getStudyCount()).sum();
            int courseNumCoverage = coverageReqList.stream().mapToInt(req -> req.getCourseNum() == null ? 0: req.getCourseNum()).sum();
			if (courseStudyCoverage > 0 && courseNumCoverage > 0) {
				theoryRate = theoryRate
						.add(BigDecimal.valueOf(courseStudyCoverage * 40.0 / courseNumCoverage));
			}

            int operationCount = operationList.stream().filter(a -> a.getKnowledge() != null
                    && a.getKnowledge().contains(re.getCategoryName())).mapToInt(a -> 1).sum();
            //实操题
            if (CollectionUtils.isNotEmpty(operationList) ){
                BigDecimal opCount = operationList.stream()
                        .filter(a -> a.getKnowledge() != null
                                && a.getKnowledge().contains(re.getCategoryName()) && Boolean.TRUE.equals(a.getSolved()))
                        .map(a -> BigDecimal.valueOf(30.0 / operationCount)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
                theoryRate = theoryRate.add(opCount);
            }

            re.setCount(theoryRate.setScale(0,BigDecimal.ROUND_HALF_UP).intValue());
            re.setSum(100);
            res.add(re);
        }

        return res;
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
        // 用户只可能是当前用户
        // 这个接口真的需要用户名吗？
        note.setUserName(UserInfoContext.getUserContext().getUserName());
        return note;
    }

}
