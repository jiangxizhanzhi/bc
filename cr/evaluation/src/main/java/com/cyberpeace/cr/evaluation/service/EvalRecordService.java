package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;

import java.util.List;

public interface EvalRecordService extends IService<EvalRecord> {

    /**
     * 靶场测评实例下评估分页信息
     * @param page 分页对象
     * @param roomId 测评实例id
     * @param status 状态过滤
     * @param pattern 名称模糊查询
     * @return 分页结构数据
     */
    ListRsp<EvalRecord> pageList(Page page, String roomId, Integer status, String pattern);

    /**
     * 已结束测评实例创建评估任务
     * @param requestData {@link EvalRecordAddReq}
     * @return {@link EvalRecordRsp}
     */
    EvalRecordRsp create(EvalRecordAddReq requestData);

    /**
     * 单条评估任务信息更新
     * @param requestData {@link EvalRecordAddReq}
     * @return {@link EvalRecordRsp}
     */
    EvalRecordRsp update(EvalRecordAddReq requestData);

    /**
     * 单条评估任务获取
     * @param recordId 评估数据resourceId
     * @return {@link EvalRecordRsp}
     */
    EvalRecordRsp get(String recordId);

    /**
     * 批量删除评估任务
     * @param recordIds 评估数据resourceId列表
     * @return bool
     */
    Boolean delete(List<String> recordIds);

    void startRecord(String recordId);

    void startVariables(List<String> variableIds);

}
