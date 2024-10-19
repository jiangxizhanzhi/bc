package com.cyberpeace.oj.person.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.person.controller.req.KnowledgeReq;
import com.cyberpeace.oj.person.service.bo.StatisticsBo;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@Api(tags = "oj-统计api")
@RestController
@RequestMapping("/oj/statistics")
public class StatisticsController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private PracticeTheoryMapper practiceTheoryMapper;

    @Autowired
    private PracticeOperationMapper practiceOperationMapper;

    @ApiOperation("知识空间统计")
    @GetMapping("/")
    public BaseRsp<KnowledgeReq> listComments() {
        KnowledgeReq knowledgeReq = new KnowledgeReq();
        //课程
        List<String> timeList = courseMapper.selectTime();
        knowledgeReq.setCourse(statistics(timeList));

        //理论
        timeList = practiceTheoryMapper.selectTime();
        knowledgeReq.setTheory(statistics(timeList));

        //实操
        timeList = practiceOperationMapper.selectTime();
        knowledgeReq.setOperate(statistics(timeList));

        return new BaseRsp<>(knowledgeReq);
    }

    private StatisticsBo statistics(List<String> timeList) {
        String thisMonth = TimeUtil.format11(new Date());
        String lastMonth = TimeUtil.format11(TimeUtil.addMonths(new Date(), -1));
        StatisticsBo statistics = new StatisticsBo();
        statistics.setTotal(timeList.size());
        statistics.setThisAdd((int) timeList.stream().filter(time -> thisMonth.equals(time)).count());
        statistics.setLastAdd((int) timeList.stream().filter(time -> lastMonth.equals(time)).count());
        return statistics;
    }
}
