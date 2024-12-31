package com.cyberpeace.cr.evaluation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.context.user.OperationUserInfo;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.model.AuthTable;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateIdRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTemplateTaskListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalToolMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.*;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.MissionStartContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "靶场测评-模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/template")
public class EvalTemplateController {

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private EvalToolMapper evalToolMapper;

	@Autowired
	private EvalTaskStepService evalTaskStepService;

	@ApiOperation("模板新增")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("")
	public BaseRsp<EvalTemplateIdRsp>
			addEvalTemplate(@RequestBody @Validated EvaTemplateAddReq evaTemplateAddReq) {

		EvalTemplate evalTemplate = new EvalTemplate();

		reqToTemplate(evaTemplateAddReq, evalTemplate);
		// 设置模板创建者
		evalTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		evalTemplateService.save(evalTemplate);

		return new BaseRsp<>(new EvalTemplateIdRsp(evalTemplate.getResourceId()));
	}

	@ApiOperation("模板修改")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PatchMapping("")
	public BaseRsp<EvalTemplateIdRsp>
			modifyEvalTemplate(@RequestBody @Validated EvaTemplateModifyReq evaTemplateModifyReq) {

		String templateId = evaTemplateModifyReq.getResourceId();

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		reqToTemplate(evaTemplateModifyReq, evalTemplate);

		evalTemplateService.updateById(evalTemplate);

		return new BaseRsp<>(new EvalTemplateIdRsp(templateId));
	}

	@ApiOperation("手动执行")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/manual/exe")
	public BaseRsp<String>
			exeCommandManual(@RequestBody @Validated ManualExecutionReq manualExecutionReq) {

		String taskId = manualExecutionReq.getTaskId();
		EvalTask evalTask = evalTaskMapper.selectById(taskId);
		String phase = evalTask.getPhase();
		List<EvalTaskStepConfig> stages = JsonUtil.toJsonObjectArray(phase, EvalTaskStepConfig.class);
		Map<Integer, EvalTaskStepConfig> map = StreamApiUtils.toMap(stages, EvalTaskStepConfig::getStage, s -> s);
		EvalTaskStepConfig evalTaskStepConfig = map.get(manualExecutionReq.getStage());
		// 执行任务
		MissionStartContext missionStartContext = new MissionStartContext();
		missionStartContext.setCommand(manualExecutionReq.getCommand());
		missionStartContext.setNode_id(manualExecutionReq.getNodeId());
		missionStartContext.setExec_user(UserInfoContext.getUserContext().getUserId());
		missionStartContext.setId(evalTask.getMpSceneId());
		evalTemplateService.executeCommond(missionStartContext, manualExecutionReq.getMissionId(), manualExecutionReq.getTaskId(), evalTaskStepConfig.getTitle());

		int stageIndex = stages.indexOf(evalTaskStepConfig);
		List<ToolBo> toolBos = evalTaskStepConfig.getEquip();
		Map<Integer, ToolBo> toolBoMap = StreamApiUtils.toMap(toolBos, ToolBo::getToolNum, a -> a);
		ToolBo toolBo = toolBoMap.get(manualExecutionReq.getEquip());
		int index = toolBos.indexOf(toolBo);
		toolBo.setExecuteTime(new Date());
		toolBo.setIsExecute(true);
		toolBos.set(index, toolBo);
		evalTaskStepConfig.setEquip(toolBos);
		stages.set(stageIndex, evalTaskStepConfig);
		evalTask.setPhase(JsonUtil.toJsonString(stages));
		evalTaskMapper.updateById(evalTask);
		return new BaseRsp<>("success");
	}

	@ApiOperation("试验阶段列表")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/phase/list")
	public BaseRsp<List<EvalTaskStepConfig>>
			getPhaseLists(@ApiParam(name = "template_id", value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId,
						  @ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(value = "task_id") String taskId) {

		String phase;
		if(StringUtils.isEmpty(taskId)){
			EvalTemplate evalTemplate = evalTemplateService.getById(templateId);
			phase = evalTemplate.getStepConfig();
		} else {
			EvalTask evalTask = evalTaskMapper.selectById(taskId);
			phase = evalTask.getPhase();
		}
		if (StringUtils.isEmpty(phase)) {
			return new BaseRsp<>(new ArrayList<>());
		}

		List<EvalTaskStepConfig> stages = JsonUtil.toJsonObjectArray(phase, EvalTaskStepConfig.class);
		return new BaseRsp<>(stages);
	}

	@ApiOperation("模板查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("")
	public BaseRsp<EvalTemplateRsp> getEvalTemplate(@ApiParam(name = "template_id",
			value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId) {

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		// 构造接口返回数据
		EvalTemplateRsp evalTemplateRsp = BeanUtils.copy(evalTemplate, EvalTemplateRsp.class);
		evalTemplateRsp.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
		evalTemplateRsp.setSceneConfigData(localSceneService.getTopoData(evalTemplate.getTopoId()));
		evalTemplateRsp.setMethod(evalTemplate.getMethod());
		evalTemplateRsp.setExpCondition(evalTemplate.getExpCondition());
		evalTemplateRsp.setTarget(evalTemplate.getTarget());
		evalTemplateRsp.setIsTem(evalTemplate.getIsTem());
		// auth users
		List<UserInfo> userInfos = remoteUserService.getByIdList(evalTemplate.getAuthIds());
		List<OperationUserInfo> operationUserInfos = StreamApiUtils.map(userInfos, userInfo -> {
			OperationUserInfo operationUserInfo = BeanUtils.copy(userInfo, OperationUserInfo.class);
			operationUserInfo.setId(userInfo.getUserId());
			operationUserInfo.setUsername(userInfo.getUserName());
			return operationUserInfo;
		});
		evalTemplateRsp.setOperationAuthIds(operationUserInfos);
		return new BaseRsp<>(evalTemplateRsp);
	}

	@ApiOperation("模板列表查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalTemplateListRsp>>
			getEvalTemplateList(EvalTemplateListQuery evalTemplateListQuery) {
		IPage<EvalTemplate> data = evalTemplateService.list(evalTemplateListQuery);
		data.convert(evalTemplate -> {
			EvalTemplateListRsp evalTemplateListRsp = BeanUtils.copy(evalTemplate,
					EvalTemplateListRsp.class);
			evalTemplateListRsp
					.setLogoPath(evalTemplateService.getLogoPath(evalTemplate.getLogo()));
			return evalTemplateListRsp;
		});
		return new BaseRsp<>(PageUtil.getListResult(data));
	}

	@ApiOperation("模板任务列表查询")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/task_list")
	public BaseRsp<EvalTemplateTaskListRsp> getEvalTemplateTaskList(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "is_finished", value = "是否只展示已结束任务", required = false) @RequestParam(
					value = "is_finished", required = false) Boolean isFinished) {

		// 校验用户身份并获取测评靶场模板实体
		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		EvalTemplateTaskListRsp evalTemplateTaskListRsp = new EvalTemplateTaskListRsp();

		evalTemplateTaskListRsp
				.setTaskList(evalTemplateService.listTask(evalTemplate, isFinished));

		if (StringUtils.isNotEmpty(evalTemplate.getCurrentTaskId())) {
			evalTemplateTaskListRsp.setCurrentTaskId(evalTemplate.getCurrentTaskId());
		}

		return new BaseRsp<>(evalTemplateTaskListRsp);
	}

	@ApiOperation("模板删除")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTemplate(
			@RequestBody @Validated EvalTemplateDeleteReq evalTemplateDeleteReq) {

		Boolean result = evalTemplateService.batchDelete(evalTemplateDeleteReq.getTemplateIds());

		return new BaseRsp<>(result);
	}

	@ApiOperation("模板基础信息校验")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("validate_info")
	public BaseRsp<Boolean> validateBaseInfo(
			@RequestBody @Validated EvalTemplateBaseInfoContext evalTemplateBaseInfoContext) {
		Boolean result = evalTemplateService.validateBaseInfo(evalTemplateBaseInfoContext);
		return new BaseRsp<>(result);
	}
	@ApiOperation("查询工具列表")
	@GetMapping("/tool")
	public BaseRsp<List<EvalTool>> getTool() {
		return new BaseRsp<>(evalToolMapper.selectList(new LambdaQueryWrapper<>()));
	}

	@ApiOperation("查询告警事件")
	@GetMapping("/alarm")
	public BaseRsp<ListRsp<CombatLog>>getAlarm(AlarmPageReq pageReq){
		if (pageReq.getSceneId()!=null) {
			pageReq.setSceneId(pageReq.getSceneId());
		}else {
			String mpSceneId = localSceneService.getByTaskId(pageReq.getTaskId());
			pageReq.setSceneId(mpSceneId);
		}
		return evalTemplateService.getAlarm(pageReq);
	}

	@ApiOperation("查询告警事件")
	@GetMapping("/alarms")
	public BaseRsp<ListRsp<CombatLog>>getAlarms(AlarmPageReq pageReq){
		if (pageReq.getSceneId()!=null) {
			pageReq.setSceneId(pageReq.getSceneId());
		}else {
			String mpSceneId = localSceneService.getByTaskId(pageReq.getTaskId());
			pageReq.setSceneId(mpSceneId);
		}
		return evalTemplateService.getAlarms(pageReq);
	}

	@ApiOperation("查询grade转换name")
	@GetMapping("/gradeConName")
	public BaseRsp<List<CodeConversionNameReq>>getGradeAndName(){
		List<CodeConversionNameReq> list = Lists.newArrayList();
		list.add(CodeConversionNameReq.builder().code(1).name("高危").build());
		list.add(CodeConversionNameReq.builder().code(2).name("中危").build());
		list.add(CodeConversionNameReq.builder().code(3).name("低危").build());
		return new BaseRsp<>(list);

	}

	@ApiOperation("试验阶段列表带有status")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/phase/list/ll")
	public BaseRsp<List<EvalTaskStepContext>>
	getPhaseListsByTem(@ApiParam(name = "template_id", value = "测评靶场模板id", required = true) @RequestParam("template_id") String templateId,
				  @ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(value = "task_id") String taskId) {

		String phase;
		List<EvalTaskStepContext> evalTaskStepContextList = Lists.newArrayList();
		if(StringUtils.isEmpty(taskId)){
			EvalTemplate evalTemplate = evalTemplateService.getById(templateId);
			evalTaskStepContextList = evalTaskStepService
					.getTemplateSteps(evalTemplate.getStepConfig());
			phase = evalTemplate.getStepConfig();
			Map<String, List<ToolBo>> map = evalTaskStepContextList.stream().collect(Collectors.toMap(EvalTaskStepContext::getTitle, EvalTaskStepContext::getEquip));

			List<EvalTaskStepConfig> stages = JsonUtil.toJsonObjectArray(phase, EvalTaskStepConfig.class);
			stages.stream().map(s->{
				if (map.containsKey(s.getTitle())) {
					map.put(s.getTitle(),s.getEquip());
				}
				return map;
			});
			evalTaskStepContextList.stream().map(e->{
				if (map.containsKey(e.getTitle())) {
					e.setEquip(map.get(e.getTitle()));
				}
				return map;
			});
		} else {
			EvalTask evalTask = evalTaskMapper.selectById(taskId);
			evalTaskStepContextList = evalTaskStepService.getTaskSteps(taskId);
			phase = evalTask.getPhase();
			List<EvalTaskStepConfig> stages = JsonUtil.toJsonObjectArray(phase, EvalTaskStepConfig.class);
			Map<String, List<ToolBo>> map = stages.stream().collect(Collectors.toMap(EvalTaskStepConfig::getTitle, EvalTaskStepConfig::getEquip));
			for (EvalTaskStepConfig eva : stages) {
				for (ToolBo toolBo : eva.getEquip()) {
					Boolean isExecute = toolBo.getIsExecute();
					if(Boolean.TRUE.equals(isExecute)){
						if(TimeUtil.addSeconds(toolBo.getExecuteTime(), 30).compareTo(new Date()) > 0){
							toolBo.setLimitTime((int)(TimeUtil.addSeconds(toolBo.getExecuteTime(), 30).getTime() - System.currentTimeMillis())/1000);
						} else{
							toolBo.setLimitTime(0);
							toolBo.setIsExecute(false);

						}
					}
				}
			}
			for (int i = 0; i < evalTaskStepContextList.size(); i++) {
				if (map.containsKey(evalTaskStepContextList.get(i).getTitle())) {
					evalTaskStepContextList.get(i).setEquip(map.get(evalTaskStepContextList.get(i).getTitle()));
				}
			}
		}
		if (StringUtils.isEmpty(phase)) {
			return new BaseRsp<>(new ArrayList<>());
		}

		return new BaseRsp<>(evalTaskStepContextList);
	}

	private EvalTemplate reqToTemplate(EvaTemplateAddReq evaTemplateAddReq,
			EvalTemplate evalTemplate) {

		// 校验测评模板配置
		validateReq(evaTemplateAddReq);

		// 复制模板信息
		BeanUtils.copyProperties(evaTemplateAddReq, evalTemplate);
		evalTemplate.setStepConfig(JsonUtil.arrayToString(evaTemplateAddReq.getStepConfig()));

		// 设置模板用户
		if (evaTemplateAddReq.getOperationAuthType().equals(AuthTable.AuthType.CUSTOM_SHARE_MODE.getCode())) {
			evalTemplate.setOperationAuthIds(
					JsonUtil.arrayToString(evaTemplateAddReq.getAuthUserIds()));
		}

		// 设置模板拓扑
		evalTemplate.setTopoId(crSceneService.saveTopo(evaTemplateAddReq.getSceneConfigData(),
				BusinessCode.RANGE_EVALUATION, evalTemplate.getTopoId()));

		return evalTemplate;
	}

	private void validateReq(EvaTemplateAddReq evaTemplateAddReq) {

		// 校验基础信息
		EvalTemplateBaseInfoContext evalTemplateBaseInfoContext = BeanUtils.copy(evaTemplateAddReq,
				EvalTemplateBaseInfoContext.class);
		evalTemplateService.validateBaseInfo(evalTemplateBaseInfoContext);

		// 校验测评步骤配置
		evalTemplateService.validateStepConfig(evaTemplateAddReq.getStepConfig());

		CrCommonValidator.validateEnumCode(evaTemplateAddReq.getOperationAuthType(),
				AuthTable.AuthType.class);
	}

}