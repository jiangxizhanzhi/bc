package com.cyberpeace.cr.redvsblue.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.redvsblue.dao.SeatSelectDto;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessTask;
import com.cyberpeace.cr.redvsblue.dao.mapper.CrUniteBusinessTaskMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateAdminService;
import com.cyberpeace.cr.redvsblue.vo.BusSeatConfigInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.context.role.CrRoleNode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigRb;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateAddReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbTemplateModifyReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbTemplateRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import static com.cyberpeace.common.enums.BusinessType.UNITE_TRAIN;

/**
 * 红蓝对抗模板管理Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "红蓝对抗模板后台")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/cr/admin/red_vs_blue/red_vs_blue_templates/")
public class RbTemplateAdminController {

    /**
     * 业务模板Service
     */
    @Autowired
	private BusinessTemplateService businessTemplateService;

    /**
     * 红蓝对抗模板Service
     */
    @Autowired
	private RbTemplateService rbTemplateService;

    /**
     * 靶场常用Service
     */
    @Autowired
	private CrCommonService crCommonService;

    /**
     * 任务模板Service
     */
    @Autowired
	private MissionTemplateService missionTemplateService;

    /**
     * 事务模板
     */
    @Autowired
	private TransactionTemplate transactionTemplate;

    /**
     * 红蓝模板信息Service
     */
    @Autowired
	private RbTemplateAdminService rbTemplateAdminService;

    /**
     * 远程日志Service
     */
    @Autowired
	private RemoteLogService remoteLogService;

    /**
     * 业务模板Mapper
     */
    @Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private CrUniteBusinessTaskMapper crUniteBusinessTaskMapper;

	@ApiOperation("查询席位")
	@GetMapping("/seat/list")
	public BaseRsp<List<SeatSelectDto>> listSeat(@RequestParam("resource_id") String resourceId) {
		LambdaQueryWrapper<BusinessTemplate> queryWrapper = new LambdaQueryWrapper<BusinessTemplate>()
				.eq(BusinessTemplate::getResourceId, resourceId);
		List<BusinessTemplate> businessTemplates = businessTemplateMapper.selectList(queryWrapper);
		if (CollectionUtils.isEmpty(businessTemplates)) {
			throw new BusinessException("场景不存在");
		} else {
			BusinessTemplate businessTemplate = businessTemplates.get(0);
			String unitTaskId = businessTemplate.getUniteTaskId();
			List<CrUniteBusinessTask> crUniteBusinessTasks = crUniteBusinessTaskMapper.selectList(new LambdaQueryWrapper<CrUniteBusinessTask>()
					.eq(CrUniteBusinessTask::getTaskId, unitTaskId));
			if (CollectionUtils.isEmpty(crUniteBusinessTasks)) {
				throw new BusinessException("联合训练任务不存在");
			} else {
				List<BusSeatConfigInfoVO> busSeatConfigInfoVOList = JsonUtil.toJsonObjectArray(
						crUniteBusinessTasks.get(0).getSeatConfigList(), BusSeatConfigInfoVO.class);
				if (CollectionUtils.isEmpty(busSeatConfigInfoVOList)) {
					return new BaseRsp<>(new ArrayList<>());
				} else {
					List<SeatSelectDto> seatSelectDtos = new ArrayList<>();
					for (BusSeatConfigInfoVO busSeatConfigInfoVO : busSeatConfigInfoVOList) {
						if (CollectionUtils.isNotEmpty(
								busSeatConfigInfoVO.getChildrens())) {
							for (BusSeatConfigInfoVO child : busSeatConfigInfoVO.getChildrens()) {
								SeatSelectDto seatSelectDto = new SeatSelectDto();
								seatSelectDto.setSeatName(child.getSeatName());
								seatSelectDto.setSeatId(child.getId());
								seatSelectDto.setUserName(child.getSeatUserName());
								seatSelectDtos.add(seatSelectDto);
							}
						}
					}
					return new BaseRsp<>(seatSelectDtos);
				}

			}
		}
	}






    /**
     * 创建
     *
     * @param addReq 新增请求体
     * @return {@code BaseRsp<Object> }
     */
    @ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_RED_VS_BLUE)
	public BaseRsp<Object> create(@RequestBody @Validated RbTemplateAddReq addReq) {

		// set type
		addReq.getBaseInfo().setType(BusinessType.RED_VS_BLUE.getCode());
		// create team template
		RbTemplate rbTemplate = reqToRbTemplate(addReq, new RbTemplate());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// save business template
				String businessTemplateId = businessTemplateService
						.addTemplate(addReq.getBaseInfo());
				// save team template
				rbTemplate.setBusinessTemplateId(businessTemplateId);
				rbTemplateService.save(rbTemplate);
				// update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(businessTemplateId, "",
						rbTemplate.getRoleConfig());
			}
		});

		String rbTrainTemplateName = addReq.getBaseInfo().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.CREATE, rbTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.CREATE,
						LogContent.RB_ROOM_TEMPLATE.getMsg(), rbTrainTemplateName));

		return new BaseRsp<>();
	}

    /**
     * 更新
     *
     * @param modifyReq 修改请求体
     * @return {@code BaseRsp<Boolean> }
     */
    @ApiOperation("修改")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_RED_VS_BLUE)
	public BaseRsp<Boolean> update(@RequestBody @Validated RbTemplateModifyReq modifyReq) {
		String resourceId = modifyReq.getResourceId();
		RbTemplate rbTemplate = rbTemplateService.getRbTemplateByBusinessId(resourceId);
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			log.error("the business template is not exist, id:{}", resourceId);
			throw new ResourceNotFoundException(resourceId);
		}
		String oldRoleConfig = rbTemplate.getRoleConfig();
		//todo 只有非联合的情况才保存红蓝模板信息？
		reqToRbTemplate(modifyReq, rbTemplate);
//		if(!UNITE_TRAIN.getCode().equals(businessTemplate.getBusinessType())) {
//			// update
//		}
		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateTemplate(businessTemplate, modifyReq.getBaseInfo());
				rbTemplateService.updateById(rbTemplate);
				// delete&update missionTemplates
				businessTemplateService.updateMissionTemplateRelation(resourceId, oldRoleConfig,
						rbTemplate.getRoleConfig());
			}
		});

		String rbTrainTemplateName = modifyReq.getBaseInfo().getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.UPDATE, rbTrainTemplateName,
				LogContent.CR_FORMAT.format(OperationType.UPDATE,
						LogContent.RB_ROOM_TEMPLATE.getMsg(), rbTrainTemplateName));

		return new BaseRsp<>();
	}

    /**
     * 批量删除
     *
     * @param deleteReq 删除请求体
     * @return {@code BaseRsp<String> }
     */
    @ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_RED_VS_BLUE)
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		rbTemplateService.batchDelete(deleteReq.getIds());
		return new BaseRsp<>();
	}

    /**
     * 获取红蓝对抗模板
     *
     * @param resourceId 资源id
     * @return {@code BaseRsp<RbTemplateRsp> }
     */
    @ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(value = { MenuConstant.CMS_RED_VS_BLUE, MenuConstant.WEB_CR_RED_VS_BLUE },
			logical = Logical.OR)
	public BaseRsp<RbTemplateRsp> getRbTemplate(@RequestParam("resource_id") String resourceId) {

		businessTemplateService.checkPermission(resourceId,
				UserInfoContext.getUserContext().getUserId());
		RbTemplateRsp teamTrainTemplateRsp = rbTemplateAdminService.getRbTemplate(resourceId);
		return new BaseRsp<>(teamTrainTemplateRsp);
	}




	/**
	 * 请求体自红蓝对抗模板
	 *
	 * @param req        请求体
	 * @param rbTemplate 红蓝对抗模板
	 * @return {@code RbTemplate }
	 */
	private RbTemplate reqToRbTemplate(RbTemplateAddReq req, RbTemplate rbTemplate) {

		CrRoleConfigRb roleConfigRb = BeanUtils.copy(req, CrRoleConfigRb.class);
		BaseInfo baseInfo = req.getBaseInfo();
		// validate role config
		crCommonService.validateRbRoleConfig(roleConfigRb);
		// validate tag
		//暂不校验训练tag，兼容联合红蓝对抗
		//判断请求中是否有席位，有席位不校验训练tag
		if (!(hasSeat(req.getRedRoles()) && hasSeat(req.getBlueRoles()))) {
            rbTemplate.setPreSkill(CrCommonUtil.handleTrainingTag(baseInfo.getPreSkill()));
            rbTemplate.setTrainSkill(CrCommonUtil.handleTrainingTag(baseInfo.getTrainSkill()));
        }
        rbTemplate.setSubType(baseInfo.getCategory());
		rbTemplate.setDurationTime(baseInfo.getDurationTime());
		rbTemplate.setRoleConfig(CrCommonUtil.roleConfig2DbJson(roleConfigRb));

		return rbTemplate;
	}

	//判断请求中是否有席位

	private Boolean hasSeat(List<CrRoleConfig> Roles) {
		for (CrRoleConfig crRoleConfig : Roles) {
			if (crRoleConfig.getSeatsInfo() != null) {
				return true;
			}
		}
		return false;
	}
}
