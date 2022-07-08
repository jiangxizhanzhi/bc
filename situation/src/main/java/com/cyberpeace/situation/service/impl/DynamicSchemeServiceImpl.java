package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.*;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.DynamicStageRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsAttackEffectDynamic;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SituationHandlerService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DynamicSchemeServiceImpl implements DynamicSchemeService {

    @Autowired
    private AttackMessageMapper attackMessageMapper;

    @Autowired
    private DynamicSchemeMapper dynamicSchemeMapper;

    @Autowired
    private SituationHandlerService situationHandlerService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    String PICTURE_TYPE = "PNG,JPG,BMP,GIF";

    @Override
    public void addDynamicScheme(DynamicSchemeReq dynamicSchemeReq) {
        DynamicScheme dynamicScheme = dynamicSchemeReqToDynamicScheme(dynamicSchemeReq);
        dynamicSchemeMapper.insert(dynamicScheme);
    }

    @Override
    public DynamicSchemeRes getDynamicSchemeById(String resourceId) {
        DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
        DynamicSchemeRes dynamicSchemeRes = BeanUtils.copy(dynamicScheme,DynamicSchemeRes.class);
        List<DynamicStage> dynamicStageList = JsonUtil.toJsonObjectArray(dynamicScheme.getSchemeData(),DynamicStage.class);
        List<DynamicStageRes> dynamicStageResList = new ArrayList<>();
        for (DynamicStage dynamicStage:dynamicStageList){
            DynamicStageRes dynamicStageRes = new DynamicStageRes();
            dynamicStageRes.setName(dynamicStage.getName());
            List<AttackMessageReq> attackMessageBoList = new ArrayList<>();
            List<AttackMessage> attackMessageList = attackMessageMapper.selectBatchIds(dynamicStage.getAttackMessageIds());
            for (AttackMessage attackMessage:attackMessageList){
                AttackMessageReq attackMessageBo = BeanUtils.copy(attackMessage, AttackMessageReq.class);
                attackMessageBo.setLineStyle(JSON.parseObject(attackMessage.getLineStyle(), LineStyle.class));
                attackMessageBo.setMediaAnimationList(JsonUtil.toJsonObjectArray(attackMessage.getMediaAnimation(), MediaAnimation.class));
                attackMessageBoList.add(attackMessageBo);
                dynamicStageRes.setAttackMessageList(attackMessageBoList);
            }
            dynamicStageResList.add(dynamicStageRes);
            dynamicSchemeRes.setSchemeData(dynamicStageResList);
        }
        return dynamicSchemeRes;
    }

    @Override
    public void updateDynamicScheme(UpdateDynamicSchemeReq updateDynamicSchemeReq) {
        DynamicScheme dynamicScheme = dynamicSchemeReqToDynamicScheme(updateDynamicSchemeReq);
        dynamicSchemeMapper.updateById(dynamicScheme);
    }

    @Override
    public void deleteDynamicScheme(String resourceId) {
        List<String> attackMessageIds = new ArrayList<>();
        DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
        List<DynamicStage> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicScheme.getSchemeData(),DynamicStage.class);
        for (DynamicStage dynamicStage:dynamicStageBoList){
            attackMessageIds.addAll(dynamicStage.getAttackMessageIds());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                attackMessageMapper.deleteBatchIds(attackMessageIds);
                dynamicSchemeMapper.deleteById(resourceId);
            }
        });
    }

    @Override
    public List<DynamicSchemeNameRes> getAllDynamicName() {
        return dynamicSchemeMapper.getAllNames();
    }

    public DynamicScheme dynamicSchemeReqToDynamicScheme(DynamicSchemeReq dynamicSchemeReq){
        DynamicScheme dynamicScheme = BeanUtils.copy(dynamicSchemeReq,DynamicScheme.class);
        List<DynamicStage> dynamicStageList = new ArrayList<>();
        for(DynamicStage dynamicStage:dynamicSchemeReq.getSchemeData()){
            //阶段
            DynamicStage dynamicStageBo = new DynamicStage();
            dynamicStage.setName(dynamicStage.getName());
            dynamicStage.setAttackMessageIds(dynamicStage.getAttackMessageIds());
            dynamicStageList.add(dynamicStageBo);
        }
        dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageList));
        return  dynamicScheme;
    }
}
