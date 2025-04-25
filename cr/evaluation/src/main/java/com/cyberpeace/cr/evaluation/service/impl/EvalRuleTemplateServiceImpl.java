package com.cyberpeace.cr.evaluation.service.impl;

import ch.qos.logback.core.util.AggregationType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.StrCodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleMPRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateDetailRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRuleTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlarmCondition;
import com.cyberpeace.cr.evaluation.dao.entity.EvalHostTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRuleTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalHostContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.PortInfo;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalRuleTemplateServiceImpl extends ServiceImpl<EvalRuleTemplateMapper, EvalRuleTemplate> implements EvalRuleTemplateService {

    @Autowired
    private EvalRuleTemplateMapper evalRuleTemplateMapper;

    @Autowired
    private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

    @Autowired
    private EvalActionTemplateMapper evalActionTemplateMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private EvalHostTemplateMapper evalHostTemplateMapper;

    @Autowired
    private EvalCloudTemplateMapper evalCloudTemplateMapper;

    @Autowired
    private TopoHandlerService topoHandlerService;

    @Autowired
    private SituationHandlerService situationHandlerService;

    @Autowired
    private EvalDimensionTemplateService evalDimensionTemplateService;

    @Autowired
    private EvalHostTemplateService evalHostTemplateService;

    @Autowired
    private EvalActionTemplateService evalActionTemplateService;

    @Autowired
    private EvalStepTemplateMapper evalStepTemplateMapper;

    @Autowired
    private EvalAlarmConditionService evalAlarmConditionService;

    @Autowired
    private EvalAlarmConditionMapper evalAlarmConditionMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public EvalRuleTemplateDetailRsp addRuleTemplate(EvalRuleTemplateAddReq evalRuleTemplateAddReq) {
        List<EvalHostTemplateAddReq> hostList = evalRuleTemplateAddReq.getHostList();
        if (CollectionUtils.isEmpty(hostList)) {
            throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, "主机", "主机配置不能为空");
        }
        EvalRuleTemplate evalRuleTemplate = BeanUtils.copy(evalRuleTemplateAddReq, EvalRuleTemplate.class);
        evalRuleTemplate.setName(evalRuleTemplateAddReq.getRuleName());
        evalRuleTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
        evalRuleTemplate.setLabelList(evalRuleTemplateAddReq.getLabels());
        List<EvalAlarmReq> alarms = evalRuleTemplateAddReq.getAlarms();
        if (alarms.size() > 0) {
            EvalAlarmReq evalAlarmReq = alarms.get(0);
            evalRuleTemplate.setAlarmName(evalAlarmReq.getAlarmName());
            evalRuleTemplate.setAlarmLevel(evalAlarmReq.getLevel());
            evalRuleTemplate.setAlarmDescription(evalAlarmReq.getDescription());
        }
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalRuleTemplateMapper.insert(evalRuleTemplate);
                String cloudTemplateId = evalRuleTemplateAddReq.getCloudTemplateId();
                String ruleId = evalRuleTemplate.getResourceId();

                evalHostTemplateService.addHostTemplateList(evalRuleTemplateAddReq.getHostList(), cloudTemplateId, ruleId);
                evalAlarmConditionService.addAlarmConditionList(evalRuleTemplateAddReq.getAlarmConditions(), cloudTemplateId, ruleId);
            }
        });
        return getRuleTemplate(evalRuleTemplate.getResourceId());
    }

    @Override
    public List<EvalRuleTemplateListRsp> getRuleTemplateList(EvalRuleTemplateListReq evalRuleTemplateListReq) {
        List<EvalRuleTemplate> evalRuleTemplateList = evalRuleTemplateMapper.listEvalRuleTemplate(evalRuleTemplateListReq);
        if (CollectionUtils.isEmpty(evalRuleTemplateList)) {
            return new ArrayList<>();
        }
        List<String> ruleIds = evalRuleTemplateList.stream().map(EvalRuleTemplate::getResourceId).collect(Collectors.toList());

        List<EvalAlarmCondition> evalAlarmConditionList = evalAlarmConditionMapper.selectList(new LambdaQueryWrapper<EvalAlarmCondition>().in(EvalAlarmCondition::getRuleId, ruleIds));
        Map<String, Long> evalAlarmConditionCountMap = evalAlarmConditionList.stream().collect(Collectors.groupingBy(EvalAlarmCondition::getRuleId, Collectors.counting()));

        List<EvalRuleTemplateListRsp> evalRuleTemplateListRspList = new ArrayList<>();
        for (EvalRuleTemplate evalRuleTemplate : evalRuleTemplateList) {
            EvalRuleTemplateListRsp evalRuleTemplateListRsp = BeanUtils.copy(evalRuleTemplate, EvalRuleTemplateListRsp.class);
            evalRuleTemplateListRsp.setRuleId(evalRuleTemplate.getResourceId());
            evalRuleTemplateListRsp.setRuleName(evalRuleTemplate.getName());
            evalRuleTemplateListRsp.setStatus(EnumUtils.getEnumDescByCode(evalRuleTemplate.getStatus(), EvalRuleTemplate.Status.class));
            evalRuleTemplateListRsp.setCreateTime(TimeUtil.format1(evalRuleTemplate.getCreateTime()));
            evalRuleTemplateListRsp.setLabelList(evalRuleTemplate.getLabelList());
            evalRuleTemplateListRsp.setConditionCount(evalAlarmConditionCountMap.getOrDefault(evalRuleTemplate.getResourceId(), 0L).intValue());
            evalRuleTemplateListRspList.add(evalRuleTemplateListRsp);
        }
        return evalRuleTemplateListRspList;
    }

    @Override
    public List<EvalRuleMPRsp> getRuleList(String cloudTemplateId) {
        LambdaQueryWrapper<EvalRuleTemplate> queryWrapper = new LambdaQueryWrapper<EvalRuleTemplate>()
                .eq(EvalRuleTemplate::getCloudTemplateId, cloudTemplateId)
                .eq(EvalRuleTemplate::getStatus, EvalRuleTemplate.Status.OPEN.getCode());
        List<EvalRuleTemplate> evalRuleTemplateList = evalRuleTemplateMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(evalRuleTemplateList)) {
            return new ArrayList<>();
        }
        List<String> ruleIds = evalRuleTemplateList.stream().map(EvalRuleTemplate::getResourceId).collect(Collectors.toList());
//		告警条件
        List<EvalAlarmCondition> evalAlarmConditionList = evalAlarmConditionMapper.selectList(new LambdaQueryWrapper<EvalAlarmCondition>().in(EvalAlarmCondition::getRuleId, ruleIds));
        Map<String, List<EvalAlarmCondition>> evalAlarmConditionMap = evalAlarmConditionList.stream().collect(Collectors.groupingBy(EvalAlarmCondition::getRuleId));
//		主机信息
        List<EvalHostTemplate> evalHostTemplateList = evalHostTemplateMapper.selectList(new LambdaQueryWrapper<EvalHostTemplate>().in(EvalHostTemplate::getRuleId, ruleIds));
        Map<String, String> hostIdMap = evalHostTemplateList.stream()
                .collect(Collectors.groupingBy(
                        EvalHostTemplate::getRuleId,
                        Collectors.mapping(EvalHostTemplate::getHostId, Collectors.joining(","))
                ));

        List<EvalRuleMPRsp> ruleTemplateDetailRspList = evalRuleTemplateList.stream()
                .map(evalRuleTemplate -> {
                    EvalRuleMPRsp evalRule = BeanUtils.copy(evalRuleTemplate, EvalRuleMPRsp.class);
                    List<EvalAlarmCondition> alarmConditionList = evalAlarmConditionMap.get(evalRuleTemplate.getResourceId());
                    List<EvalAlarmConditionReq> evalAlarmConditionReqList = alarmConditionList.stream().map(evalAlarmCondition -> {
                        EvalAlarmConditionReq evalAlarmConditionReq = BeanUtils.copy(evalAlarmCondition, EvalAlarmConditionReq.class);
                        evalAlarmConditionReq.setCondition(evalAlarmCondition.getAlarmCondition());
                        return evalAlarmConditionReq;
                    }).collect(Collectors.toList());
                    try {
                        evalRule.setAlarmConditions(objectMapper.writeValueAsString(evalAlarmConditionReqList));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    evalRule.setHosts(hostIdMap.get(evalRuleTemplate.getResourceId()));
                    return evalRule;
                })
                .collect(Collectors.toList());

        return ruleTemplateDetailRspList;
    }

    @Override
    public void deleteRuleTemplate(DeleteReq deleteReq) {
        log.info("deleteReq:" + deleteReq.toString());
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                evalRuleTemplateMapper.deleteBatchIds(deleteReq.getIds());
                evalActionTemplateMapper.deleteByRuleIds(deleteReq.getIds());
                evalAlarmConditionMapper.delete(new QueryWrapper<EvalAlarmCondition>().lambda().in(EvalAlarmCondition::getRuleId, deleteReq.getIds()));
            }
        });
    }

    @Override
    public EvalRuleTemplateDetailRsp getRuleTemplate(String id) {
        EvalRuleTemplate evalRuleTemplate = evalRuleTemplateMapper.selectById(id);
        if (evalRuleTemplate == null) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        EvalRuleTemplateDetailRsp evalRuleTemplateDetail = BeanUtils.copy(evalRuleTemplate, EvalRuleTemplateDetailRsp.class);
        Integer status = evalRuleTemplateDetail.getStatus();
        if (0 == status) {
            evalRuleTemplateDetail.setStatusName("关闭");
        } else if (1 == status) {
            evalRuleTemplateDetail.setStatusName("打开");
        }

        evalRuleTemplateDetail.setRuleId(evalRuleTemplate.getResourceId());
        evalRuleTemplateDetail.setRuleName(evalRuleTemplate.getName());
        evalRuleTemplateDetail.setCreateUser(remoteUserService.getById(evalRuleTemplate.getCreateUserId()).getUserName());
        evalRuleTemplateDetail.setCreateTime(TimeUtil.format1(evalRuleTemplate.getCreateTime()));
        evalRuleTemplateDetail.setLabels(evalRuleTemplate.getLabelList());
        List<EvalHostTemplate> evalHostTemplateList = evalHostTemplateMapper.selectByRuleId(evalRuleTemplate.getResourceId());
        if (evalCloudTemplateMapper.selectById(evalRuleTemplate.getCloudTemplateId()) == null
                || StringUtils.isBlank(evalCloudTemplateMapper.getTopoIdById(evalRuleTemplate.getCloudTemplateId()))) {
            //说明此时拓扑未保存，直接读取数据库的数据
            evalRuleTemplateDetail.setHosts(
                    evalHostTemplateList.stream()
                            .map(evalHostTemplate -> BeanUtils.copy(evalHostTemplate, EvalHostContext.class))
                            .collect(Collectors.toList())
            );
        } else {
            //读取远程数据，防止数据变化
            SceneData sceneData = topoHandlerService.getSceneData(null, evalCloudTemplateMapper.getTopoIdById(evalRuleTemplate.getCloudTemplateId()));
            SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
            List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
            Map<String, ElementInfo> idElementMap = elementInfoList.stream().collect(Collectors.toMap(ElementInfo::getElementId, e -> e));
            evalRuleTemplateDetail.setHosts(
                    evalHostTemplateList.stream()
                            .map(evalHostTemplate -> {
                                EvalHostContext evalHostContext = BeanUtils.copy(evalHostTemplate, EvalHostContext.class);
                                evalHostContext.setHostName(idElementMap.get(evalHostContext.getHostId()) != null ? idElementMap.get(evalHostContext.getHostId()).getName() : "");
                                evalHostContext.setIp(idElementMap.get(evalHostContext.getHostId()) != null ? getElementIps(idElementMap.get(evalHostContext.getHostId())) : "");
                                return evalHostContext;
                            })
                            .collect(Collectors.toList()));
        }

        List<EvalAlarmCondition> evalAlarmConditionList = evalAlarmConditionMapper.selectList(new LambdaQueryWrapper<EvalAlarmCondition>().eq(EvalAlarmCondition::getRuleId, evalRuleTemplate.getResourceId()));
        List<EvalAlarmConditionReq> evalAlarmConditionReqList = evalAlarmConditionList.stream().map(evalAlarmCondition -> {
                    EvalAlarmConditionReq evalAlarmConditionReq = BeanUtils.copy(evalAlarmCondition, EvalAlarmConditionReq.class);
                    evalAlarmConditionReq.setCondition(evalAlarmCondition.getAlarmCondition());
                    String aggregationType = evalAlarmConditionReq.getAggregationType();
                    evalAlarmConditionReq.setAggregationTypeName(EvalRuleTemplate.Aggregation.getDescByCode(aggregationType));
                    evalAlarmConditionReq.setOperatorName(EvalRuleTemplate.Operator.getDescByCode(evalAlarmConditionReq.getOperator()));
                    return evalAlarmConditionReq;
                })
                .collect(Collectors.toList());
        evalRuleTemplateDetail.setAlarmConditions(evalAlarmConditionReqList);
        if (StringUtils.isNotBlank(evalRuleTemplate.getAlarmName())) {
            EvalAlarmReq evalAlarmReq = new EvalAlarmReq();
            evalAlarmReq.setAlarmName(evalRuleTemplate.getAlarmName());
            evalAlarmReq.setDescription(evalRuleTemplate.getAlarmDescription());
            evalAlarmReq.setLevel(evalRuleTemplate.getAlarmLevel());
            evalAlarmReq.setAlarmLevelName(getAlarmLevelName(evalAlarmReq.getLevel()));
            evalRuleTemplateDetail.setAlarms(Arrays.asList(evalAlarmReq));
        }
        return evalRuleTemplateDetail;
    }

    @Override
    public EvalRuleTemplateDetailRsp modifyRuleTemplate(EvalRuleTemplateModifyReq evalRuleTemplateModifyReq) {
        List<EvalHostTemplateAddReq> hostList = evalRuleTemplateModifyReq.getHostList();
        if (CollectionUtils.isEmpty(hostList)) {
            throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, "主机", "主机配置不能为空");
        }
        EvalRuleTemplate evalRuleTemplate = BeanUtils.copy(evalRuleTemplateModifyReq, EvalRuleTemplate.class);
        evalRuleTemplate.setResourceId(evalRuleTemplateModifyReq.getRuleId());
        evalRuleTemplate.setName(evalRuleTemplateModifyReq.getRuleName());
        evalRuleTemplate.setLabelList(evalRuleTemplateModifyReq.getLabels());
        List<EvalAlarmReq> alarms = evalRuleTemplateModifyReq.getAlarms();
        if (alarms.size() > 0) {
            EvalAlarmReq evalAlarmReq = alarms.get(0);
            evalRuleTemplate.setAlarmName(evalAlarmReq.getAlarmName());
            evalRuleTemplate.setAlarmLevel(evalAlarmReq.getLevel());
            evalRuleTemplate.setAlarmDescription(evalAlarmReq.getDescription());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

                String cloudTemplateId = evalRuleTemplateModifyReq.getCloudTemplateId();
                String ruleId = evalRuleTemplateModifyReq.getRuleId();
                List<String> ruleIds = Arrays.asList(evalRuleTemplateModifyReq.getRuleId());

                evalRuleTemplateMapper.updateById(evalRuleTemplate);
                evalHostTemplateMapper.deleteByRuleTemplateIds(ruleIds);
                evalHostTemplateService.addHostTemplateList(evalRuleTemplateModifyReq.getHostList(), cloudTemplateId, ruleId);
                evalAlarmConditionMapper.delete(new QueryWrapper<EvalAlarmCondition>().lambda().in(EvalAlarmCondition::getRuleId, ruleIds));
                evalAlarmConditionService.addAlarmConditionList(evalRuleTemplateModifyReq.getAlarmConditions(), cloudTemplateId, ruleId);
            }
        });
        return getRuleTemplate(evalRuleTemplate.getResourceId());
    }

    private String getAlarmLevelName(Integer alarmLevel){
//        1 低 5 中 10 高
        switch (alarmLevel) {
            case 1:
                return "低";
            case 5:
                return "中";
            case 10:
                return "高";
            default:
                return "";
        }
    }


    @Override
    public EvalRuleTemplateEnumRsp getRuleEnum() {
        EvalRuleTemplateEnumRsp evalRuleTemplateEnumRsp = new EvalRuleTemplateEnumRsp();
        evalRuleTemplateEnumRsp.setStatus(Arrays.stream(EvalRuleTemplate.Status.values())
                .map(status -> new CodeNameRsp(status.getCode(), status.getDesc())).collect(Collectors.toList()));
        evalRuleTemplateEnumRsp.setGrade(Arrays.stream(EvalRuleTemplate.Grade.values())
                .map(grade -> new CodeNameRsp(grade.getCode(), grade.getDesc())).collect(Collectors.toList()));
        evalRuleTemplateEnumRsp.setOperator(Arrays.stream(EvalRuleTemplate.Operator.values())
                .map(operator -> new StrCodeNameRsp(operator.getCode(), operator.getDesc())).collect(Collectors.toList()));
        evalRuleTemplateEnumRsp.setAggregation(Arrays.stream(EvalRuleTemplate.Aggregation.values())
                .map(aggregation -> new StrCodeNameRsp(aggregation.getCode(), aggregation.getDesc())).collect(Collectors.toList()));
        evalRuleTemplateEnumRsp.setCondition(Arrays.stream(EvalRuleTemplate.Condition.values())
                .map(condition -> new StrCodeNameRsp(condition.getCode(), condition.getDesc())).collect(Collectors.toList()));
        evalRuleTemplateEnumRsp.setEvaluationFrequency(Arrays.stream(EvalRuleTemplate.EvaluationFrequency.values())
                .map(evaluationFrequency -> new StrCodeNameRsp(evaluationFrequency.getCode(), evaluationFrequency.getDesc())).collect(Collectors.toList()));
        return evalRuleTemplateEnumRsp;
    }

    private String getElementIps(ElementInfo elementInfo) {
        List<String> ips = elementInfo.getPorts().values().stream().map(PortInfo::getIp).collect(Collectors.toList());
        return StringUtils.join(ips, ",");
    }


}
