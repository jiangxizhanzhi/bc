package com.cyberpeace.cr.crbase.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.req.DailyTrainListReq;
import com.cyberpeace.cr.crbase.controller.rsp.DailyTrainRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.DailyTrainMapper;
import com.cyberpeace.cr.crbase.service.DailyTrainService;
import com.cyberpeace.integratation.service.RemoteAdService;
import com.cyberpeace.integratation.service.bo.CalendarCompetitionBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SN
 */
@Service
@Slf4j
public class DailyTrainServiceImpl implements DailyTrainService {
    @Autowired
    private DailyTrainMapper dailyTrainMapper;

    @Autowired
    private RemoteAdService remoteAdService;

    @Override
    public ListRsp<DailyTrainRsp> list(DailyTrainListReq listReq) {

        List<Integer> businessTypeList = BusinessTemplate.BusinessType.BUSINESS_TYPE_LIST;
        List<Integer> trainingTypeList = DailyTrainRsp.TrainType.TRAINING_TYPE_LIST;
        List<Integer> categoryTypeList = DailyTrainRsp.TrainCategoryType.CATEGORY_TYPE_LIST;
        if (Objects.isNull(listReq.getType())) {
            ArrayList<DailyTrainRsp> result = new ArrayList<>();

            IPage<DailyTrainRsp> dailyTrainRspList = dailyTrainMapper.listBusinessTemplate(listReq.toPageParam(), listReq.getPattern(), businessTypeList, categoryTypeList);
            IPage<DailyTrainRsp> courseList = dailyTrainMapper.listCourse(listReq.toPageParam(), listReq.getPattern());
            courseList.getRecords().forEach(item->item.setType(DailyTrainRsp.TrainType.COURSE.getCode()));

            Page newPage = new Page<>();
            newPage.setPages(1);
            newPage.setSize(10000);
            ListRsp<CalendarCompetitionBo> competitionBoList = remoteAdService.listCalendarCompetitionBo(newPage, listReq.getPattern());
            List<DailyTrainRsp> competitionList = getCompetitionBoList(competitionBoList);

            result.addAll(dailyTrainRspList.getRecords());
            result.addAll(courseList.getRecords());
            result.addAll(competitionList);
            List<DailyTrainRsp> collect = result.stream().sorted(Comparator.comparing(DailyTrainRsp::getCreateTime).reversed()).collect(Collectors.toList());
            return PageUtil.getListResult(collect, listReq.toPageParam(), result.size());

        } else if (trainingTypeList.contains(listReq.getType())) {
            // 红蓝/团队/单兵其中一种
            Integer trainCategoryType = DailyTrainRsp.TrainCategoryType.getTrainCategoryByTrainType(listReq.getType());
            IPage<DailyTrainRsp> dailyTrainRspList = dailyTrainMapper.listBusinessTemplate(listReq.toPageParam(), listReq.getPattern(), Collections.singletonList(listReq.getType()), Collections.singletonList(trainCategoryType));
            return PageUtil.getListResult(dailyTrainRspList);
        } else if (DailyTrainRsp.TrainType.COURSE.getCode().equals(listReq.getType())) {
            // 攻防实训
            IPage<DailyTrainRsp> courseList = dailyTrainMapper.listCourse(listReq.toPageParam(), listReq.getPattern());
            courseList.getRecords().forEach(item->item.setType(DailyTrainRsp.TrainType.COURSE.getCode()));
            return PageUtil.getListResult(courseList);
        } else {
            ListRsp<CalendarCompetitionBo> competitionBoRspList = remoteAdService.listCalendarCompetitionBo(listReq.toPageParam(), listReq.getPattern());
            List<DailyTrainRsp> competitionList = getCompetitionBoList(competitionBoRspList);
            ListRsp<DailyTrainRsp> listRsp = new ListRsp<>();
            listRsp.setList(competitionList);
            listRsp.setTotal(competitionBoRspList.getTotal());
            listRsp.setLastPage(competitionBoRspList.getLastPage());
            listRsp.setCurrentPage(competitionBoRspList.getCurrentPage());
            return listRsp;
        }
    }

    private List<DailyTrainRsp> getCompetitionBoList(ListRsp<CalendarCompetitionBo> competitionBoRspList) {
        List<DailyTrainRsp> competitionList = new ArrayList<>();
        for (CalendarCompetitionBo competitionBo : competitionBoRspList.getList()) {
            DailyTrainRsp dailyTrainData = new DailyTrainRsp();
            dailyTrainData.setResourceId(competitionBo.getResourceId());
            dailyTrainData.setName(competitionBo.getName());
            dailyTrainData.setDescription(competitionBo.getStartTime() + "-" + competitionBo.getEndTime());
            dailyTrainData.setType(DailyTrainRsp.TrainType.TRAINING_DAILY.getCode());
            dailyTrainData.setCreateTime(competitionBo.getStartTime());
            competitionList.add(dailyTrainData);
        }
        return competitionList;
    }
}