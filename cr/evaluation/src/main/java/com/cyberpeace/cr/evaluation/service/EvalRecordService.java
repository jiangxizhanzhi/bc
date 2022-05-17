package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

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
    ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern);

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

    /**
     * 评估事件开始
     * @param recordId 评估id
     * @return bool
     */
    Boolean startRecord(String recordId);

    /**
     * 评估事件实际运行
     * @param evalRecord
     * @param evalTask
     */
    void recordRun(EvalRecord evalRecord, EvalTask evalTask);

    /**
     * 获取测评实例中场景所有节点`id`, `name`
     * @param roomId 靶场测评实例id
     * @return List
     */
    List<EvalRoomPairRsp> getNodes(String roomId);

    /**
     * 获取房间中场景某个终端使用所有探针`id`, `name`（无论是否开启探针采集）
     * @param roomId 靶场测评实例id
     * @param nodeId 场景节点id
     * @return List
     */
    List<EvalRoomPairRsp> getProbes(String roomId, String nodeId);

    /**
     * 获取评估结果
     * @param recordId 评估id
     * @return EvalRecordResultRsp
     */
    EvalRecordResultRsp result(String recordId);

    List<EvalRoomIntPairRsp> recordStatusEnum();

}
