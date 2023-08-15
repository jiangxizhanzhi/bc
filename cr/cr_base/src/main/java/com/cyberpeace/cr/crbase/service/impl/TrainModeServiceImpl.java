package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.TrainModeListReq;
import com.cyberpeace.cr.crbase.controller.req.TrainModeReq;
import com.cyberpeace.cr.crbase.controller.rsp.TrainModeRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.TrainModeMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.TrainingModeService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
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
    private TransactionTemplate transactionTemplate;

    @Autowired
    @Qualifier("businessTemplateHandler")
    private BusinessTemplateHandler businessTemplateHandler;

    @Autowired
    private LocalSceneService localSceneService;


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
            IPage<TrainModeRsp> trainModeRspPage = trainModeMapper.listBusinessTemplate(listReq.toPageParam(), listReq.getPattern(), Collections.singletonList(businessType), Collections.singletonList(trainCategoryType));
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
        if (CollectionUtils.isEmpty(trainModeReqList)){
            return  false;
        }

        List<TrainModeReq> competitionList = new ArrayList<>();
        for (TrainModeReq trainModeReq : trainModeReqList) {
            if (TrainModeRsp.TrainType.COMPETITION.getCode().equals(trainModeReq.getType())) {
                competitionList.add(trainModeReq);
            }
        }
        //todo 删除竞赛成功,删除其他类型

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

//                List<String> trainIds = new ArrayList<>();

                trainModeReqList.removeAll(competitionList);
                for (TrainModeReq trainModeReq : trainModeReqList) {
                    // 校验是否符合删除条件
//                    BusinessTemplateHandler handler = businessTemplateHandler
//                            .getHandler(businessTemplate.getBusinessType());
//                    handler.checkBeforeDel(businessTemplate);
//                    // 先删除测试场景
//                    if (StringUtils.isNotBlank(businessTemplate.getLocalSceneId())) {
//                        LocalScene localScene = localSceneService
//                                .getById(businessTemplate.getLocalSceneId());
//
//                        if (localScene != null
//                                && StringUtils.isNotBlank(localScene.getMpSceneId())) {
//                            try {
//                                localSceneService.deleteRemoteScene(localScene);
//                            } catch (Exception e) {
//                                log.error(String.format(
//                                        "failed to delete test scene [%s] of business template [%s]",
//                                        localScene.getMpSceneId(),
//                                        businessTemplate.getResourceId()));
//                                // do nothing
//                            }
//                        }
//                        // 删除测试场景的localScene
//                        localSceneService.removeById(businessTemplate.getLocalSceneId());
//                    }
//                    // 删除中台拓扑
//                    try {
//                        localSceneService.deleteTopo(businessTemplate.getTopoId());
//                    } catch (Exception e) {
//                        log.error(String.format(
//                                "failed to delete topo [%s] of business template [%s]",
//                                businessTemplate.getTopoId(), businessTemplate.getResourceId()));
//                        // do nothing
//                    }
//                    // 删除模板相关其他
//                    handler.deleteTemplateRelated(businessTemplate);
                }

            }
        });


        return null;
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