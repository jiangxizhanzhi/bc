package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.ManualExecutionReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

public interface EvalMissionService extends IService<EvalMissionTemplate> {

    void executeCommand(ManualExecutionReq manualExecutionReq, EvalTask evalTask);
}
