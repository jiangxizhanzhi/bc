package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultVariableRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableGraphRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 测评变量裁判Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface EvalVariableRefService extends IService<EvalVariableRef> {

    /**
     * 时间字段获取存在差异的探针
     *
     * @see List<String>
     */
    List<String> BASE = Arrays.asList("002", "003");

    /**
     * 由es查询的探针数据, 无法使用`page`、`per_page`分页, 需要传入`requestId`、`per_page`迭代获取
     *
     * @see List<String>
     */
    List<String> ES_PROBES = Arrays.asList("006", "007", "008", "009", "010");

    /**
     * 采集变量项目算法执行
     *
     * @param evalVariableRef     变量项对象
     * @param evalAlgorithmRefMap 评估使用算法Map
     * @param evalRecord          评估对象
     * @param evalTask            测评实例对象
     * @return {@code String }
     * @throws
     * @see EvalVariableRef
     * @see Map<String, EvalAlgorithmRef>
     * @see EvalRecord
     * @see EvalTask
     * @see String
     */
    String startVariableAlgorithm(EvalVariableRef evalVariableRef,
                                  Map<String, EvalAlgorithmRef> evalAlgorithmRefMap,
                                  EvalRecord evalRecord,
                                  EvalTask evalTask);

    /**
     * 获取变量项结果折线图
     *
     * @param variableId 变量项id
     * @return 变量项目折线图信息
     * @throws
     * @see String
     * @see EvalVariableGraphRsp
     */
    EvalVariableGraphRsp graph(String variableId);

    /**
     * 获取变量项探针原始数据
     *
     * @param page       分页信息
     * @param variableId 变量项id
     * @return 探针原始数据分页信息
     * @throws
     * @see Page
     * @see String
     * @see ListRsp<EvalVariableDataPageRsp>
     */
    ListRsp<EvalVariableDataPageRsp> originData(Page page, String variableId);

    /**
     * 获取评估结果变量项信息
     *
     * @param variableId 变量项id
     * @return 评估结果变量项信息
     * @throws
     * @see String
     * @see EvalRecordResultVariableRsp
     */
    EvalRecordResultVariableRsp resultVariable(String variableId);
}
