package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplateLog;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateLogMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "靶场测评-前台-任务")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalMissionController {

    @Autowired
    private EvalMissionTemplateLogMapper evalMissionTemplateLogMapper;

    @ApiOperation("查看任务执行结果")
    @GetMapping("/mission/result")
    public BaseRsp<String> getMissionResult(@ApiParam(name = "mission_id", value = "任务id", required = true) @RequestParam("mission_id") String missionId,
                                            @ApiParam(name = "task_id", value = "测评任务id", required = true) @RequestParam("task_id") String taskId) {
        Map<String, Object> map = new HashMap<>();
        map.put("mission_id", missionId);
        map.put("task_id", taskId);
        List<EvalMissionTemplateLog> evalMissionTemplateLogs = evalMissionTemplateLogMapper.selectByMap(map);

        String result = null;
        if (!evalMissionTemplateLogs.isEmpty()) {
            for (EvalMissionTemplateLog evalMissionTemplateLog : evalMissionTemplateLogs) {
                if (StringUtils.isNotEmpty(evalMissionTemplateLog.getMissionResult())) {
                    result = evalMissionTemplateLog.getMissionResult();
                    break;
                }
            }
        }
        return new BaseRsp<>(result);
    }


}