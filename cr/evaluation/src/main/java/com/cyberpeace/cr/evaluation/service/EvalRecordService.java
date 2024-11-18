package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

import java.util.List;
import java.util.Map;

/**
 * 测评记录Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface EvalRecordService extends IService<EvalRecord> {

    /**
     * 是否存在
     *
     * @param requestData 请求数据
     * @throws
     * @see EvalRecordAddReq
     */
    void isExist(EvalRecordAddReq requestData);

    /**
     * 获取实例对象, 检查返回值有效性
     *
     * @param resourceId 评估resourceId
     * @return {@link EvalRecord}
     * @throws
     * @see String
     * @see EvalRecord
     */
    EvalRecord getEntity(String resourceId);

    /**
     * 靶场测评实例下评估分页信息
     *
     * @param page    分页对象
     * @param taskId  测评实例id
     * @param status  状态过滤
     * @param pattern 名称模糊查询
     * @return 分页结构数据
     * @throws
     * @see Page
     * @see String
     * @see Integer
     * @see String
     * @see ListRsp<EvalRecordListRsp>
     */
    ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern);

    /**
     * 已结束测评实例创建评估任务
     *
     * @param requestData {@link EvalRecordAddReq}
     * @return {@link EvalRecordRsp}
     * @throws
     * @see EvalRecordAddReq
     * @see EvalRecordRsp
     */
    EvalRecordRsp create(EvalRecordAddReq requestData);

    /**
     * 单条评估任务信息更新
     *
     * @param requestData {@link EvalRecordAddReq}
     * @return {@link EvalRecordRsp}
     * @throws
     * @see EvalRecordAddReq
     * @see EvalRecordRsp
     */
    EvalRecordRsp update(EvalRecordAddReq requestData);

    /**
     * 单条评估任务获取
     *
     * @param recordId 评估数据resourceId
     * @return {@link EvalRecordRsp}
     * @throws
     * @see String
     * @see EvalRecordRsp
     */
    EvalRecordRsp get(String recordId);

    /**
     * 批量删除评估任务
     *
     * @param recordIds 评估数据resourceId列表
     * @return bool
     * @throws
     * @see List<String>
     * @see Boolean
     */
    Boolean delete(List<String> recordIds);

    /**
     * 测评删除, 清空评估信息
     *
     * @param taskIds 任务Ids
     * @return 是否删除成功
     * @throws
     * @see List<String>
     * @see Boolean
     */
    Boolean deleteRecord(List<String> taskIds);

    /**
     * 评估事件实际运行
     *
     * @param evalRecord 评估对象
     * @param evalTask   测评实例对象
     * @param isRestart  是否重新启动
     * @param language   语言
     * @throws
     * @see EvalRecord
     * @see EvalTask
     * @see Boolean
     * @see String
     */
    void recordRun(EvalRecord evalRecord, EvalTask evalTask, Boolean isRestart, String language);

    /**
     * 获取测评实例中场景所有节点`id`, `name`
     *
     * @param roomId 靶场测评实例id
     * @return 测评实例内节点ID、名称列表
     * @throws
     * @see String
     * @see List<EvalRoomPairRsp>
     */
    List<EvalRoomPairRsp> getNodes(String roomId);

    /**
     * 获取房间中场景某个终端使用所有探针`id`, `name`（无论是否开启探针采集）
     *
     * @param roomId 靶场测评实例id
     * @param nodeId 场景节点id
     * @return 节点探针ID、名称列表
     * @throws
     * @see String
     * @see String
     * @see List<EvalRoomPairRsp>
     */
    List<EvalRoomPairRsp> getProbes(String roomId, String nodeId);

    /**
     * 获取全量节点、探针 （Map）
     *
     * @param roomId 测评实例id
     * @return 全量节点、探针信息
     * @throws
     * @see String
     * @see Map<String, List<EvalRoomPairRsp>>
     */
    Map<String, List<EvalRoomPairRsp>> getProbes(String roomId);

    /**
     * 获取全量节点、探针（List）
     *
     * @param roomId 测评实例id
     * @return 全量节点、探针信息
     * @throws
     * @see String
     * @see List<EvalRecordNodeProbeReq>
     */
    List<EvalRecordNodeProbeReq> getNodeProbe(String roomId);

    /**
     * 获取评估结果
     *
     * @param recordId 评估id
     * @return 评估结果对象
     * @throws
     * @see String
     * @see EvalRecordResultRsp
     */
    EvalRecordResultRsp result(String recordId);

    /**
     * 获取评估状态枚举信息
     *
     * @return 评估状态枚举信息
     * @throws
     * @see List<EvalRoomIntPairRsp>
     */
    List<EvalRoomIntPairRsp> recordStatusEnum();

    /**
     * 导出测评报告pdf
     *
     * @param resourceId 评估id
     * @param taskId     测评实例id
     * @return 测评报告链接
     * @throws
     * @see String
     * @see String
     * @see String
     */
    String dumpEvalRecordPath(String resourceId, String taskId);

    /**
     * 获取全部周期结果
     *
     * @param resourceId 资源id
     * @return {@code List<EvalSystemResultRsp> }
     * @throws
     * @see String
     * @see List<EvalSystemResultRsp>
     */
    List<EvalSystemResultRsp> getAllCycleResult(String resourceId);
}
