package com.cyberpeace.cr.teamtrain.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateAddReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateModifyReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private TeamTemplateService teamTemplateService;
    @Autowired
    private RemoteLogService remoteLogService;


    @ApiOperation("新增")
    @PostMapping()
    @RequiresPermissions(MenuConstant.CMS_DAILY_MODE)
    public BaseRsp create(@RequestBody @Validated TeamTrainTemplateAddReq addReq) {
        // set type
        addReq.getBaseInfo().setType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
        // create team template
        TeamTemplate teamTemplate = reqToTeamTemplate(addReq, new TeamTemplate());
        teamTemplate.setTrainCategory(addReq.getTrainCategory());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // save business template
                String businessTemplateId = businessTemplateService.addTemplate(addReq.getBaseInfo(), OperationModule.DAILY_TRAIN.getCode());
                // save team template
                teamTemplate.setBusinessTemplateId(businessTemplateId);
                teamTemplateService.save(teamTemplate);
                // update related missionTemplates
                businessTemplateService.updateMissionTemplateRelation(businessTemplateId, "",
                        teamTemplate.getRoleConfig());
            }
        });

        String teamTrainTemplateName = addReq.getBaseInfo().getName();

        remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.DAILY_TRAIN,
                OperationType.CREATE, teamTrainTemplateName,
                LogContent.CR_FORMAT.format(OperationType.CREATE, teamTemplate.getLogMsg(), teamTrainTemplateName));

        return new BaseRsp<>();
    }


    @ApiOperation("修改")
    @PatchMapping()
    @RequiresPermissions(MenuConstant.CMS_DAILY_MODE)
    public BaseRsp update(@RequestBody @Validated TeamTrainTemplateModifyReq modifyReq) {
        String resourceId = modifyReq.getResourceId();
        TeamTemplate teamTemplate = teamTemplateService.getByTemplateBusinessId(resourceId);
        BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
        if (businessTemplate == null) {
            throw new ResourceNotFoundException(resourceId);
        }
        // update
        String oldRoleConfig = teamTemplate.getRoleConfig();
        reqToTeamTemplate(modifyReq, teamTemplate);

        // save
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                businessTemplateService.updateTemplate(businessTemplate, modifyReq.getBaseInfo());
                teamTemplateService.updateById(teamTemplate);
                // delete&update related missionTemplates
                businessTemplateService.updateMissionTemplateRelation(resourceId, oldRoleConfig,
                        teamTemplate.getRoleConfig());
            }
        });

        String teamTrainTemplateName = modifyReq.getBaseInfo().getName();
        remoteLogService.addOjLog(UserInfoContext.getUserContext(), teamTemplate.getModule(),
                OperationType.UPDATE, teamTrainTemplateName,
                LogContent.CR_FORMAT.format(OperationType.UPDATE, teamTemplate.getLogMsg(), teamTrainTemplateName));

        return new BaseRsp<>();
    }

    private TeamTemplate reqToTeamTemplate(TeamTrainTemplateReq req, TeamTemplate teamTemplate) {

        List<CrRoleConfig> roleConfigs = req.getRoleList();
        BaseInfo baseInfo = req.getBaseInfo();
        // 校验角色名称是否重复
        CrCommonValidator.checkStringListDuplicate(roleConfigs.stream().map(CrRoleConfig::getName).collect(Collectors.toList()),CrBusinessErrorCode.ROLE_NAME_DUPLICATE);
        teamTemplate.setPreSkills(JsonUtil.arrayToString(new ArrayList<>()));
        teamTemplate.setTrainSkills(JsonUtil.arrayToString(new ArrayList<>()));

        // 校验 tag
        teamTemplate.setSubType(baseInfo.getCategory());
        teamTemplate.setSceneType(baseInfo.getSceneType());
        teamTemplate.setPeopleNumber(baseInfo.getPeopleNumber());
        teamTemplate.setDurationTime(baseInfo.getDurationTime());
        teamTemplate.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigs));
        teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(req.getTimeLineConfig()));

        return teamTemplate;
    }

}
