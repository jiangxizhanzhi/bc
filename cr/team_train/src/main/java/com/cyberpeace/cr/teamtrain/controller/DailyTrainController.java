package com.cyberpeace.cr.teamtrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.req.TemplateReq;
import com.cyberpeace.cr.crbase.controller.rsp.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SceneConfigRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(tags = "日常练习")
@RestController
@Slf4j
@RequestMapping("/cr/daily_train")
@RequiresAuthentication
public class DailyTrainController {

    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private BusinessTemplateService businessTemplateService;
    @Autowired
    private TeamTemplateMapper teamTemplateMapper;

    /**
     * 获取所有靶场的业务模板
     */
    @ApiOperation("查询模板列表")
    @GetMapping("/list")
    @RequiresPermissions(MenuConstant.CMS_DAILY_MODE)
    public BaseRsp<ListRsp<BusinessTemplateRsp>> getTemplate(TemplateReq listReq) {
        BusinessTemplateQuery businessTemplateQuery = BeanUtils.copy(listReq,
                BusinessTemplateQuery.class);
        businessTemplateQuery.setSort("-cr_business_template.create_time");

        IPage<BusinessTemplateData> data = businessTemplateService.page(businessTemplateQuery);

        List<String> ids = new ArrayList<>();
        List<String> userIds = Lists.newArrayList();
        for (BusinessTemplateData businessTemplateData : data.getRecords()) {
            userIds.add(businessTemplateData.getCreateUserId());
            ids.add(businessTemplateData.getResourceId());
        }
        Map<String, String> idNameMap = remoteUserService.getIdNameMap(userIds);
        Map<String, Integer> subTypeMap = StreamApiUtils.toMap(teamTemplateMapper.getTeamTemplateByBusinessIds(ids), TeamTemplate::getBusinessTemplateId, TeamTemplate::getSubType);

        data.convert(businessTemplateData -> {
            BusinessTemplateRsp businessTemplateRsp = BeanUtils.copy(businessTemplateData, BusinessTemplateRsp.class);
            businessTemplateRsp.setDifficult(businessTemplateData.getDifficulty());
            businessTemplateRsp.setCreateUser(idNameMap.get(businessTemplateData.getCreateUserId()));
            businessTemplateRsp.setId(businessTemplateData.getResourceId());
            businessTemplateRsp.setType(businessTemplateData.getBusinessType());
            businessTemplateRsp.setSceneManagerId(businessTemplateData.getTopoId());
            businessTemplateRsp.setShare(businessTemplateData.getOperationAuthType());
            businessTemplateRsp.setShareTeachers(JsonUtil.toJsonObjectArray(businessTemplateData.getOperationAuthIds(), String.class));

            SceneConfigRsp sceneConfigInfo = new SceneConfigRsp();
            sceneConfigInfo.setNodeCount(businessTemplateData.getNodesNum());
            sceneConfigInfo.setResourceOccupiedNums(businessTemplateData.getResourceOccupiedNums());
            sceneConfigInfo.setHasScene(StringUtils.isNotBlank(businessTemplateData.getLocalSceneId()));
            businessTemplateRsp.setSceneConfigInfo(sceneConfigInfo);

            businessTemplateRsp.setCategory(subTypeMap.get(businessTemplateRsp.getResourceId()));
            return businessTemplateRsp;
        });

        return new BaseRsp(PageUtil.getListResult(data));
    }

}
