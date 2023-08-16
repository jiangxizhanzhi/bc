package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.TrainModeListReq;
import com.cyberpeace.cr.crbase.controller.req.TrainModeReq;
import com.cyberpeace.cr.crbase.controller.rsp.TrainModeRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.TrainModeMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.oj.course.service.TrainingModeService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.service.CourseService;
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

    @Override
    public ListRsp<TrainModeRsp> list(TrainModeListReq listReq) {

        List<Integer> businessTypeList = BusinessTemplate.BusinessType.BUSINESS_TYPE_LIST;
        List<Integer> trainingTypeList = TrainModeRsp.TrainType.TRAINING_TYPE_LIST;
        List<Integer> categoryTypeList = TrainModeRsp.TrainCategoryType.CATEGORY_TYPE_LIST;
        if (Objects.isNull(listReq.getType())) {
            ArrayList<TrainModeRsp> result = new ArrayList<>();

            IPage<TrainModeRsp> trainModeRspPage = trainModeMapper.listBusinessTemplate(listReq.toPageParam(), listReq.getPattern(), businessTypeList, categoryTypeList);
            IPage<TrainModeRsp> courseList = trainModeMapper.listCourse(listReq.toPageParam(), listReq.getPattern());
            courseList.getRecords().forEach(item->item.setType(TrainModeRsp.TrainType.COURSE.getCode()));

            Page newPage = new Page<>();
            newPage.setPages(1);
            newPage.setSize(10000);
            ListRsp<CalendarCompetitionBo> competitionBoList = remoteAdService.listCalendarCompetitionBo(newPage, listReq.getPattern());
            List<TrainModeRsp> competitionList = getCompetitionBoList(competitionBoList);

            result.addAll(trainModeRspPage.getRecords());
            result.addAll(courseList.getRecords());
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
            // 攻防实训
            IPage<TrainModeRsp> courseList = trainModeMapper.listCourse(listReq.toPageParam(), listReq.getPattern());
            courseList.getRecords().forEach(item->item.setType(TrainModeRsp.TrainType.COURSE.getCode()));
            return PageUtil.getListResult(courseList);
        } else {
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

    @Override
    public Boolean delete(List<TrainModeReq> trainModeReqList) {
        if (CollectionUtils.isEmpty(trainModeReqList)) {
            return false;
        }
        AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),false);

        List<TrainModeReq> competitionList = new ArrayList<>();
        List<String> trainIdList = new ArrayList<>();
        List<String> courseIdList = new ArrayList<>();
        for (TrainModeReq trainModeReq : trainModeReqList) {
            if (TrainModeRsp.TrainType.COMPETITION.getCode().equals(trainModeReq.getType())) {
                competitionList.add(trainModeReq);
            } else if (TrainModeRsp.TrainType.COURSE.getCode().equals(trainModeReq.getType())) {
                courseIdList.add(trainModeReq.getId());
            } else {
                trainIdList.add(trainModeReq.getId());
            }
        }
        //todo 删除竞赛成功,删除其他类型

        //攻防实训删除
        List<Course> courseList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(courseIdList)) {
            courseList = courseService.listByIds(courseIdList);
            courseService.batchDelete(courseIdList, authContext);
        }

        //单兵/红队/蓝队/红蓝对抗/日常练习删除
        Map<String, BusinessTemplate> businessTemplateMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(trainIdList)){
            businessTemplateService.batchDelete(trainIdList);
            List<BusinessTemplate> businessTemplateList = businessTemplateService.listByIds(trainIdList);
            businessTemplateMap = StreamApiUtils.toMap(businessTemplateList, item-> String.valueOf(item.getResourceId()) , item -> item);
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
            switch (type){
                case TRAINING_SINGLE :
                    module = OperationModule.SINGLE_TRAIN;
                    msg = LogContent.SINGLE_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.SINGLE_TRAIN_TEMPLATE.getMsg());
                    break;
                case TRAINING_TEAM_RED :
                    module = OperationModule.RED_TEAM;
                    msg = LogContent.RED_TEAM_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.RED_TEAM_TRAIN_TEMPLATE.getMsg());
                    break;
                case TRAINING_TEAM_BLUE :
                    module = OperationModule.BLUE_TEAM;
                    msg = LogContent.BLUE_TEAM_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.BLUE_TEAM_TRAIN_TEMPLATE.getMsg());
                    break;
                case RED_VS_BLUE :
                    module = OperationModule.RED_VS_BLUE;
                    msg = LogContent.RB_ROOM_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.RB_ROOM_TEMPLATE.getMsg());
                    break;
                case TRAINING_DAILY :
                    module = OperationModule.DAILY_TRAIN;
                    msg = LogContent.TEAM_TRAIN_TEMPLATE.getMsg();
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.TEAM_TRAIN_TEMPLATE.getMsg());
                    break;
                case COURSE :
                    module = OperationModule.COURSE;
                    templateName = LogContent.BATCH_DELETE_FORMAT.format(LogContent.COURSE_FORMAT.getMsg());
                    break;
                default:
                    break;
            }

            if (!CollectionUtils.isEmpty(entry.getValue())) {
                List<String> nameList = new ArrayList<>();
                for (TrainModeReq trainModeReq : entry.getValue()) {
                    if (TrainModeRsp.TrainType.COURSE.getCode().equals(entry.getKey()) ){
                        nameList = courseList.stream().map(Course::getName).collect(Collectors.toList());
                    }else {
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
            trainModeRsp.setDescription(competitionBo.getStartTime() + "-" + competitionBo.getEndTime());
            trainModeRsp.setType(TrainModeRsp.TrainType.TRAINING_DAILY.getCode());
            trainModeRsp.setCreateTime(competitionBo.getStartTime());
            competitionList.add(trainModeRsp);
        }
        return competitionList;
    }
}