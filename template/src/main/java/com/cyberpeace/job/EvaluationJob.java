package com.cyberpeace.job;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EvaluationJob {
    @Autowired
    private EvalTaskService evalTaskService;

    @Autowired
    private EvalRecordService evalRecordService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void checkRecord() {
        List<EvalRecord> evalRecordList = evalRecordService.lambdaQuery().eq(EvalRecord::getStatus, EvalRecord.Status.RUNNING.getCode()).list();
        log.info(String.format("Check running evaluation record, size %s.", evalRecordList.size()));

        if (!evalRecordList.isEmpty()) {
            Map<String, EvalTask> evalTaskMap = evalTaskService.lambdaQuery()
                    .in(EvalTask::getResourceId, evalRecordList.stream().map(EvalRecord::getTaskId).collect(Collectors.toList()))
                    .list()
                    .stream().collect(Collectors.toMap(EvalTask::getResourceId, evalTask -> evalTask));

            evalRecordList.forEach(evalRecord -> {
                EvalTask evalTask = evalTaskMap.get(evalRecord.getTaskId());
                if (evalRecord.getEvalTime() == null) {
                    evalRecord.setEvalTime(new Date());
                }
                evalRecordService.recordRun(evalRecord, true, WebConstant.LANGUAGE_ZH);
            });
        }

    }
}
