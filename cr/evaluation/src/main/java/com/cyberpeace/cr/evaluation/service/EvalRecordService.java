/**
 * 这段代码定义了一个名为 `EvalRecordService` 的接口，该接口继承了 `IService<EvalRecord>` 接口。这个接口主要用于处理与评估记录相关的各种操作和服务。以下是对其主要功能的简要分析： 
 * ### 接口概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service` 
 * - **接口名**: `EvalRecordService` 
 * - **实现**: 继承自 `IService<EvalRecord>` 
 * ### 主要方法 
 * 1. **`isExist(EvalRecordAddReq requestData)`** 
 * - **描述**: 检查某个评估记录是否存在。 
 * - **参数**: `requestData` (请求数据) 
 * 2. **`EvalRecord getEntity(String resourceId)`** 
 * - **描述**: 根据资源 ID 获取评估记录实体。 
 * - **参数**: `resourceId` (评估资源 ID) 
 * - **返回值**: `EvalRecord` 对象 
 * 3. **`ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern)`** 
 * - **描述**: 分页获取评估记录列表，可以按任务 ID、状态和名称进行过滤。 
 * - **参数**: `page` (分页对象), `taskId` (任务 ID), `status` (状态过滤), `pattern` (名称模糊查询) 
 * - **返回值**: `ListRsp<EvalRecordListRsp>` 分页结构数据 
 * 4. **`EvalRecordRsp create(EvalRecordAddReq requestData)`** 
 * - **描述**: 创建一个新的评估记录。 
 * - **参数**: `requestData` (请求数据) 
 * - **返回值**: `EvalRecordRsp` 创建后的评估记录响应对象 
 * 5. **`EvalRecordRsp update(EvalRecordAddReq requestData)`** 
 * - **描述**: 更新一个评估记录。 
 * - **参数**: `requestData` (请求数据) 
 * - **返回值**: `EvalRecordRsp` 更新后的评估记录响应对象 
 * 6. **`EvalRecordRsp get(String recordId)`** 
 * - **描述**: 根据评估记录 ID 获取单个评估记录信息。 
 * - **参数**: `recordId` (评估记录 ID) 
 * - **返回值**: `EvalRecordRsp` 评估记录响应对象 
 * 7. **`Boolean delete(List<String> recordIds)`** 
 * - **描述**: 删除多个评估记录。 
 * - **参数**: `recordIds` (评估记录 ID 列表) 
 * - **返回值**: `Boolean` 是否删除成功 
 * 8. **`Boolean deleteRecord(List<String> taskIds)`** 
 * - **描述**: 删除评估任务及其关联的评估记录。 
 * - **参数**: `taskIds` (任务 ID 列表) 
 * - **返回值**: `Boolean` 是否删除成功 
 * 9. **`void recordRun(EvalRecord evalRecord, EvalTask evalTask, Boolean isRestart, String language)`** 
 * - **描述**: 运行评估任务。 
 * - **参数**: `evalRecord` (评估记录), `evalTask` (评估任务), `isRestart` (是否重新启动), `language` (语言) 
 * 10. **`List<EvalRoomPairRsp> getNodes(String roomId)`** 
 * - **描述**: 获取评估实例中的所有节点 ID 和名称。 
 * - **参数**: `roomId` (评估实例 ID) 
 * - **返回值**: `List<EvalRoomPairRsp>` 节点 ID 和名称列表 
 * 11. **`List<EvalRoomPairRsp> getProbes(String roomId, String nodeId)`** 
 * - **描述**: 获取指定节点的所有探针 ID 和名称。 
 * - **参数**: `roomId` (评估实例 ID), `nodeId` (节点 ID) 
 * - **返回值**: `List<EvalRoomPairRsp>` 探针 ID 和名称列表 
 * 12. **`Map<String, List<EvalRoomPairRsp>> getProbes(String roomId)`** 
 * - **描述**: 获取评估实例中所有节点及其探针的信息。 
 * - **参数**: `roomId` (评估实例 ID) 
 * - **返回值**: `Map<String, List<EvalRoomPairRsp>>` 节点 ID 及其探针信息 
 * 13. **`List<EvalRecordNodeProbeReq> getNodeProbe(String roomId)`** 
 * - **描述**: 获取评估实例中所有节点及其探针的信息。 
 * - **参数**: `roomId` (评估实例 ID) 
 * - **返回值**: `List<EvalRecordNodeProbeReq>` 节点及其探针信息 
 * 14. **`EvalRecordResultRsp result(String recordId)`** 
 * - **描述**: 获取评估结果。 
 * - **参数**: `recordId` (评估记录 ID) 
 * - **返回值**: `EvalRecordResultRsp` 评估结果对象 
 * 15. **`List<EvalRoomIntPairRsp> recordStatusEnum()`** 
 * - **描述**: 获取评估状态枚举信息。 
 * - **返回值**: `List<EvalRoomIntPairRsp>` 评估状态枚举信息 
 * 16. **`String dumpEvalRecordPath(String resourceId, String taskId)`** 
 * - **描述**: 导出评估报告 PDF。 
 * - **参数**: `resourceId` (评估 ID), `taskId` (任务 ID) 
 * - **返回值**: `String` 测评报告链接 
 * 17. **`List<EvalSystemResultRsp> getAllCycleResult(String resourceId)`** 
 * - **描述**: 获取所有周期结果。 
 * - **参数**: `resourceId` (评估 ID) 
 * - **返回值**: `List<EvalSystemResultRsp>` 所有周期结果 
 * ### 总结 
 * `EvalRecordService` 接口提供了全面的评估记录管理功能，包括创建、更新、删除、查询以及导出评估报告等。这些方法覆盖了评估记录生命周期的各个方面，并且通过丰富的参数支持了灵活的查询和操作需求。 
**/
package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalRecordAddReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;

import java.util.List;
import java.util.Map;

/**
 * 评估记录Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalRecordService extends IService<EvalRecord> {

    /**
     * 存在
     *
     * @param requestData 请求数据
     */
    void isExist(EvalRecordAddReq requestData);

    /**
     * 获取实例对象, 检查返回值有效性
     *
     * @param resourceId 评估resourceId
     * @return {@link EvalRecord}
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
     */
    ListRsp<EvalRecordListRsp> pageList(Page page, String taskId, Integer status, String pattern);

    /**
     * 已结束测评实例创建评估任务
     *
     * @param requestData {@link EvalRecordAddReq}
     * @return {@link EvalRecordRsp}
     */
    EvalRecordRsp create(EvalRecordAddReq requestData);

    /**
     * 单条评估任务信息更新
     *
     * @param requestData {@link EvalRecordAddReq}
     * @return {@link EvalRecordRsp}
     */
    EvalRecordRsp update(EvalRecordAddReq requestData);

    /**
     * 单条评估任务获取
     *
     * @param recordId 评估数据resourceId
     * @return {@link EvalRecordRsp}
     */
    EvalRecordRsp get(String recordId);

    /**
     * 批量删除评估任务
     *
     * @param recordIds 评估数据resourceId列表
     * @return bool
     */
    Boolean delete(List<String> recordIds);

    /**
     * 测评删除, 清空评估信息
     *
     * @param taskIds 任务Ids
     * @return 是否删除成功
     */
    Boolean deleteRecord(List<String> taskIds);

    /**
     * 评估事件实际运行
     *
     * @param evalRecord 评估对象
     * @param evalTask   测评实例对象
     * @param isRestart  是重新启动
     * @param language   语言
     */
    void recordRun(EvalRecord evalRecord, EvalTask evalTask, Boolean isRestart, String language);

    /**
     * 获取测评实例中场景所有节点`id`, `name`
     *
     * @param roomId 靶场测评实例id
     * @return 测评实例内节点ID、名称列表
     */
    List<EvalRoomPairRsp> getNodes(String roomId);

    /**
     * 获取房间中场景某个终端使用所有探针`id`, `name`（无论是否开启探针采集）
     *
     * @param roomId 靶场测评实例id
     * @param nodeId 场景节点id
     * @return 节点探针ID、名称列表
     */
    List<EvalRoomPairRsp> getProbes(String roomId, String nodeId);

    /**
     * 获取全量节点、探针 （Map）
     *
     * @param roomId 测评实例id
     * @return 全量节点、探针信息
     */
    Map<String, List<EvalRoomPairRsp>> getProbes(String roomId);

    /**
     * 获取全量节点、探针（List）
     *
     * @param roomId 测评实例id
     * @return 全量节点、探针信息
     */
    List<EvalRecordNodeProbeReq> getNodeProbe(String roomId);

    /**
     * 获取评估结果
     *
     * @param recordId 评估id
     * @return 评估结果对象
     */
    EvalRecordResultRsp result(String recordId);

    /**
     * 获取评估状态枚举信息
     * @return 评估状态枚举信息
     */
    List<EvalRoomIntPairRsp> recordStatusEnum();

    /**
     * 导出测评报告pdf
     * @param resourceId 评估id
     * @param taskId 测评实例id
     * @return 测评报告链接
     */
    String dumpEvalRecordPath(String resourceId, String taskId);

    List<EvalSystemResultRsp> getAllCycleResult(String resourceId);
}
