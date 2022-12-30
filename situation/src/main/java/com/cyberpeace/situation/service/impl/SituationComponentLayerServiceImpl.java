package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.ComponentLayerReq;
import com.cyberpeace.situation.controller.res.ComponentLayerRes;
import com.cyberpeace.situation.dao.entity.ComponentLayer;
import com.cyberpeace.situation.dao.mapper.SituationComponentLayerMapper;
import com.cyberpeace.situation.service.SituationComponentLayerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午10:22
 */
@Service
public class SituationComponentLayerServiceImpl extends ServiceImpl<SituationComponentLayerMapper, ComponentLayer> implements SituationComponentLayerService {

    @Autowired
    private SituationComponentLayerMapper situationComponentLayerMapper;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    public String addComponentLayer(List<ComponentLayerReq> componentLayerReqList) {
        if (CollectionUtils.isNotEmpty(componentLayerReqList)) {
            List<ComponentLayer> componentLayerList = changeToComponentLayer(componentLayerReqList);
            situationComponentLayerMapper.batchInsert(componentLayerList);
        }
        return null;
    }

    @Override
    public String updateComponentLayer(List<ComponentLayerReq> componentLayerReqList) {

        if (CollectionUtils.isNotEmpty(componentLayerReqList)) {
            String templateId = componentLayerReqList.get(0).getTemplateId();
            List<ComponentLayer> oldComponentLayerList = situationComponentLayerMapper.selectByTemplateId(templateId);
            List<String> oldLayerIdList = oldComponentLayerList.stream().map(ComponentLayer::getResourceId).collect(Collectors.toList());
            List<ComponentLayer> componentLayerList = changeToComponentLayer(componentLayerReqList);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    situationComponentLayerMapper.deleteBatchIds(oldLayerIdList);
                    situationComponentLayerMapper.batchInsert(componentLayerList);
                }
            });
        }
        return null;
    }

    @Override
    public Integer deleteComponentLayer(List<String> idList) {
        if (CollectionUtils.isNotEmpty(idList)) {
            situationComponentLayerMapper.deleteBatchIds(idList);
        }
        return idList.size();
    }

    @Override
    public List<ComponentLayerRes> getComponentLayer(String resourceId) {
        List<ComponentLayer> componentLayerList = situationComponentLayerMapper.selectByTemplateId(resourceId);
        List<ComponentLayerRes> componentLayerResList = new ArrayList<>();
        for (ComponentLayer componentLayer : componentLayerList) {
            String componentId = componentLayer.getComponentId();
            ComponentLayerRes copy = BeanUtils.copy(componentLayer, ComponentLayerRes.class);
            if (StringUtils.isNotBlank(componentId)) {
                List<String> componentList = JsonUtil.toJsonObjectArray(componentId, String.class);
                List<ComponentLayer> collect = componentLayerList.stream().filter(componentLayer1 -> componentList.contains(componentLayer1.getResourceId())).collect(Collectors.toList());
                copy.setComponentLayerList(collect);
            }
            componentLayerResList.add(copy);
        }
        return componentLayerResList;
    }

    private List<ComponentLayer> changeToComponentLayer(List<ComponentLayerReq> componentLayerReqList){
        List<ComponentLayer> componentLayerList = new ArrayList<>();
        Integer order = 0 ;
        for (ComponentLayerReq componentLayerReq : componentLayerReqList) {

            componentLayerReq.setResourceId(IDGeneratorUtil.getID());
            componentLayerReq.setOrder(order);
            ComponentLayer componentLayer = BeanUtils.copy(componentLayerReq, ComponentLayer.class);
            List<ComponentLayerReq> combination = componentLayerReq.getCombination();

            //处理组合图层
            if (CollectionUtils.isNotEmpty(combination)) {
                List<String> combinationList = new ArrayList<>();
                combination.forEach(componentLayer1 -> {
                    String resourceId = IDGeneratorUtil.getID();
                    componentLayer1.setResourceId(resourceId);
                    combinationList.add(resourceId);
                    componentLayerList.add(BeanUtils.copy(componentLayer1, ComponentLayer.class));
                });
                componentLayer.setCombination(combinationList.toString());
            }
            componentLayerList.add(componentLayer);
            order ++ ;
        }
        return componentLayerList;
    }
}
