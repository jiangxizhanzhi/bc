package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.bo.RecordDumpLoadBo;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalExportRecordMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexRefMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalVariableRefMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexRefService;
import com.cyberpeace.cr.evaluation.service.EvalRecordService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalVariableRefService;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.util.*;

import static com.cyberpeace.cr.crbase.utils.FileMD5Checksum.getFileMD5Checksum;

@Api(tags = "靶场测评-评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/record")
@Slf4j
public class EvalRecordController {

    @Autowired
    private EvalRecordService evalRecordService;

    @Autowired
    private EvalIndexRefService evalIndexRefService;

    @Autowired
    private EvalVariableRefService evalVariableRefService;

    @Autowired
    private EvalTemplateService evalTemplateService;

    @Autowired
    private EvalIndexRefMapper evalIndexRefMapper;

    @Autowired
    private EvalVariableRefMapper evalVariableRefMapper;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private UtilService utilService;

    @Autowired
    private EvalExportRecordMapper evalExportRecordMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @ApiOperation("获取靶场测评实例评估（分页）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/list")
    public BaseRsp<ListRsp<EvalRecordListRsp>> list(EvalRecordListReq queryParams) {
        return new BaseRsp<>(evalRecordService.pageList(
                queryParams.toPageParam(),
                queryParams.getTaskId(),
                queryParams.getStatus(),
                queryParams.getPattern()
        ));
    }

    @ApiOperation("评估状态枚举（查询用）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/status")
    public BaseRsp<List<EvalRoomIntPairRsp>> statusEnum() {
        return new BaseRsp<>(evalRecordService.recordStatusEnum());
    }

    @ApiOperation("创建评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("")
    public BaseRsp<EvalRecordRsp> create(@RequestBody EvalRecordAddReq requestData) {
        requestData.validate();
        return new BaseRsp<>(evalRecordService.create(requestData));
    }

    @ApiOperation("更新评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PatchMapping("")
    public BaseRsp<EvalRecordRsp> update(@RequestBody EvalRecordAddReq requestData) {
        requestData.validate();
        return new BaseRsp<>(evalRecordService.update(requestData));
    }

    @ApiOperation("获取单个评估")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("")
    public BaseRsp<EvalRecordRsp> get(@ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.get(recordId));
    }

    @ApiOperation("评估任务删除")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @DeleteMapping("")
    public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq requestData) {
        return new BaseRsp<>(evalRecordService.delete(requestData.getIds()));
    }

    @ApiOperation("场景终端节点获取")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/nodes")
    public BaseRsp<List<EvalRoomPairRsp>> getNodes(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodes(roomId));
    }

    @ApiOperation("终端使用探针信息获取")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes")
    public BaseRsp<List<EvalRoomPairRsp>> getProbes(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId,
                                                    @ApiParam(name = "node_id", value = "场景节点ID") @RequestParam("node_id") String nodeId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId, nodeId));
    }

    @ApiOperation("终端使用探针信息获取-批量")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes-map")
    public BaseRsp<Map<String, List<EvalRoomPairRsp>>> getProbes(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getProbes(roomId));
    }

    @ApiOperation("场景使用终端/探针")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/probes/all")
    public BaseRsp<List<EvalRecordNodeProbeReq>> getNodeProbe(@ApiParam(name = "room_id", value = "测评任务ID") @RequestParam("room_id") String roomId) {
        return new BaseRsp<>(evalRecordService.getNodeProbe(roomId));
    }

    @ApiOperation("评估任务开启")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("/start")
    public BaseRsp<Boolean> start(@RequestBody EvalRecordStartReq requestData) {
        // 评估对象
        EvalRecord evalRecord = evalRecordService.getEntity(requestData.getResourceId());

        // 设置评估状态、开始时间
        if (evalRecord.getStatus().equals(EvalRecord.Status.NOT_START.getCode())) {
            evalRecord.setStatus(EvalRecord.Status.RUNNING.getCode());
        }
        evalRecord.setEvalTime(new Date());
        boolean result = evalRecordService.updateById(evalRecord);

        // 开始执行
        log.info(String.format("Range Test evaluation %s START.", evalRecord.getName()));
        evalRecordService.recordRun(evalRecord, false, CommonContext.getLanguage());

        return new BaseRsp<>(result);
    }

    @ApiOperation("评估任务结果")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result")
    public BaseRsp<EvalRecordResultRsp> result(@ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String recordId) {
        return new BaseRsp<>(evalRecordService.result(recordId));
    }

    @ApiOperation("获取某一级指标项下全部变量项")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables")
    public BaseRsp<List<EvalRoomPairRsp>> variables(@ApiParam(name = "index_id", value = "指标项ID") @RequestParam("index_id") String indexId) {
        return new BaseRsp<>(evalIndexRefService.selectVariables(indexId));
    }

    @ApiOperation("变量项采集信息（折线图）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables/graph")
    public BaseRsp<EvalVariableGraphRsp> variableGraph(@ApiParam(name = "variable_id", value = "变量项ID") @RequestParam("variable_id") String variableId) {
        return new BaseRsp<>(evalVariableRefService.graph(variableId));
    }

    @ApiOperation("变量项采集信息（原始数据）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/variables/data")
    public BaseRsp<ListRsp<EvalVariableDataPageRsp>> variableData(EvalVariableProbeDataReq queryParams) {
        return new BaseRsp<>(evalVariableRefService.originData(queryParams.toPageParam(), queryParams.getVariableId()));
    }

    @ApiOperation("评估任务结果（指标项信息）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result/index")
    public BaseRsp<EvalRecordResultIndexRsp> resultIndex(@ApiParam(name = "index_id", value = "指标项ID") @RequestParam("index_id") String indexId) {
        return new BaseRsp<>(evalIndexRefService.resultIndex(indexId));
    }

    @ApiOperation("评估任务结果（变量项信息）")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/result/variable")
    public BaseRsp<EvalRecordResultVariableRsp> resultVariable(@ApiParam(name = "variable_id", value = "变量项ID") @RequestParam("variable_id") String variableId) {
        return new BaseRsp<>(evalVariableRefService.resultVariable(variableId));
    }

    @ApiOperation("评估详情导出为PDF")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/train2pdf")
    public void dumpEvalRecord2PDF(HttpServletResponse httpServletResponse,
                                   @ApiParam(name = "resource_id", value = "评估ID") @RequestParam("resource_id") String resourceId,
                                   @ApiParam(name = "task_id", value = "测评任务ID") @RequestParam("task_id")String taskId) {
        FileUtil.setResponse(new File(evalRecordService.dumpEvalRecordPath(resourceId, taskId)), true, httpServletResponse);
    }

    @ApiOperation("导入")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @PostMapping("/import")
    public BaseRsp<Boolean> importRecord(@Validated LoadRecordReq loadRecordReq) {
        AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.OTHER.getCode(),
                false, loadRecordReq.getFile(), true);
        String realPath = utilService.getRealPath(attachmentBo.getFilePath());
        String md5sum = getFileMD5Checksum(realPath);

        String content = FileUtil.getContent(new File(realPath));
        RecordDumpLoadBo recordDumpLoadBo = JsonUtil.toJsonObject(content,
                RecordDumpLoadBo.class);
        List<EvalIndexRef> indexRefs = recordDumpLoadBo.getIndexRefs();
        List<EvalVariableRef> variableRefs = recordDumpLoadBo.getVariableRefs();

        String recordName = recordDumpLoadBo.getName();
        LambdaQueryWrapper<EvalRecord> queryRecord = new LambdaQueryWrapper<>();
        queryRecord.eq(EvalRecord::getName, recordName);
        EvalRecord evalRecord = evalRecordService.getBaseMapper().selectOne(queryRecord);
        if(null == evalRecord){
            BeanUtils.copyProperties(recordDumpLoadBo, evalRecord);
        } else {
            recordName += "（1）";
        }
        evalRecord.setName(recordName);
        String recordId = IDGeneratorUtil.getID();
        evalRecord.setResourceId(recordId);
        Map<String, String> variableMap = new HashMap<>();
        Map<String, String> indexMap = new HashMap<>();
        variableRefs.forEach(variableRef -> {
            variableRef.setId(null);
            variableRef.setRecordId(recordId);
            String resourceId = variableRef.getResourceId();
            String id = IDGeneratorUtil.getID();
            variableRef.setResourceId(id);
            variableMap.put(resourceId, id);
        });

        indexRefs.forEach(evalIndexRef -> {
            evalIndexRef.setId(null);
            evalIndexRef.setRecordId(recordId);
            String nextIds = evalIndexRef.getNextIds();
            List<String> ids = JsonUtil.toJsonObjectArray(nextIds, String.class);
            String replaceId;
            for(String id:ids){
                if(variableMap.containsKey(id)){
                    replaceId = variableMap.get(id);
                    nextIds = nextIds.replaceAll(id, replaceId);
                } else if(indexMap.containsKey(id)){
                    replaceId = indexMap.get(id);
                    nextIds = nextIds.replaceAll(id, replaceId);
                }

            }
            evalIndexRef.setNextIds(nextIds);
            String resourceId = evalIndexRef.getResourceId();
            String id = IDGeneratorUtil.getID();
            evalIndexRef.setResourceId(id);
            indexMap.put(resourceId, id);

        });

        evalRecord.setId(null);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalRecordService.getBaseMapper().insert(evalRecord);
                evalIndexRefService.saveBatch(indexRefs);
                evalVariableRefService.saveBatch(variableRefs);
            }
        });

        return new BaseRsp<>(Boolean.TRUE);
    }

    @ApiOperation("导出")
    @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
    @GetMapping("/export")
    public BaseRsp<Boolean> exportRecord(@ApiParam(name = "id",
            value = "评估记录编号") @RequestParam("id") @NotEmpty String resourceId,
                                         HttpServletResponse response) {
        EvalRecord evalRecord = evalRecordService.getById(resourceId);
        if(null == evalRecord){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        EvalTemplate evalTemplate = evalTemplateService.getById(evalRecord.getTemplateId());
        String redVsBlueId = evalTemplate.getRedVsBlueId();
        // 体系包含数据查询
        List<EvalIndexRef> evalIndexRefs = getIndexRefs(resourceId);
        List<EvalVariableRef> variableRefs = getVariableRefs(resourceId);

        // 5级指标项绑定节点及人工评分查询
        RecordDumpLoadBo recordDumpLoadBo = new RecordDumpLoadBo();
        BeanUtils.copyProperties(evalRecord, recordDumpLoadBo);
        recordDumpLoadBo.setRbId(redVsBlueId);
        recordDumpLoadBo.setIndexRefs(evalIndexRefs);
        recordDumpLoadBo.setVariableRefs(variableRefs);

        String recordStr = JsonUtil.toJsonString(recordDumpLoadBo);
        String dataPath = AttachmentPath.OTHER.getDesc() + IDGeneratorUtil.getID() + File.separatorChar + CrCommonConstants.RESOURCE_FILE_NAME;

        // 导出文件记录md5sum存入表中
        String md5sum = getFileMD5Checksum(dataPath);
        EvalExportRecord evalExportRecord = new EvalExportRecord();
        evalExportRecord.setRecord(evalRecord.getResourceId());
        evalExportRecord.setExportFileMd5(md5sum);
        LambdaQueryWrapper<EvalExportRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EvalExportRecord::getRecord, evalRecord.getResourceId());
        EvalExportRecord exportRecord = evalExportRecordMapper.selectOne(queryWrapper);
        if(null == exportRecord){
            evalExportRecordMapper.insert(evalExportRecord);
        } else {
            exportRecord.setExportFileMd5(md5sum);
            evalExportRecordMapper.updateById(exportRecord);
        }

        FileUtil.writeToDestFile(recordStr, dataPath);
        FileUtil.setResponse(new File(dataPath), false, response);
        return new BaseRsp(Boolean.TRUE);
    }

    private List<EvalVariableRef> getVariableRefs(String resourceId) {
        LambdaQueryWrapper<EvalVariableRef> queryVariable = new LambdaQueryWrapper<>();
        queryVariable.eq(EvalVariableRef::getRecordId, resourceId);
        return evalVariableRefMapper.selectList(queryVariable);
    }

    private List<EvalIndexRef> getIndexRefs(String resourceId) {
        LambdaQueryWrapper<EvalIndexRef> queryIndex = new LambdaQueryWrapper<>();
        queryIndex.eq(EvalIndexRef::getRecordId, resourceId);
        return evalIndexRefMapper.selectList(queryIndex);
    }

    public <S, T> List<T> convertList(List<S> sourceList, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        sourceList.forEach(source -> {
            try {
                T target = targetClass.getDeclaredConstructor().newInstance();
                BeanUtils.copyProperties(source, target);
                targetList.add(target);
            } catch (Exception e) {
                // 处理异常
            }
        });
        return targetList;
    }

}
