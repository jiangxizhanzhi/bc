package com.cyberpeace.situation.service;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.situation.controller.req.ComponentLayerReq;
import com.cyberpeace.situation.controller.res.ComponentLayerRes;
import com.cyberpeace.situation.dao.entity.ComponentLayer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author xuxz
 * @Date 30/12/2022 上午10:21
 */
public interface SituationComponentLayerService {

    /**
     * 创建图层管理
     * @param componentLayerReqList
     */
    String addComponentLayer(List<ComponentLayerReq> componentLayerReqList);

    /**
     * 更新图层
     * @param componentLayerReq
     * @return
     */
    String updateComponentLayer(ComponentLayerReq componentLayerReq);

    /**
     * 删除图层
     * @param idList
     * @return
     */
    Integer deleteComponentLayer(List<String> idList);

    /**
     * 获取图层
     * @param resourceId
     * @return
     */
    List<ComponentLayerRes> getComponentLayer(String resourceId);
}

