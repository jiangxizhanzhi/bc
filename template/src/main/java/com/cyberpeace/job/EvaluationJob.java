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

/**
 * 评估任务
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class EvaluationJob {
    /**
     * 测评任务Service
     *
     * @see EvalTaskService
     */
    @Autowired
    private EvalTaskService evalTaskService;

    /**
     * 测评记录Service
     *
     * @see EvalRecordService
     */
    @Autowired
    private EvalRecordService evalRecordService;

    /**
     * 检查记录
     *
     * @throws
     */
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
                evalRecordService.recordRun(evalRecord, evalTask, true, WebConstant.LANGUAGE_ZH);
            });
        }

    }
}
