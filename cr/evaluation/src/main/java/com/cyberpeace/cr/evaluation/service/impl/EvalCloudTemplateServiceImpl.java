package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRuleTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalCloudTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalRuleTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalCloudTemplateListContext;
import com.cyberpeace.cr.evaluation.service.context.EvalHostContext;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalCloudTemplateServiceImpl extends ServiceImpl<EvalCloudTemplateMapper, EvalCloudTemplate> implements EvalCloudTemplateService {

    @Autowired
    private EvalCloudTemplateMapper evalCloudTemplateMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private EvalRuleTemplateMapper evalRuleTemplateMapper;

    @Autowired
    private EvalActionTemplateMapper evalActionTemplateMapper;

    @Autowired
    private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

    @Autowired
    private EvalHostTemplateMapper evalHostTemplateMapper;

    @Autowired
    private EvalStepTemplateMapper evalStepTemplateMapper;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private RemoteTopoService remoteTopoService;

    @Autowired
    private EvalStepTemplateServiceImpl evalStepTemplateService;

    @Autowired
    private EvalRuleTemplateService evalRuleTemplateService;

    @Override
    public void addCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
        EvalCloudTemplate oldEvalCloudTemplate = evalCloudTemplateMapper.selectById(evalCloudTemplateAddReq.getCloudTemplateId());
        if (oldEvalCloudTemplate != null) {
            throw new BusinessException(CrBusinessErrorCode.CLOUD_TEMPLATE_ID_EXIST);
        }
        EvalCloudTemplate evalCloudTemplate = BeanUtils.copy(evalCloudTemplateAddReq.getBaseInfo(), EvalCloudTemplate.class);
        evalCloudTemplate.setStepIdList(evalCloudTemplateAddReq.getStepIds());
        evalCloudTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        evalCloudTemplate.setResourceId(evalCloudTemplateAddReq.getCloudTemplateId());
        evalCloudTemplate.setTopoId(localSceneService.saveTopo(evalCloudTemplateAddReq.getSceneConfigData(),
                BusinessCode.RANGE_EVALUATION.getCode()));
        evalCloudTemplateMapper.insert(evalCloudTemplate);
    }

    @Override
    public void modifyCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
        String topoId = evalCloudTemplateMapper.getTopoIdById(evalCloudTemplateAddReq.getCloudTemplateId());
        EvalCloudTemplate newEvalCloudTemplate = BeanUtils.copy(evalCloudTemplateAddReq.getBaseInfo(), EvalCloudTemplate.class);
        newEvalCloudTemplate.setStepIdList(evalCloudTemplateAddReq.getStepIds());
        newEvalCloudTemplate.setResourceId(evalCloudTemplateAddReq.getCloudTemplateId());
        newEvalCloudTemplate.setTopoId(topoId);
        localSceneService.saveTopo(evalCloudTemplateAddReq.getSceneConfigData(), topoId);
        evalCloudTemplateMapper.updateById(newEvalCloudTemplate);
    }

    @Override
    public void deleteCloudTemplate(List<String> ids) {

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                evalCloudTemplateMapper.deleteBatchIds(ids);
                evalRuleTemplateMapper.deleteByCloudTemplateIds(ids);
                evalActionTemplateMapper.deleteByCloudTemplateIds(ids);
                evalDimensionTemplateMapper.deleteByCloudTemplateIds(ids);
                evalHostTemplateMapper.deleteByCloudTemplateIds(ids);
                evalStepTemplateMapper.deleteByCloudTemplateIds(ids);

                List<String> topoIds = evalCloudTemplateMapper.getTopoIdsByIds(ids);
                remoteTopoService.batchDeleteTopo(topoIds);
            }
        });
    }

    @Override
    public List<EvalCloudTemplateListRsp> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq) {
        List<EvalCloudTemplateListContext> cloudList = evalCloudTemplateMapper.selectList(evalCloudTemplateListReq.getType(), evalCloudTemplateListReq.getPattern());
        List<String> createUserIds = cloudList.stream().map(EvalCloudTemplateListContext::getCreateUserId).distinct().collect(Collectors.toList());
        Map<String, String> userMap = remoteUserService.getIdNameMap(createUserIds);
        List<String> topoIds = cloudList.stream().map(EvalCloudTemplateListContext::getTopoId).collect(Collectors.toList());
        Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchPostTopoDigest(topoIds);
        return cloudList.stream().map(evalCloudTemplateListContext -> {
            EvalCloudTemplateListRsp evalCloudTemplateListRsp = BeanUtils.copy(evalCloudTemplateListContext, EvalCloudTemplateListRsp.class);
            evalCloudTemplateListRsp.setCloudTemplateId(evalCloudTemplateListContext.getResourceId());
            evalCloudTemplateListRsp.setCreateUser(userMap.get(evalCloudTemplateListContext.getCreateUserId()));
            evalCloudTemplateListRsp.setType(EnumUtils.getEnumDescByCode(evalCloudTemplateListContext.getType(), EvalCloudTemplate.TestType.class));
            TopoDigest topoDigest = nodeCountMap.get(evalCloudTemplateListContext.getTopoId());
            if (topoDigest == null) {
                evalCloudTemplateListRsp.setNodeCount(0);
            } else {
                evalCloudTemplateListRsp.setNodeCount(topoDigest.getNodeCounts());
            }
            return evalCloudTemplateListRsp;
        }).collect(Collectors.toList());
    }

    @Override
    public EvalCloudTemplateEnumRsp getCloudTemplateEnum() {
        EvalCloudTemplateEnumRsp evalCloudTemplateEnumRsp = new EvalCloudTemplateEnumRsp();
        evalCloudTemplateEnumRsp.setCloudType(Arrays.stream(EvalCloudTemplate.CloudType.values())
                .map(cloudType -> new CodeNameRsp(cloudType.getCode(), cloudType.getDesc())).collect(Collectors.toList()));
        evalCloudTemplateEnumRsp.setTestType(Arrays.stream(EvalCloudTemplate.TestType.values())
                .map(testType -> new CodeNameRsp(testType.getCode(), testType.getDesc())).collect(Collectors.toList()));
        return evalCloudTemplateEnumRsp;
    }

    @Override
    public EvalCloudTemplateAddReq getCloudTemplate(String resourceId) {
        EvalCloudTemplate evalCloudTemplate = evalCloudTemplateMapper.selectById(resourceId);
        JSONObject topoData = localSceneService.getTopoData(evalCloudTemplate.getTopoId());
        JSONArray jsonArray = topoData.getJSONObject("scene_config").getJSONObject("topology").getJSONArray("elements");
        Map<String, String> userMap = remoteUserService.getIdNameMap(Arrays.asList(evalCloudTemplate.getCreateUserId()));
        EvalCloudTemplateBaseReq evalCloudTemplateBaseReq = BeanUtils.copy(evalCloudTemplate, EvalCloudTemplateBaseReq.class);
        String typeName = EnumUtils.getEnumDescByCode(evalCloudTemplateBaseReq.getType(), EvalCloudTemplate.TestType.class);
        evalCloudTemplateBaseReq.setNodeCount(jsonArray.size());
        evalCloudTemplateBaseReq.setTypeName(typeName);
        evalCloudTemplateBaseReq.setCreateUser(userMap.get(evalCloudTemplate.getCreateUserId()));
        evalCloudTemplateBaseReq.setDifficultyName(getDifficultyName(evalCloudTemplate.getDifficulty()));
        evalCloudTemplateBaseReq.setCloudTypeName(getCloudTypeName(evalCloudTemplate.getCloudType()));
        EvalCloudTemplateAddReq evalCloudTemplateAddReq = new EvalCloudTemplateAddReq();
        evalCloudTemplateAddReq.setBaseInfo(evalCloudTemplateBaseReq);
        evalCloudTemplateAddReq.setCloudTemplateId(evalCloudTemplate.getResourceId());
        evalCloudTemplateAddReq.setStepIds(evalCloudTemplate.getStepIdList());
        evalCloudTemplateAddReq.setSceneConfigData(topoData);
        return evalCloudTemplateAddReq;
    }

    //	写一个方法 难度等级 1 初级 2中级 3 高级
    public String getDifficultyName(Integer difficulty) {
        if (difficulty == 1) {
            return "初级";
        }
        if (difficulty == 2) {
            return "中级";
        }
        if (difficulty == 3) {
            return "高级";
        }
        return "";
    }

//    写一个方法 云类型名 0 混合云 1 私有云 2 公有云
    public String getCloudTypeName(Integer cloudType) {
        if (cloudType == 0) {
            return "混合云";
        }
        if (cloudType == 1) {
            return "私有云";
        }
        if (cloudType == 2) {
            return "公有云";
        }
        return "";
    }

    @Override
    public ListRsp<EvalTestTemplateListRsp> templateList(EvalTestListReq evalTestListReq) {
        IPage<EvalCloudTemplate> templateIPage = evalCloudTemplateMapper.templateList(evalTestListReq.toPageParam(), evalTestListReq);
        List<String> topoIds = templateIPage.getRecords().stream().map(EvalCloudTemplate::getTopoId).collect(Collectors.toList());
        Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
        templateIPage.convert(template -> {
            EvalTestTemplateListRsp templateListRsp = BeanUtils.copy(template, EvalTestTemplateListRsp.class);
            templateListRsp.setTemplateId(template.getResourceId());
            templateListRsp.setTemplateName(template.getName());
            templateListRsp.setNodeCount(nodeCountMap.get(template.getTopoId()).getNodeCounts());
            return templateListRsp;
        });
        return PageUtil.getListResult(templateIPage);
    }

    @Override
    public String makeDumpFile(List<String> templateIds, String tempDir) throws IOException {
        if (templateIds.isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.EMPTY_RESOURCE_ID_LIST);
        }
        log.info("make dump file for templateIds:{}", templateIds);
        // 1. make temp dir
        FileUtil.mkdirs(tempDir);
        List<EvalCloudTemplateAddReq> contextList = Lists.newArrayList();
        for (String templateId : templateIds) {
            log.info("make dump file for templateId:{}", templateId);
            EvalCloudTemplateDumpReq cloudTemplate = BeanUtils.copy(getCloudTemplate(templateId), EvalCloudTemplateDumpReq.class);
//			2.1步骤信息处理
            List<EvalStepTemplateListRsp> stepList = evalStepTemplateService.getStepList(templateId);
            cloudTemplate.setStepList(stepList);
//			2.2事件规则处理
            List<EvalRuleTemplate> evalRuleTemplateList = evalRuleTemplateMapper.selectAllByCloudTemplateId(templateId);
            List<EvalRuleTemplateDetailRsp> ruleList = evalRuleTemplateList.stream()
                    .map(evalRuleTemplate -> {
                        EvalRuleTemplateDetailRsp ruleTemplate = evalRuleTemplateService.getRuleTemplate(evalRuleTemplate.getResourceId());
                        return ruleTemplate;
                    })
                    .collect(Collectors.toList());
            cloudTemplate.setRuleList(ruleList);
//            封面处理
            // 2. copy pics
            /*String originPath = AttachmentPath.UPLOAD_IMAGE.getDesc() + cloudTemplate.getBaseInfo().getLogo();
            String destPath = tempDir + File.separator + CrCommonConstants.RESOURCE_IMAGE_DIR
                    + File.separator + cloudTemplate.getBaseInfo().getLogo();
            try {
                FileUtil.copyFile(originPath, destPath, false);
            } catch (Exception e) {
                log.error("error occurred when copying picture");
                throw new IOException(e);
            }*/

            // 3.collect template data
            log.info("make dump file for cloudTemplate:{}", cloudTemplate);
            contextList.add(cloudTemplate);
        }

        // 4. generate data.json
        String jsonPath = tempDir + File.separator + CrCommonConstants.RESOURCE_FILE_NAME;
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(jsonPath),
                StandardCharsets.UTF_8);
        String data = JsonUtil.arrayToString(contextList);
        FileUtil.writeString(data, osw);

        // 5. generate zip file, return zip file path
        return ZipUtil.toZip(tempDir);
    }

    @Override
    public Boolean batchLoad(String unZipPath) {
        // 1. read data.json
        String dataJson = FileUtil.getContent(
                new File(unZipPath + File.separator + CrCommonConstants.RESOURCE_FILE_NAME));
        List<EvalCloudTemplateDumpReq> resources = JsonUtil.toJsonObjectArray(dataJson,
                EvalCloudTemplateDumpReq.class);
        log.info("batchLoad resources:{}", JSON.toJSONString(resources));
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for (EvalCloudTemplateDumpReq resource : resources) {
//					2.1步骤信息处理
                    List<String> stepIds = resource.getStepIds();
                    if (stepIds.size() > 0) {
                        evalStepTemplateService.deleteStep(stepIds);
                    }

                    List<EvalStepTemplateListRsp> stepList = resource.getStepList();
                    List<String> newStepId = stepList.stream()
                            .map(step -> {
                                EvalStepTemplateAddReq evalStepTemplateAddReq = BeanUtils.copy(step, EvalStepTemplateAddReq.class);
                                evalStepTemplateAddReq.setCloudTemplateId(resource.getCloudTemplateId());
                                String stepId = evalStepTemplateService.addStep(evalStepTemplateAddReq);

                                return stepId;
                            })
                            .collect(Collectors.toList());
                    resource.setStepIds(newStepId);

//					2.2事件规则处理
//                    先删后加
                    LambdaQueryWrapper<EvalRuleTemplate> evalRuleTemplateQueryWrapper = new LambdaQueryWrapper<>();
                    evalRuleTemplateQueryWrapper.eq(EvalRuleTemplate::getCloudTemplateId, resource.getCloudTemplateId());
                    List<String> ruelIds = evalRuleTemplateService.list(evalRuleTemplateQueryWrapper).stream().map(EvalRuleTemplate::getResourceId).collect(Collectors.toList());
                    if (!ruelIds.isEmpty()) {
                        DeleteReq deleteReq = new DeleteReq();
                        deleteReq.setIds(ruelIds);
                        evalRuleTemplateService.deleteRuleTemplate(deleteReq);
                    }
                    for (EvalRuleTemplateDetailRsp rule : resource.getRuleList()) {
                        EvalRuleTemplateAddReq ruleTemplateAddReq = BeanUtils.copy(rule, EvalRuleTemplateAddReq.class);
                        List<EvalHostContext> hosts = rule.getHosts();
                        List<EvalHostTemplateAddReq> hostList = JsonUtil.toJsonObjectArray(JsonUtil.arrayToString(hosts), EvalHostTemplateAddReq.class);
                        ruleTemplateAddReq.setHostList(hostList);
                        evalRuleTemplateService.addRuleTemplate(ruleTemplateAddReq);
                    }
                    try {
                        addCloudTemplate(resource);
                    } catch (BusinessException e) {
                        modifyCloudTemplate(resource);
                    }
                }
            }
        });
        return true;
    }

}
