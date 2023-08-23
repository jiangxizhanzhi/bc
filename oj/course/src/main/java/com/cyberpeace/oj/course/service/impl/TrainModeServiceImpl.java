package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.TrainModeListReq;
import com.cyberpeace.cr.crbase.controller.req.TrainModeReq;
import com.cyberpeace.cr.crbase.controller.rsp.TrainModeRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.TrainModeMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.TrainingModeService;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @author SN
 */
@Service
@Slf4j
public class TrainModeServiceImpl implements TrainingModeService {
    @Autowired
    private TrainModeMapper trainModeMapper;

    @Autowired
    private RemoteAdService remoteAdService;

    @Autowired
    private BusinessTemplateService businessTemplateService;

    @Autowired
    private RemoteLogService remoteLogService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public ListRsp<TrainModeRsp> list(TrainModeListReq listReq) {

        List<Integer> businessTypeList = BusinessTemplate.BusinessType.BUSINESS_TYPE_LIST;
        List<Integer> trainingTypeList = TrainModeRsp.TrainType.TRAINING_TYPE_LIST;
        List<Integer> categoryTypeList = TrainModeRsp.TrainCategoryType.CATEGORY_TYPE_LIST;
        if (Objects.isNull(listReq.getType())) {
            Page newPage = new Page<>();
            newPage.setPages(1);
            newPage.setSize(10000);
            IPage<TrainModeRsp> trainModeRspPage = trainModeMapper.listBusinessTemplate(newPage, listReq.getPattern(), businessTypeList, categoryTypeList);
            ArrayList<TrainModeRsp> result = new ArrayList<>(trainModeRspPage.getRecords());
            //实训
            IPage<Course> iPage = getCoursePage(newPage,listReq.getPattern());
            List<TrainModeRsp> trainCourseList = getTrainCourseList(iPage);
            result.addAll(trainCourseList);
            //竞赛
            ListRsp<CalendarCompetitionBo> competitionBoList = remoteAdService.listCalendarCompetitionBo(newPage, listReq.getPattern());
            List<TrainModeRsp> competitionList = getCompetitionBoList(competitionBoList);
            result.addAll(competitionList);

            List<TrainModeRsp> collect = result.stream().sorted(Comparator.comparing(TrainModeRsp::getCreateTime).reversed()).collect(Collectors.toList());
            return PageUtil.getListResult(collect, listReq.toPageParam(), result.size());

        } else if (trainingTypeList.contains(listReq.getType())) {
            // 红蓝/团队/单兵其中一种
            Integer businessType = TrainModeRsp.getBusinessTypeByTrainType(listReq.getType());
            Integer trainCategoryType = TrainModeRsp.TrainCategoryType.getTrainCategoryByTrainType(listReq.getType());
            categoryTypeList = Objects.isNull(trainCategoryType) ? null : Collections.singletonList(trainCategoryType);
            IPage<TrainModeRsp> trainModeRspPage = trainModeMapper.listBusinessTemplate(listReq.toPageParam(), listReq.getPattern(), Collections.singletonList(businessType), categoryTypeList);
            return PageUtil.getListResult(trainModeRspPage);

        } else if (TrainModeRsp.TrainType.COURSE.getCode().equals(listReq.getType())) {
            // 单独攻防实训
            IPage<Course> iPage = getCoursePage(listReq.toPageParam(), listReq.getPattern());
            List<TrainModeRsp> trainCourseList = getTrainCourseList(iPage);
            ListRsp<TrainModeRsp> listRsp = new ListRsp<>();
            listRsp.setList(trainCourseList);
            listRsp.setTotal(iPage.getTotal());
            listRsp.setCurrentPage(iPage.getCurrent());
            return listRsp;
        } else {
            //单独竞赛
            ListRsp<CalendarCompetitionBo> competitionBoRspList = remoteAdService.listCalendarCompetitionBo(listReq.toPageParam(), listReq.getPattern());
            List<TrainModeRsp> competitionList = getCompetitionBoList(competitionBoRspList);
            ListRsp<TrainModeRsp> listRsp = new ListRsp<>();
            listRsp.setList(competitionList);
            listRsp.setTotal(competitionBoRspList.getTotal());
            listRsp.setLastPage(competitionBoRspList.getLastPage());
            listRsp.setCurrentPage(competitionBoRspList.getCurrentPage());
            return listRsp;
        }
    }

    private List<TrainModeRsp> getTrainCourseList(IPage<Course> iPage) {
        List<TrainModeRsp> courseList = new ArrayList<>();
        for (Course record : iPage.getRecords()) {
            TrainModeRsp trainModeRsp = new TrainModeRsp();
            trainModeRsp.setResourceId(record.getResourceId());
            trainModeRsp.setName(record.getName());
            trainModeRsp.setDescription(record.getDescription());
            trainModeRsp.setType(TrainModeRsp.TrainType.COURSE.getCode());
            trainModeRsp.setCreateTime(record.getCreateTime());
            courseList.add(trainModeRsp);
        }
        return courseList;
    }

    private IPage<Course> getCoursePage(Page newPage, String pattern) {
        // 分类数据组装
        CourseQueryBo adminQueryBo = new CourseQueryBo();
        adminQueryBo.setPattern(pattern);
        adminQueryBo.setUserId(UserInfoContext.getUserContext().getUserId());
        // 获取权限
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false);
        List<String> courseIds = courseMapper.selectByAuth(authContext);

        // 我的课程 系统课程 共享课程过滤
        SpecialCourseContext specialContext = BeanUtils.copy(authContext, SpecialCourseContext.class);
        if (!specialContext.getMyCourse() && !specialContext.getSystemCourse() && !specialContext.getShareCourse()) {
            return new Page<>();
        }
        if (!specialContext.getMyCourse() || !specialContext.getShareCourse() || !specialContext.getSystemCourse()) {
            courseIds = courseMapper.selectSpecial(courseIds, specialContext);
            if (CollectionUtils.isEmpty(courseIds)) {
                return new Page<>();
            }
        }
        return courseMapper.listByPage(newPage, adminQueryBo, courseIds, authContext.getIsWeb());
    }

    @Override
    public Boolean delete(List<TrainModeReq> trainModeReqList) {
        if (CollectionUtils.isEmpty(trainModeReqList)) {
            return false;
        }
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false);

        List<String> competitionList = new ArrayList<>();
        List<String> trainIdList = new ArrayList<>();
        List<String> courseIdList = new ArrayList<>();
        for (TrainModeReq trainModeReq : trainModeReqList) {
            if (TrainModeRsp.TrainType.COMPETITION.getCode().equals(trainModeReq.getType())) {
                competitionList.add(trainModeReq.getId());
            } else if (TrainModeRsp.TrainType.COURSE.getCode().equals(trainModeReq.getType())) {
                courseIdList.add(trainModeReq.getId());
            } else {
                trainIdList.add(trainModeReq.getId());
            }
        }
        if (!CollectionUtils.isEmpty(competitionList)) {
            remoteAdService.batchDelete(competitionList);
        }
        //攻防实训删除
        List<Course> courseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(courseIdList)) {
            courseList = courseService.listByIds(courseIdList);
            courseService.batchDelete(courseIdList, authContext);
        }

        //单兵/红队/蓝队/红蓝对抗/日常练习删除
        Map<String, BusinessTemplate> businessTemplateMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(trainIdList)) {
            businessTemplateService.batchDelete(trainIdList);
            List<BusinessTemplate> businessTemplateList = businessTemplateService.listByIds(trainIdList);
            businessTemplateMap = StreamApiUtils.toMap(businessTemplateList, item -> String.valueOf(item.getResourceId()), item -> item);
        }
        //根据类型分组
        Map<Integer, List<TrainModeReq>> typeListMap = StreamApiUtils.groupBy(trainModeReqList, TrainModeReq::getType);
        //根据类型插入日志
        addLogByType(courseList, businessTemplateMap, typeListMap);
        return true;
    }

    private void addLogByType(List<Course> courseList, Map<String, BusinessTemplate> businessTemplateMap, Map<Integer, List<TrainModeReq>> typeListMap) {
        for (Map.Entry<Integer, List<TrainModeReq>> entry : typeListMap.entrySet()) {
            OperationModule module = null;
            String msg = "";
            String templateName = "";
            //竞赛类型不需要插入日志
            if (TrainModeRsp.TrainType.COMPETITION.getCode().equals(entry.getKey())) {
                continue;
            }
            TrainModeRsp.TrainType type = TrainModeRsp.TrainType.getTrainTypeByCode(entry.getKey());
            switch (type) {
                case TRAINING_SINGLE:
                    module = OperationModule.SINGLE_TRAIN;
                    msg = LogContent.SINGLE_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.SINGLE_TRAIN_TEMPLATE.getMsg());
                    break;
                case TRAINING_TEAM_RED:
                    module = OperationModule.RED_TEAM;
                    msg = LogContent.RED_TEAM_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.RED_TEAM_TRAIN_TEMPLATE.getMsg());
                    break;
                case TRAINING_TEAM_BLUE:
                    module = OperationModule.BLUE_TEAM;
                    msg = LogContent.BLUE_TEAM_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.BLUE_TEAM_TRAIN_TEMPLATE.getMsg());
                    break;
                case RED_VS_BLUE:
                    module = OperationModule.RED_VS_BLUE;
                    msg = LogContent.RB_ROOM_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.RB_ROOM_TEMPLATE.getMsg());
                    break;
                case TRAINING_DAILY:
                    module = OperationModule.DAILY_TRAIN;
                    msg = LogContent.TEAM_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.TEAM_TRAIN_TEMPLATE.getMsg());
                    break;
                case COURSE:
                    module = OperationModule.COURSE;
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.COURSE_FORMAT.getMsg());
                    break;
                default:
                    break;
            }

            if (!CollectionUtils.isEmpty(entry.getValue())) {
                List<String> nameList = new ArrayList<>();
                for (TrainModeReq trainModeReq : entry.getValue()) {
                    if (TrainModeRsp.TrainType.COURSE.getCode().equals(entry.getKey())) {
                        nameList = courseList.stream().map(Course::getName).collect(Collectors.toList());
                    } else {
                        BusinessTemplate businessTemplate = businessTemplateMap.get(trainModeReq.getId());
                        if (Objects.nonNull(businessTemplate)) {
                            nameList.add(businessTemplate.getName());
                        }
                    }
                }
                remoteLogService.addOjLog(UserInfoContext.getUserContext(), module, OperationType.DELETE, templateName,
                        LogContent.CR_FORMAT.format(OperationType.DELETE, msg, nameList.toString()));
            }
        }
    }


    private List<TrainModeRsp> getCompetitionBoList(ListRsp<CalendarCompetitionBo> competitionBoRspList) {
        List<TrainModeRsp> competitionList = new ArrayList<>();
        for (CalendarCompetitionBo competitionBo : competitionBoRspList.getList()) {
            TrainModeRsp trainModeRsp = new TrainModeRsp();
            trainModeRsp.setResourceId(competitionBo.getResourceId());
            trainModeRsp.setName(competitionBo.getName());
            trainModeRsp.setDescription(competitionBo.getDesc());
            trainModeRsp.setType(TrainModeRsp.TrainType.COMPETITION.getCode());
            trainModeRsp.setCreateTime(competitionBo.getCreateTime());
            competitionList.add(trainModeRsp);
        }
        return competitionList;
    }
}