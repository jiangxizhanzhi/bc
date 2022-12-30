package com.cyberpeace.situation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.controller.req.ComponentLayerReq;
import com.cyberpeace.situation.controller.res.ComponentLayerRes;
import com.cyberpeace.situation.dao.entity.ComponentLayer;
import com.cyberpeace.situation.dao.mapper.SituationComponentLayerMapper;
import com.cyberpeace.situation.service.SituationComponentLayerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午10:22
 */
@Service
public class SituationComponentLayerServiceImpl extends ServiceImpl<SituationComponentLayerMapper, ComponentLayer> implements SituationComponentLayerService {

    @Autowired
    private SituationComponentLayerMapper situationComponentLayerMapper;

    @Override
    public String addComponentLayer(List<ComponentLayerReq> componentLayerReqList) {
        if (CollectionUtils.isNotEmpty(componentLayerReqList)) {
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
            situationComponentLayerMapper.batchInsert(componentLayerList);
        }
        return null;
    }

    @Override
    public String updateComponentLayer(ComponentLayerReq componentLayerReq) {
        return null;
    }

    @Override
    public Integer deleteComponentLayer(List<String> idList) {
        return null;
    }

    @Override
    public List<ComponentLayerRes> getComponentLayer(String resourceId) {

        return null;
    }
}
