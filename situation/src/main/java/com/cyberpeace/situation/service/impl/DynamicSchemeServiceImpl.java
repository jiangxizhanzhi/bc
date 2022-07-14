package com.cyberpeace.situation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.AttackMessageReq;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeReq;
import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import com.cyberpeace.situation.controller.res.DynamicSchemeNameRes;
import com.cyberpeace.situation.controller.res.DynamicSchemeRes;
import com.cyberpeace.situation.controller.res.OptionsRes;
import com.cyberpeace.situation.dao.entity.AttackMessage;
import com.cyberpeace.situation.dao.entity.DynamicScheme;
import com.cyberpeace.situation.dao.entity.SceneConfig;
import com.cyberpeace.situation.dao.mapper.AttackMessageMapper;
import com.cyberpeace.situation.dao.mapper.DynamicSchemeMapper;
import com.cyberpeace.situation.dao.mapper.SceneConfigMapper;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsAttackEffectDynamic;
import com.cyberpeace.situation.model.ws.WsAttackSchedule;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.service.AttackMessageService;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import lombok.SneakyThrows;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private TopoHandlerService topoHandlerService;

    @Autowired
    private AttackMessageService attackMessageService;

    @Autowired
    private SceneConfigMapper sceneConfigMapper;

    public static String PICTURE_TYPE = "PNG,JPG,JPEG,GIF,SVG";

    public static String VIDEO_TYPE = "MP4";

    @Override
    public String addDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq) {
        DynamicScheme dynamicScheme = addDynamicSchemeReqToDynamicScheme(addDynamicSchemeReq);
        dynamicScheme.setResourceId(IDGeneratorUtil.getID());
        dynamicSchemeMapper.insert(dynamicScheme);
        return dynamicScheme.getResourceId();
    }

    @Override
    public DynamicSchemeRes getDynamicSchemeById(String resourceId) {
        DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
        if(dynamicScheme == null){
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        DynamicSchemeRes dynamicSchemeRes = BeanUtils.copy(dynamicScheme, DynamicSchemeRes.class);
        List<DynamicStageBo> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicScheme.getSchemeData(), DynamicStageBo.class);
        List<DynamicStage> dynamicStageResList = new ArrayList<>();
        for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
            DynamicStage dynamicStageRes = new DynamicStage();
            dynamicStageRes.setName(dynamicStageBo.getName());
            List<AttackMessageReq> attackMessageBoList = new ArrayList<>();
            if(dynamicStageBo.getAttackMessageIds().size()>0){
                List<AttackMessage> attackMessageList = attackMessageMapper.selectBatchIds(dynamicStageBo.getAttackMessageIds());
                for (AttackMessage attackMessage : attackMessageList) {
                    AttackMessageReq attackMessageBo = BeanUtils.copy(attackMessage, AttackMessageReq.class);
                    attackMessageBo.setMediaAnimationList(JsonUtil.toJsonObjectArray(attackMessage.getMediaAnimation(), MediaAnimation.class));
                    attackMessageBoList.add(attackMessageBo);
                    dynamicStageRes.setAttackMessageReqList(attackMessageBoList);
                }
            }else {
                dynamicStageRes.setAttackMessageReqList(attackMessageBoList);
            }
            dynamicStageResList.add(dynamicStageRes);
        }
        dynamicSchemeRes.setSchemeData(dynamicStageResList);
        return dynamicSchemeRes;
    }

    @Override
    public DynamicSchemeReq updateDynamicScheme(DynamicSchemeReq dynamicSchemeReq) {
        DynamicScheme dynamicScheme = BeanUtils.copy(dynamicSchemeReq,DynamicScheme.class);
        List<DynamicStageBo> dynamicStageBoList = new ArrayList<>();
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                for(DynamicStage dynamicStage : dynamicSchemeReq.getSchemeData()){
                    HashSet<String> description = new HashSet<>(dynamicStage.getAttackMessageReqList().stream().map(AttackMessageReq::getDescription).collect(Collectors.toList()));
                    if (description.size()<dynamicStage.getAttackMessageReqList().size()){
                        throw new BusinessException(CrBusinessErrorCode.DUPLICATED_MISSION_NAME);
                    }
                    for(AttackMessageReq attackMessageReq:dynamicStage.getAttackMessageReqList()){
                        if(attackMessageReq.getResourceId() == null || attackMessageReq.getResourceId().equals("")){
                            attackMessageReq.setSceneId(dynamicSchemeReq.getSceneId());
                            attackMessageReq.setResourceId(attackMessageService.addAttackMessage(attackMessageReq));
                        }else {
                            attackMessageService.updateAttackMessage(attackMessageReq);
                        }
                    }
                    DynamicStageBo dynamicStageBo = new DynamicStageBo();
                    dynamicStageBo.setName(dynamicStage.getName());
                    dynamicStageBo.setAttackMessageIds(dynamicStage.getAttackMessageReqList().stream().map(AttackMessageReq::getResourceId).collect(Collectors.toList()));
                    dynamicStageBoList.add(dynamicStageBo);
                }
                dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageBoList));
                dynamicSchemeMapper.updateById(dynamicScheme);
            }
        });
        return dynamicSchemeReq;
    }

    @Override
    public void deleteDynamicScheme(String resourceId) {
        List<String> attackMessageIds = new ArrayList<>();
        DynamicScheme dynamicScheme = dynamicSchemeMapper.selectById(resourceId);
        List<DynamicStageBo> dynamicStageBoList = JsonUtil.toJsonObjectArray(dynamicScheme.getSchemeData(), DynamicStageBo.class);
        for (DynamicStageBo dynamicStageBo : dynamicStageBoList) {
            attackMessageIds.addAll(dynamicStageBo.getAttackMessageIds());
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                if(attackMessageIds.size()>0)
                    attackMessageMapper.deleteBatchIds(attackMessageIds);
                dynamicSchemeMapper.deleteById(resourceId);
            }
        });
    }

    @Override
    public List<DynamicSchemeNameRes> getAllDynamicName(String sceneId) {
        return dynamicSchemeMapper.getAllNamesBySceneId(sceneId);
    }

    @Override
    public void getLocalOption(OptionsRes optionsRes) {
        optionsRes.setAttackTypeList(StreamApiUtils.map(Lists.newArrayList(AttackType.values()), attackType ->
                new WebAttackType(attackType.getKeyWord(),attackType.getMsg(),
                        attackType.getActionType().getType(),attackType.getActionType().isSource(),attackType.getActionType().isDurationTime())));
        optionsRes.setLineTypeList(StreamApiUtils.map(Lists.newArrayList(LineType.values()),lineType -> new LocalType(lineType.getCode(),lineType.getMsg())));
        optionsRes.setColorList(StreamApiUtils.map(Lists.newArrayList(Color.values()),color -> new LocalType(color.getCode(),color.getMsg())));
    }

    @Override
    public void getRemoteOption(OptionsRes optionsRes, String sceneId) {
        SceneData sceneData = topoHandlerService.getSceneData(sceneId, null);
        SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
        List<AreaInfo> areaInfoList = Lists.newArrayList(situationTopo.getAreas().values());
        List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
        optionsRes.setAreaList(StreamApiUtils.map(areaInfoList, areaInfo -> new ElementType(areaInfo.getAreaId(),"area-"+areaInfo.getAreaId().substring(0,8))));
        optionsRes.setElementList(StreamApiUtils.map(elementInfoList,elementInfo -> new ElementType(elementInfo.getElementId(),elementInfo.getName())));
    }

    public DynamicScheme addDynamicSchemeReqToDynamicScheme(AddDynamicSchemeReq addDynamicSchemeReq) {
        DynamicScheme dynamicScheme = BeanUtils.copy(addDynamicSchemeReq, DynamicScheme.class);
        List<DynamicStageBo> dynamicStageList = new ArrayList<>();
        dynamicScheme.setSchemeData(JSON.toJSONString(dynamicStageList));
        return dynamicScheme;
    }

    @Override
    public void pushMediaToRoom(AttackMessageReq attackMessageBo) {
        WsAttackEffectDynamic wsAttackEffectDynamic = new WsAttackEffectDynamic();
        wsAttackEffectDynamic.setSceneId(attackMessageBo.getSceneId());
        wsAttackEffectDynamic.setCategory("attack");
        wsAttackEffectDynamic.setDescription(attackMessageBo.getDescription());
        wsAttackEffectDynamic.setName("攻击效果");
        wsAttackEffectDynamic.setType(ActionType.NONE.getCode());
        List<AttackEffect> attackEffectList = new ArrayList<>();
        for(MediaAnimation mediaAnimation:attackMessageBo.getMediaAnimationList()){
            AttackEffect attackEffect = BeanUtils.copy(mediaAnimation,AttackEffect.class);
            attackEffect.setElementId(mediaAnimation.getSourceId());
            //判断是图片还是视频
            String fileSuffix = FileUtil.getFileSuffix(mediaAnimation.getUrl()).toLowerCase();
            List<String> pictureTypeList = Arrays.asList(PICTURE_TYPE.toLowerCase().split(CommonConstant.CODE_COMMA));
            List<String> videoTypeList = Arrays.asList(VIDEO_TYPE.toLowerCase().split(CommonConstant.CODE_COMMA));
            if(pictureTypeList.contains(fileSuffix)){
                attackEffect.setMediaType(1);
            }else if(videoTypeList.contains(fileSuffix)){
                attackEffect.setMediaType(2);
            }else {
                throw new BusinessException(ErrorCode.FILE_TYPE_ERROR);
            }

            attackEffect.setMediaUrl(mediaAnimation.getUrl());
            attackEffect.setLineStyle(new LineStyle("#FFFFFF",3,"straight"));
            attackEffectList.add(attackEffect);
        }
        wsAttackEffectDynamic.setEffectList(attackEffectList);

        WsAttackEffectDynamic wsAttackEffectDynamicBlank = new WsAttackEffectDynamic();
        wsAttackEffectDynamicBlank.setName("攻击效果");
        wsAttackEffectDynamicBlank.setType(ActionType.NONE.getCode());
        wsAttackEffectDynamicBlank.setCategory("attack");
        wsAttackEffectDynamicBlank.setEffectList(new ArrayList<>());

        WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(MessageType.AttackEffect.getDesc(), wsAttackEffectDynamic);
        JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
        situationHandlerService.pushDataToRoom(data, attackMessageBo.getSceneId());

        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(MessageType.AttackEffect.getDesc(), wsAttackEffectDynamicBlank);
                JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
                Thread.sleep(attackMessageBo.getDurationTime() * 1000);
                situationHandlerService.pushDataToRoom(data, attackMessageBo.getSceneId());
            }
        });
        thread.start();

    }

    @Override
    public void pushAttackToRoom(AttackMessageReq attackMessageBo) {
        WsMultiNodeDynamic wsMultiNodeDynamic = BeanUtils.copy(attackMessageBo,WsMultiNodeDynamic.class);
        wsMultiNodeDynamic.setCategory("attack");
        for(AttackType attackType:AttackType.values()){
            if(attackType.getKeyWord().equals(attackMessageBo.getType())){
                wsMultiNodeDynamic.setType(attackType.getActionType().getCode());
            }
        }
        if (wsMultiNodeDynamic.getType() == null){
            wsMultiNodeDynamic.setType(ActionType.NONE.getCode());
        }
        wsMultiNodeDynamic.setName(null);
        wsMultiNodeDynamic.setLineStyle(new LineStyle(attackMessageBo.getLineColor(),attackMessageBo.getLineWidth(),attackMessageBo.getLineType()));
        wsMultiNodeDynamic.setDuration(attackMessageBo.getDurationTime());
        List<String> elementIds = getElementIds(attackMessageBo.getSceneId());
        WsMessage<WsMultiNodeDynamic> wsMessage;
        if(elementIds.contains(attackMessageBo.getTargetId()))
            wsMessage = new WsMessage<>(MessageType.AttackEventEE.getDesc(), wsMultiNodeDynamic);
        else
            wsMessage = new WsMessage<>(MessageType.AttackEventEA.getDesc(), wsMultiNodeDynamic);
        JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
        situationHandlerService.pushDataToRoom(data, wsMultiNodeDynamic.getSceneId());
    }

    @Override
    public void pushDynamicSchemeToRoom(DynamicSchemeReq dynamicSchemeReq) {
        SceneConfig sceneConfig = sceneConfigMapper.selectBySceneId(dynamicSchemeReq.getSceneId());
        if(sceneConfig == null){
            sceneConfig = new SceneConfig();
            sceneConfig.setSchemeCode(dynamicSchemeReq.getResourceId());
            sceneConfig.setSceneId(dynamicSchemeReq.getSceneId());
            sceneConfigMapper.insert(sceneConfig);
        }else {
            sceneConfig.setSchemeCode(dynamicSchemeReq.getResourceId());
            sceneConfigMapper.updateById(sceneConfig);
        }

        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                List<WsAttackSchedule> wsAttackScheduleList = new ArrayList<>();
                boolean state = true;
                for(DynamicStage dynamicStage:dynamicSchemeReq.getSchemeData()){
                    if (!state)
                        break;

                    WsAttackSchedule wsAttackScheduleStage = new WsAttackSchedule(dynamicStage.getName(),1);
                    wsAttackScheduleList.add(wsAttackScheduleStage);
                    WsAttackSchedule wsAttackMessageSchedule = new WsAttackSchedule("",3);
                    wsAttackScheduleList.add(wsAttackMessageSchedule);
                    //动效序号
                    int number = 1;

                    for(AttackMessageReq attackMessageReq:dynamicStage.getAttackMessageReqList()){

                        SceneConfig nowSceneConfig = sceneConfigMapper.selectBySceneId(dynamicSchemeReq.getSceneId());
                        if(!nowSceneConfig.getSchemeCode().equals(dynamicSchemeReq.getResourceId())){
                            state = false;
                            break;
                        }

                        wsAttackMessageSchedule.setName(number+++attackMessageReq.getDescription());
                        pushAttackScheduleToRoom(wsAttackScheduleList,dynamicSchemeReq.getSceneId());
                        dynamicSchemeMapper.updateScheduleById(JSONObject.toJSONString(wsAttackScheduleList),dynamicSchemeReq.getResourceId());

                        if(attackMessageReq.getType().equals(AttackType.MEDIA_ANIMATION.getKeyWord())){
                            pushMediaToRoom(attackMessageReq);
                        }else{
                            pushAttackToRoom(attackMessageReq);
                        }
                        Thread.sleep(attackMessageReq.getIntervalTime()*1000);
                    }
                    if (state){
                        wsAttackScheduleStage.setStatus(0);
                        wsAttackMessageSchedule.setStatus(2);
                        wsAttackMessageSchedule.setName("");
                        pushAttackScheduleToRoom(wsAttackScheduleList,dynamicSchemeReq.getSceneId());
                        dynamicSchemeMapper.updateScheduleById(JSONObject.toJSONString(wsAttackScheduleList),dynamicSchemeReq.getResourceId());
                    }
                }
                if (state){
                    sceneConfigMapper.deleteBySceneId(dynamicSchemeReq.getSceneId());
                }
                dynamicSchemeMapper.updateScheduleById(null,dynamicSchemeReq.getResourceId());
            }
        });
        thread.start();

    }

    @Override
    public void validDynamicSchemeName(AddDynamicSchemeReq addDynamicSchemeReq) {
        DynamicScheme dynamicScheme = dynamicSchemeMapper.selectByName(addDynamicSchemeReq.getName(),addDynamicSchemeReq.getSceneId());
        if (dynamicScheme != null){
            throw new BusinessException(CrBusinessErrorCode.TEMPLATE_NAME_DUPLICATE);

        }
    }

    @Override
    public List<WsAttackSchedule> getSchedule(String sceneId) {
        String schedule = dynamicSchemeMapper.getScheduleBySceneId(sceneId);
        if (schedule == null){
            return new ArrayList<WsAttackSchedule>();
        }
        return JsonUtil.toJsonObjectArray(schedule,WsAttackSchedule.class);
    }

    @Cacheable(cacheNames = "getElementIds", key = "#sceneId")
    public List<String> getElementIds(String sceneId){
        SceneData sceneData = topoHandlerService.getSceneData(sceneId, null);
        SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);
        List<ElementInfo> elementInfoList = Lists.newArrayList(situationTopo.getElements().values());
        return elementInfoList.stream().map(ElementInfo::getElementId).collect(Collectors.toList());
    }

    public void pushAttackScheduleToRoom(List<WsAttackSchedule> wsAttackScheduleList,String sceneId){
        WsMessage<List<WsAttackSchedule>> wsMessage = new WsMessage<>(MessageType.AttackSchedule.getDesc(), wsAttackScheduleList);
        JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
        situationHandlerService.pushDataToRoom(data, sceneId);
    }

}
