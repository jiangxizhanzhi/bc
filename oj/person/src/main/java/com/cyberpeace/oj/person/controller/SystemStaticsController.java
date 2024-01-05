package com.cyberpeace.oj.person.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyberpeace.auth.controller.req.UserReq;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.UserRes;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.person.controller.res.SystemRecordRes;
import com.cyberpeace.oj.person.controller.res.SystemStaticsRes;
import com.cyberpeace.auth.service.bo.OpLog;
import com.cyberpeace.auth.service.OpLogService;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.entity.PracticeOperation;
import com.cyberpeace.oj.practice.service.PracticeOperationService;
import com.cyberpeace.oj.practice.service.PracticeTheoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Api(tags = "oj-系统总览")
@RestController
@RequestMapping("/oj/system/statics")
public class SystemStaticsController {

    @Autowired
    private OpLogService opLogService;
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseLessonService courseLessonService;

    @Autowired
    private PracticeTheoryService practiceTheoryService;
    @Autowired
    private PracticeOperationService practiceOperationService;
    @ApiOperation("数据统计")
    @GetMapping("/count_statics")
    public BaseRsp<SystemStaticsRes> countStatics() {
        SystemStaticsRes systemStaticsRes = new SystemStaticsRes();
        systemStaticsRes.setPersonCount(sysUserService.count());
        systemStaticsRes.setCourseCount(courseService.count());
        systemStaticsRes.setLessonCount(courseLessonService.count());
        //TODO 真卷
        systemStaticsRes.setTruePaperCount(sysUserService.count());
        //TODO 还差运维题
        QueryWrapper<PracticeOperation> operationQueryWrapper = new QueryWrapper<>();
        operationQueryWrapper.lambda().eq(PracticeOperation::getIsTrue,1);
        QueryWrapper<PracticeTheory> theoryQueryWrapper = new QueryWrapper<>();
        theoryQueryWrapper.lambda().eq(PracticeTheory::getIsTrue,1);
        systemStaticsRes.setTrueCount(practiceTheoryService.count(theoryQueryWrapper)+practiceOperationService.count(operationQueryWrapper));
        systemStaticsRes.setPracticeCount(practiceTheoryService.count()+practiceOperationService.count());

        return new BaseRsp<>(systemStaticsRes);
    }
    @ApiOperation("学员行为记录")
    @GetMapping("/record/page")
    public BaseRsp<ListRsp<SystemRecordRes>> page(UserReq req) {
        ListRsp<UserRes> userResListRsp = sysUserService.pageUser(req);
        List<SystemRecordRes> list =new ArrayList<>();
        List<String> userIdList = userResListRsp.getList().stream().map(UserRes::getResourceId).collect(Collectors.toList());
        //根据createTime获取用户最小一次记录
        List<OpLog> opLogList = opLogService.listLastRecord(userIdList);
        Map<String, OpLog> opLogMap = opLogList.stream().collect(Collectors.toMap(OpLog::getCreateUserId, Function.identity()));
        for (UserRes userRes : userResListRsp.getList()) {
            SystemRecordRes systemRecordRes = new SystemRecordRes();
            systemRecordRes.setUsername(userRes.getUsername());
            systemRecordRes.setTime(userRes.getOnlineTime());
            systemRecordRes.setName(userRes.getName());
            systemRecordRes.setCreateTime(opLogMap.getOrDefault(userRes.getResourceId(),new OpLog()).getCreateTime());
            systemRecordRes.setTarget(opLogMap.getOrDefault(userRes.getResourceId(),new OpLog()).getTarget());
            systemRecordRes.setMethod(opLogMap.getOrDefault(userRes.getResourceId(),new OpLog()).getMethod());
            list.add(systemRecordRes);
        }
        return new BaseRsp<>(new ListRsp<>(list, userResListRsp.getTotal(), userResListRsp.getLastPage(), userResListRsp.getCurrentPage()));
    }


}
