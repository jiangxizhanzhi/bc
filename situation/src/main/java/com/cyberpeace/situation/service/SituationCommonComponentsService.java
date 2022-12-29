package com.cyberpeace.situation.service;

import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.situation.controller.req.ColorThemeDelReq;
import com.cyberpeace.situation.controller.req.CommonComponentsReq;
import com.cyberpeace.situation.controller.req.SituationCommonComponentsPageReq;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsPageRes;
import com.cyberpeace.situation.controller.res.SituationCommonComponentsRes;
import com.cyberpeace.situation.dao.entity.SituationCommonComponents;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author xuxz
 * @Date 16/11/2022 下午5:10
 */
public interface SituationCommonComponentsService {

    /**
     * 获取gis列表
     *
     * @param situationCommonComponentsPageReq
     * @return
     */
    ListRsp<SituationCommonComponentsPageRes> getComponentsPage(SituationCommonComponentsPageReq situationCommonComponentsPageReq);

    /**
     * 获取组件详情
     * @param resourceId
     * @return
     */
    SituationCommonComponentsRes getComponentsInfo(String resourceId);

    /**
     * 新增组件
     * @param commonComponentsReq
     * @return
     */
    String createComponents(CommonComponentsReq commonComponentsReq);

    /**
     * 更新组件
     * @param commonComponentsReq
     * @return
     */
    String updateComponents(CommonComponentsReq commonComponentsReq);

    /**
     * 批量删除组件
     * @param idList
     * @return
     */
    Integer deleteComponents(List<String> idList);
}
