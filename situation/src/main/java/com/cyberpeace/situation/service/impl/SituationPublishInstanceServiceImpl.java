package com.cyberpeace.situation.service.impl;

import java.util.*;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.cyberpeace.situation.constant.dashboard.*;
import com.cyberpeace.situation.constant.gis.MapSource;
import com.cyberpeace.situation.constant.gis.MapView;
import com.cyberpeace.situation.controller.res.ApiRequestInfoRes;
import com.cyberpeace.situation.model.outer.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.constant.BcType;
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.model.situation.BcContext;
import com.cyberpeace.integratation.model.situation.SubjectContext;
import com.cyberpeace.integratation.model.situation.TopoContext;
import com.cyberpeace.integratation.model.situation.req.SubjectListReq;
import com.cyberpeace.integratation.model.situation.req.TopoReq;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.bo.situation.SchemeVisualizationBo;
import com.cyberpeace.situation.constant.*;
import com.cyberpeace.situation.controller.req.ReplayInstanceReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceAddReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceFilterReq;
import com.cyberpeace.situation.controller.req.SituationPublishInstanceUpdateReq;
import com.cyberpeace.situation.controller.res.DashboardTemplateRes;
import com.cyberpeace.situation.controller.res.SituationPublishInstancePageRes;
import com.cyberpeace.situation.dao.entity.*;
import com.cyberpeace.situation.dao.mapper.*;
import com.cyberpeace.situation.service.DashboardTemplateRefService;
import com.cyberpeace.situation.service.DashboardTemplateService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class SituationPublishInstanceServiceImpl extends ServiceImpl<SituationPublishInstanceMapper, SituationPublishInstance> implements SituationPublishInstanceService {


    @Autowired
    private SituationPublishInstanceMapper situationPublishInstanceMapper;

    @Autowired
    private DashboardTemplateService dashboardTemplateService;

    @Autowired
    private SituationCategoryMapper situationCategoryMapper;

    @Autowired
    private DashboardTemplateMapper dashboardTemplateMapper;

    @Autowired
    private DashboardTemplateRefMapper dashboardTemplateRefMapper;

    @Autowired
    private DashboardTemplateRefService dashboardTemplateRefService;

    @Autowired
    private RemoteSituationService remoteSituationService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private DashboardAttributeMapper dashboardAttributeMapper;

    @Autowired
    private TopoConfigMapper topoConfigMapper;

    @Autowired
    private RemoteUserService remoteUserService;

	@Override
	public SituationPublishInstance selectReleasedXdDashboardBySubjectId(String taskId,
			String subjectId) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectXdDashboardBySubjectId(taskId, subjectId);
		if (null == situationPublishInstance) {
			throw new BusinessException(SituationErrorCode.NO_RELEASED_DASHBOARD);
		}

		return situationPublishInstance;
	}

	@Override
	public SituationPublishInstance addReplayInstance(ReplayInstanceReq replayInstanceReq) {

		// 复制大屏属性
		SituationPublishInstance situationPublishInstance = BeanUtils.copy(replayInstanceReq,
				SituationPublishInstance.class);

		// 设置用户信息
		situationPublishInstance.setCreateUserId(remoteUserService.getCurrentUserId());

		// 设置基础信息
		situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());
		situationPublishInstance.setPublicStatus(PublicStatus.PREPARATION.getCode());

		DashboardAttribute dashboardAttribute = BeanUtils.copy(replayInstanceReq,
				DashboardAttribute.class);
		dashboardAttribute.setResourceId(IDGeneratorUtil.getID());
		dashboardAttribute.setDashboardMode(DashboardMode.SPG.getCode());
		dashboardAttribute.setTitle(replayInstanceReq.getName());
		dashboardAttribute.setMapSource(MapSource.GGIM.getCode());
		situationPublishInstance.setAttributeId(dashboardAttribute.getResourceId());

		// 设置类型
		String taskType = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getDesc().equals(replayInstanceReq.getTaskType())).findFirst()
				.orElse(TaskType.QB).getCode();

		dashboardAttribute.setTaskType(taskType);

		situationPublishInstance.setCategory(getCategoryByTaskType(taskType));

		// 从大屏模板中拷贝属性
		String templateId = replayInstanceReq.getTemplateId();

		// 处理模板数据
		setInstanceTemplateInfo(templateId, situationPublishInstance, dashboardAttribute);

		// 写入数据库
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					situationPublishInstanceMapper.insert(situationPublishInstance);
					dashboardAttributeMapper.insert(dashboardAttribute);
				} catch (DuplicateKeyException e) {
					throw new BusinessException(ErrorCode.NAME_DUPLICATE);
				}
			}
		});
		return situationPublishInstance;
	}

	@Override
	public SituationPublishInstance updateReplayInstance(ReplayInstanceReq replayInstanceReq) {

		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectById(replayInstanceReq.getResourceId());

		// 获取旧模板id
		String oldTemplateId = situationPublishInstance.getTemplateId();

		// 只允许修改名称、描述和缩略图、模板、特定事件
		situationPublishInstance.setName(replayInstanceReq.getName());
		situationPublishInstance.setDescription(replayInstanceReq.getDescription());
		situationPublishInstance.setThumbnail(replayInstanceReq.getThumbnail());
		situationPublishInstance.setEventList(replayInstanceReq.getEventList());

		String templateId = replayInstanceReq.getTemplateId();
		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId(situationPublishInstance.getAttributeId());
		setInstanceTemplateInfo(templateId, situationPublishInstance, dashboardAttribute);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 如果模板发生了变化，删除旧模板数据
				if (!oldTemplateId.equals(templateId)) {
					dashboardTemplateRefMapper.deleteById(oldTemplateId);
				}

				// 更新态势实例数据
				situationPublishInstanceMapper.updateById(situationPublishInstance);
				dashboardAttributeMapper.updateById(dashboardAttribute);
			}
		});
		return situationPublishInstance;
	}

    @Override
    public SituationPublishInstance insertOne(SituationPublishInstanceAddReq situationPublishInstanceAddReq, Boolean isXd) {

        // 复制大屏属性
        SituationPublishInstance situationPublishInstance = BeanUtils.copy(situationPublishInstanceAddReq,
                SituationPublishInstance.class);

        // 校验参数
        checkParams(situationPublishInstanceAddReq);

        // 写入基本信息
        String userId = remoteUserService.getCurrentUserId();
        situationPublishInstance.setCreateUserId(userId);

        // 大屏新增后默认为草稿
        situationPublishInstance.setStatus(SituationInstanceStatus.DRAFT.getCode());

        // 默认为未下发状态
        situationPublishInstance.setPublicStatus(PublicStatus.PREPARATION.getCode());

        // 构造大屏属性表
        DashboardAttribute dashboardAttribute = BeanUtils.copy(situationPublishInstanceAddReq, DashboardAttribute.class);
        dashboardAttribute.setResourceId(IDGeneratorUtil.getID());
        dashboardAttribute.setDashboardMode(DashboardMode.SPG.getCode());

		// 建立实例与属性关联关系
		situationPublishInstance.setAttributeId(dashboardAttribute.getResourceId());

        // 默认使用谷歌地图源
        dashboardAttribute.setMapSource(MapSource.GGIM.getCode());

        // 从可视化方案中拷贝属性
        SchemeVisualizationBo scheme = remoteSituationService.getScheme(situationPublishInstanceAddReq.getSchemeId());

        // 设置任务关联信息
		situationPublishInstance.setSubRangeId(scheme.getSubRangeId());
        situationPublishInstance.setSubRangeName(scheme.getSubRangeName());
        situationPublishInstance.setTaskName(scheme.getTaskName());
        situationPublishInstance.setSubjectName(scheme.getSubjectName());

        // 设置科目关联信息
		dashboardAttribute.setEnvId(scheme.getEnvId());
        dashboardAttribute.setStartTime(TimeUtil.toDate1(scheme.getStartTime()));
        dashboardAttribute.setSchemeName(scheme.getSchemeName());
        dashboardAttribute.setSchemeDesc(scheme.getSchemeDesc());
        dashboardAttribute.setTaskType(scheme.getTaskType());

		// 设置名称属性
        String rangeName = StringUtils.isBlank(scheme.getSubRangeName()) ? "联管中心" : scheme.getSubRangeName();
        String taskName = scheme.getTaskName();
        dashboardAttribute.setTitle(rangeName + "任务态势");
        dashboardAttribute.setSubTitle(taskName);

		// 设置任务子类型
		if (TaskType.SYZZ.getCode().equals(scheme.getTaskType())) {
			dashboardAttribute.setTaskSubType("1");
		}

        // 设置大屏名称
        if (StringUtils.isBlank(situationPublishInstance.getName())) {
            situationPublishInstance.setName(scheme.getSchemeName());
        }

		// 设置描述
		if (StringUtils.isBlank(situationPublishInstance.getDescription())) {
			situationPublishInstance.setDescription(scheme.getSchemeDesc());
		}

        // 从大屏模板中拷贝属性
        String templateId = situationPublishInstanceAddReq.getTemplateId();
        // 模板不存在时获取默认模板
        if (templateId == null) {
            String defaultTemplateId = dashboardTemplateService.getDefaultTemplateId(scheme.getTaskType());
            templateId = dashboardTemplateRefService.createDashboardTemplate(defaultTemplateId).getResourceId();
        }

		// 所有大屏初始默认可编辑
        situationPublishInstance.setEdit(true);

		// 标记大屏来源是否为想定跳转
        situationPublishInstance.setXd(isXd);

        // 处理大屏类型字段
		situationPublishInstance.setCategory(getCategoryByTaskType(scheme.getTaskType()));

		// 处理模板数据
		setInstanceTemplateInfo(templateId, situationPublishInstance, dashboardAttribute);

        // 写入数据库
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    situationPublishInstanceMapper.insert(situationPublishInstance);
                    dashboardAttributeMapper.insert(dashboardAttribute);
                    positionAdjust(situationPublishInstance.getSubRangeId(), situationPublishInstance.getTaskId(),
                            situationPublishInstance.getSubjectId(), dashboardAttribute.getEnvId());
                } catch (DuplicateKeyException e) {
                    log.error("新增大屏错误",e);
                    throw new BusinessException(ErrorCode.NAME_DUPLICATE);
                }
            }
        });
        return situationPublishInstance;
    }

	private void setInstanceTemplateInfo(String templateId, SituationPublishInstance instance,
			DashboardAttribute attribute) {

		// 提取模板中有效信息至大屏参数
		DashboardTemplateRes dashboardTemplateInfo = dashboardTemplateRefService
				.getDashboardTemplateInfo(templateId, null, Boolean.FALSE);
		Integer level = dashboardTemplateInfo.getLevel();
		Integer refresh = dashboardTemplateInfo.getRefresh();
		Boolean editStatus = dashboardTemplateInfo.getEditStatus();
		attribute.setLevel(level == null ? DashboardLevel.RW.getCode() : level);
		attribute.setRefresh(refresh == null ? 5 : refresh);
		attribute.setDashboardType(
				editStatus ? DashboardType.ZB.getCode() : DashboardType.DZ.getCode());

		// 从模板中提取GIS视角信息
		attribute.setViewAngle(JsonUtil.toJsonString(dashboardTemplateInfo.getViewAngle()));

		// 提取模板中有效信息至大屏实例
		instance.setThumbnail(dashboardTemplateInfo.getThumbnail());
		instance.setTemplateId(dashboardTemplateInfo.getResourceId());
	}

	private String getCategoryByTaskType(String taskType){
		// 处理实例类型
		SituationCategory situationCategory = situationCategoryMapper.selectByTaskType(taskType);
		String category;

		if (situationCategory != null) {
			category = situationCategory.getResourceId();
		} else {
			TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
					.filter(t -> t.getCode().equals(taskType)).findFirst().orElse(TaskType.QB);
			SituationCategory newCategory = new SituationCategory(type.getName(), type.getCode(), null);
			category = IDGeneratorUtil.getID();
			newCategory.setResourceId(category);
			situationCategoryMapper.insert(newCategory);
		}

		return category;
	}

    private void positionAdjust(String subRangeId, String taskId, String subjectId, String envId) {

        // 不重复处理拓扑坐标
        TopoConfig topoConfig = topoConfigMapper.selectByEnvId(envId);
        if (topoConfig != null) {
            return;
        }

        TopoReq req = new TopoReq(subRangeId, taskId, subjectId, envId);
        TopoContext topoContext = remoteSituationService.getTopo(req, true);
        topoConfig = new TopoConfig(subRangeId, taskId, subjectId, envId, topoContext);
        topoConfigMapper.insert(topoConfig);
    }

    private void checkParams(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {
        // 校验模板实例是否存在
        checkTemplate(situationPublishInstanceAddReq);
    }

    private void checkTemplate(SituationPublishInstanceAddReq situationPublishInstanceAddReq) {

        // 如果没有模板ID，直接跳过校验，主要用于想定接入
        if (situationPublishInstanceAddReq.getTemplateId() == null){
            return;
        }

        DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefMapper.selectById(situationPublishInstanceAddReq.getTemplateId());
        if (null == dashboardTemplateRef) {
            throw new BusinessException(SituationErrorCode.TEMPLATE_INVALID_ERROR);
        }
    }

    @Override
    public SituationPublishInstance updateOne(SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq) {

        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(situationPublishInstanceUpdateReq.getResourceId());
        if (null == situationPublishInstance) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }

        // 校验变更项，暂不允许修改任务信息
        if (!Objects.equals(situationPublishInstance.getSchemeId(), situationPublishInstanceUpdateReq.getSchemeId())) {
            throw new BusinessException(SituationErrorCode.CAN_NOT_MODIFY_TASK);
        }

        //获取旧模板id
        String oldTemplateId = situationPublishInstance.getTemplateId();

        // 只允许修改名称、描述和缩略图、模板、特定事件
        situationPublishInstance.setName(situationPublishInstanceUpdateReq.getName());
        situationPublishInstance.setDescription(situationPublishInstanceUpdateReq.getDescription());
        situationPublishInstance.setThumbnail(situationPublishInstanceUpdateReq.getThumbnail());
        situationPublishInstance.setEventList(situationPublishInstanceUpdateReq.getEventList());

		String templateId = situationPublishInstanceUpdateReq.getTemplateId();
        DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getByResourceId(situationPublishInstance.getAttributeId());
		setInstanceTemplateInfo(templateId, situationPublishInstance, dashboardAttribute);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // 如果模板发生了变化，删除旧模板数据
                if (!oldTemplateId.equals(templateId)){
                    dashboardTemplateRefMapper.deleteById(oldTemplateId);
                }

				// 更新态势实例数据
                situationPublishInstanceMapper.updateById(situationPublishInstance);
				dashboardAttributeMapper.updateById(dashboardAttribute);
            }
        });

        return null;
    }

    @Override
	public void deleteUnusedSituation() {
        List<String> list = dashboardTemplateRefMapper.selectAllId();
        dashboardTemplateRefService.deleteDashboardTemplate(list);
    }

    @Override
    public ListRsp<SituationPublishInstancePageRes> getSituationPublishInstancePage(SituationPublishInstanceFilterReq situationPublishInstanceFilterReq) {

        Page page = situationPublishInstanceFilterReq.toPageParam();

        //获取分页数据
        IPage<SituationPublishInstancePageRes> situationPublishInstancePage = situationPublishInstanceMapper.getSituationPublishInstancePage(page, situationPublishInstanceFilterReq);
        List<SituationPublishInstancePageRes> situationPublishInstancePageResList = situationPublishInstancePage.getRecords();

        //任务类型id列表
        List<SituationCategory> categories = situationCategoryMapper.selectList(new LambdaQueryWrapper<>());

        //模板列表
        List<DashboardTemplateRef> dashboardTemplateRefs = dashboardTemplateRefMapper.selectList(new LambdaQueryWrapper<>());

        SubjectListReq subjectListReq = new SubjectListReq();

        subjectListReq.setSubjectIds(StreamApiUtils.map(situationPublishInstancePageResList,
                SituationPublishInstancePageRes::getSubjectId));
        List<SubjectContext> subjectList = new ArrayList<>();
        if (subjectListReq.getSubjectIds().size() != 0) {
            //任务科目列表
            subjectList = remoteSituationService.getSubjectList(subjectListReq);
        }


        List<DashboardAttribute> dashboardAttributes = dashboardAttributeMapper.selectList(null);
        Map<String, DashboardAttribute> stringDashboardAttributeMap = StreamApiUtils.toMap(dashboardAttributes, DashboardAttribute::getResourceId, dashboardAttribute -> dashboardAttribute);
        //填充相关名称
        if (CollectionUtils.isNotEmpty(situationPublishInstancePageResList)) {
            for (SituationPublishInstancePageRes situationPublishInstancePageRes : situationPublishInstancePageResList) {
                String category = situationPublishInstancePageRes.getCategory();
                String templateId = situationPublishInstancePageRes.getTemplateId();
                String taskId = situationPublishInstancePageRes.getTaskId();
                String subjectId = situationPublishInstancePageRes.getSubjectId();

                situationPublishInstancePageRes.setButton();

                // 赋值大屏类型
                categories.forEach(situationCategory -> {
                    if (situationCategory.getResourceId().equals(category)) {
                        situationPublishInstancePageRes.setCategory(situationCategory.getName());
                    }
                });

                //模板编辑状态
                dashboardTemplateRefs.forEach(dashboardTemplateRef -> {
                    if (StringUtils.isNotBlank(templateId) && Objects.equals(templateId, dashboardTemplateRef.getResourceId())) {
                        situationPublishInstancePageRes.setTemplateEdit(dashboardTemplateRef.getEditStatus());
                    }
                });

                //设置任务科目信息
                if (StringUtils.isNotBlank(taskId)) {
                    subjectList.forEach(subjectContext -> {
                        //设置任务信息
                        if (subjectContext.getTaskId().equals(taskId)) {
                            situationPublishInstancePageRes.setTaskName(subjectContext.getTaskName());
                            situationPublishInstancePageRes.setTaskStatus(subjectContext.getTaskStatus());

                            //设置科目信息
                            if (StringUtils.isNotBlank(subjectId) && subjectContext.getSubjectId().equals(subjectId)) {
                                situationPublishInstancePageRes.setSubjectName(subjectContext.getSubjectName());
                                situationPublishInstancePageRes.setSubjectStatus(subjectContext.getSubjectStatus());
                                situationPublishInstancePageRes.setStartTime(subjectContext.getStartTime());
                                situationPublishInstancePageRes.setEndTime(subjectContext.getEndTime());
                            }
                        }
                    });
                }
                if(situationPublishInstanceFilterReq.getReplay()){
                    String attributeId = situationPublishInstancePageRes.getAttributeId();
                    DashboardAttribute dashboardAttribute = stringDashboardAttributeMap.get(attributeId);
                    if(null != dashboardAttribute){
                        situationPublishInstancePageRes.setStartTime(DateUtil.DateToString(dashboardAttribute.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
                        situationPublishInstancePageRes.setEndTime(DateUtil.DateToString(dashboardAttribute.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
                    }
                }
            }
        }
        situationPublishInstancePage.setRecords(situationPublishInstancePageResList);
        return PageUtil.getListResult(situationPublishInstancePage);
    }

    @Override
    public String getEnvIdByDashboardId(String dashboardId) {
        SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper.selectById(dashboardId);
        if (null == situationPublishInstance) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
        }
        DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getByResourceId(situationPublishInstance.getAttributeId());

        return dashboardAttribute.getEnvId();
    }

    @Override
    public SituationPublishInstance createBcInstance(BcContext bcContext){

        String bcId = bcContext.getGroupId();
        String bcName = bcContext.getName();

        SituationPublishInstance currentSituationPublishInstance = situationPublishInstanceMapper.selectById(bcId);

        if (currentSituationPublishInstance != null){
            return currentSituationPublishInstance;
        }

        SituationCategory situationCategory = situationCategoryMapper.selectByName("综合态势");
        // 构建分靶场大屏实例
        SituationPublishInstance situationPublishInstance = new SituationPublishInstance();
        situationPublishInstance.setResourceId(bcId);
        situationPublishInstance.setName(bcName + "综合态势");
        situationPublishInstance.setDescription(bcName + "综合态势");
        situationPublishInstance.setSubRangeId(bcId);
        situationPublishInstance.setSubRangeName(bcName);
        situationPublishInstance.setCategory(situationCategory.getResourceId());

        // 构架分靶场大屏属性
        DashboardAttribute dashboardAttribute = new DashboardAttribute();
        dashboardAttribute.setResourceId(IDGeneratorUtil.getID());
        situationPublishInstance.setAttributeId(dashboardAttribute.getResourceId());

        // 模板不存在时获取默认模板
        Integer level = BcType.LGZX.getCode().equals(bcContext.getBcType())? 1:2;
        String defaultTemplateId = dashboardTemplateMapper.selectByLevel(level);
        String templateId = dashboardTemplateRefService.createDashboardTemplate(defaultTemplateId).getResourceId();
        situationPublishInstance.setTemplateId(templateId);

        // 设置分靶场大屏参数
        setInstanceTemplateInfo(templateId, situationPublishInstance, dashboardAttribute);
        dashboardAttribute.setMapSource(MapSource.GGIM.getCode());
        dashboardAttribute.setDashboardType(1);
        dashboardAttribute.setDashboardMode(3);
        dashboardAttribute.setTitle(bcName + "综合态势");
        dashboardAttribute.setViewAngle(JsonUtil.toJsonString(MapView.ZH.getViewAngle()));

        // 写入数据库
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    situationPublishInstanceMapper.insert(situationPublishInstance);
                    dashboardAttributeMapper.insert(dashboardAttribute);
                } catch (DuplicateKeyException e) {
                    throw new BusinessException(ErrorCode.FAIL_TO_WRITE_DB);
                }
            }
        });
        return situationPublishInstance;
    }

	@Override
	public Boolean changeInstancePublicStatus(String dashboardId, PublicStatus publicStatus) {

		// 获取大屏实例
		SituationPublishInstance situationPublishInstance = situationPublishInstanceMapper
				.selectById(dashboardId);
		if (null == situationPublishInstance) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return changeInstancePublicStatus(situationPublishInstance, publicStatus);
	}

	@Override
	public Boolean changeInstancePublicStatus(SituationPublishInstance situationPublishInstance,
			PublicStatus publicStatus) {
		// 设置大屏公共状态属性
		situationPublishInstance.setPublicStatus(publicStatus.getCode());
		situationPublishInstance.setEdit(publicStatus.getEdit());
		situationPublishInstanceMapper.updateById(situationPublishInstance);
		return true;
	}

    @Override
    public void updatePosition(String subRangeId, String taskId, String subjectId, String envId) {
        TopoConfig topoConfig = topoConfigMapper.selectByEnvId(envId);
        Boolean update = false;
        String resourceId = null;
        if (topoConfig != null) {
            resourceId = topoConfig.getResourceId();
            update = true;
        }
        TopoReq req = new TopoReq(subRangeId, taskId, subjectId, envId);
        TopoContext topoContext = remoteSituationService.getTopo(req, true);
        topoConfig = new TopoConfig(subRangeId, taskId, subjectId, envId, topoContext);
        if (update) {
            topoConfig.setResourceId(resourceId);
            topoConfigMapper.updateById(topoConfig);
        }else {
            topoConfigMapper.insert(topoConfig);
        }
    }

    @Override
    public Command getTemplateList(JSONObject jsonObject) {
        Command command = new Command();
        command.setSrcSystemCode("05");
        command.setDstSystemCode("03");
        command.setCommand("infoResponse");
        command.setCommandType("get");
        command.setCommandSubtype("getPlanParams");
        command.setCommandResult("success");
        command.setResultDesc("查询方案配置项成功");

        List<Param> paramList = new ArrayList<>();
        paramList.add(new Param(IDGeneratorUtil.get16Id(), "大屏名称", "string", "", "可视化大屏名称"));
        String name = jsonObject.getJSONObject("getPlanParams").getStr("taskSubtype");
//        TaskType type = TaskType.getEnumByCode(name, TaskType.class);
        List<VisualzationModel> models = dashboardTemplateMapper.selectVisualzationModel(null);
        List<JSONObject> objects = new ArrayList<>();
        for (VisualzationModel visualzationModel : models) {
            Map<String, List<String>> map = new HashMap<>();
            map.put(visualzationModel.getUuid(), Arrays.asList(visualzationModel.getName(), visualzationModel.getThumbnail()));
            objects.add(new JSONObject(map));
        }

        paramList.add(new Param(IDGeneratorUtil.get16Id(), "可视化模型", "visualzationModel", objects, "可视化模型配置项"));

        GetPlanParams getPlanParams = new GetPlanParams();
        getPlanParams.setTaskType(jsonObject.getJSONObject("getPlanParams").getStr("taskType"));
        getPlanParams.setTaskSubtype(name);
        getPlanParams.setCommandData(new Params(paramList));
        command.setGetPlanParams(getPlanParams);
        return command;
    }

    @Override
    public Command addDashboard(JSONObject jsonObject) {
        String setPlanInfo = "setPlanInfo";
        String defaultValue = "defaultValue";
        String commandDataString = "commandData";

        Command command = new Command();
        command.setSrcSystemCode("05");
        command.setDstSystemCode("03");
        command.setCommand("orderResponse");
        command.setCommandType("check");
        command.setCommandSubtype("setPlanInfo");
        command.setCommandResult("success");
        command.setResultDesc("可视化方案配置项成功");

        PlanInfo oldPlanInfo = jsonObject.get(setPlanInfo, PlanInfo.class);
        oldPlanInfo.setSchemeId(oldPlanInfo.getSubPlanId());

        JSONArray array = jsonObject.getJSONObject(setPlanInfo).getJSONObject(commandDataString).getJSONArray("dataIn");
        String templateId = null;
        String name = null;
        String description = null;
        for (Object object: array) {
            JSONObject param = new JSONObject(object);
            String paramName = param.getStr("name");
            if (paramName.equals("可视化模型")) {
                JSONObject object1 = param.getJSONArray(defaultValue).getJSONObject(0);
                for(String s :object1.keySet()) {
                    templateId = s;
                }
            } else if (paramName.equals("大屏名称")) {
                name = param.getStr(defaultValue);
            } else if (paramName.equals("大屏描述")) {
                description = param.getStr(defaultValue);
            }
        }

        if (templateId == null) {
            throw new BusinessException(SituationErrorCode.CHOOSE_TEMPLATE);
        }
        if (name == null) {
            throw new BusinessException(SituationErrorCode.BLANK_NAME);
        }

        PlanInfo newPlanInfo = BeanUtils.copy(oldPlanInfo, PlanInfo.class);

        // 判断是否存在已有想定大屏
        String xdDashboardId = situationPublishInstanceMapper
                .selectXdInstanceBySchemeId(oldPlanInfo.getSchemeId());

        String dashboardId;

        if (StringUtils.isNotBlank(xdDashboardId)) {
            SituationPublishInstanceUpdateReq situationPublishInstanceUpdateReq = BeanUtils.copy(oldPlanInfo,
                    SituationPublishInstanceUpdateReq.class);
            DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefService
                    .createDashboardTemplate(templateId);
            situationPublishInstanceUpdateReq.setTemplateId(dashboardTemplateRef.getResourceId());
            situationPublishInstanceUpdateReq.setName(name);
            situationPublishInstanceUpdateReq.setDescription(description);

            SituationPublishInstance situationPublishInstance = updateOne(situationPublishInstanceUpdateReq);

            dashboardId = situationPublishInstance.getResourceId();

        } else {
            // 如果没有找到大屏，进行新增
            SituationPublishInstanceAddReq situationPublishInstanceAddReq = BeanUtils.copy(oldPlanInfo,
                    SituationPublishInstanceAddReq.class);


            DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefService
                    .createDashboardTemplate(templateId);


            situationPublishInstanceAddReq.setTemplateId(dashboardTemplateRef.getResourceId());
            situationPublishInstanceAddReq.setName(name);
            situationPublishInstanceAddReq.setDescription(description);


            SituationPublishInstance situationPublishInstance = insertOne(situationPublishInstanceAddReq, true);
            dashboardId = situationPublishInstance.getResourceId();
        }

        templateId = situationPublishInstanceMapper.getTemplateId(dashboardId);
        DashboardPath dashboardPath = DashboardPath.getEnumByCode(templateId, DashboardPath.class);
        String path;
        if (dashboardPath == null) {
            path = "/cr/admin/situation-release/preview/" + dashboardId;
        } else {
            path = dashboardPath.getPath() + dashboardId;
        }
        List<Param> paramList = Arrays.asList(new Param(IDGeneratorUtil.get16Id(), "可视化方案访问地址", "string", path, "这是可视化方案访问地址"));

        //获取视图模板对应的依赖数据
        DashboardTemplateRef dashboardTemplateRef = dashboardTemplateRefService.createDashboardTemplate(templateId);
        List<JSONObject> componentsSettings =  JsonUtil.toJsonObjectArray(dashboardTemplateRef.getComponentsSetting(), JSONObject.class);
        List<String> relyOnList = new ArrayList<>();
        for (JSONObject setting : componentsSettings) {
            ApiRequestInfoRes apiRequestInfoRes = setting.getJSONObject("dataSourceConfig").get("apiDataSource", ApiRequestInfoRes.class);
            List<String> relyOnRes = apiRequestInfoRes.getRelyOnRes();
            if (relyOnRes != null) {
                relyOnList.addAll(apiRequestInfoRes.getRelyOnRes());
            }
        }
        relyOnList = new ArrayList<>(new HashSet<>(relyOnList));
        paramList.add(new Param(IDGeneratorUtil.get16Id(), "依赖数据", "list<String>", relyOnList ,"该可视化方案所依赖的采集数据"));

        CommandData commandData = new CommandData();
        commandData.setDataOutput(paramList);
        newPlanInfo.setCommandData(commandData);
        command.setSetPlanInfo(newPlanInfo);

        JSONObject commandDataJsonObject = jsonObject.getJSONObject("setPlanInfo").getJSONObject("commandData");
        commandDataJsonObject.put("dataOutput", commandDataJsonObject.getJSONArray("dataIn"));
        commandDataJsonObject.remove("dataIn");

        return command;
    }

    @Override
    public Command checkDashboard(JSONObject jsonObject) {
        Command command = new Command();
        command.setSrcSystemCode("05");
        command.setDstSystemCode("03");
        command.setCommand("orderResponse");
        command.setCommandType("check");
        command.setCommandSubtype("getPlanStatus");

        String schemeId = jsonObject.getJSONObject("getPlanStatus").getStr("subPlanId");
        String resourceId = situationPublishInstanceMapper.selectXdInstanceBySchemeId(schemeId);
//        if (resourceId != null) {
//            command.setResultDesc("可视化方案配置成功");
//            command.setCommandResult("success");
//            Param param = new Param();
//            param.setId(IDGeneratorUtil.get16Id());
//            param.setName("可视化方案名称");
//            param.setDataType("string");
//            param.setDataDescripe("可视化方案名称");
//            JSONObject dashboardName = new JSONObject(param);
//            dashboardName.put("dataValue", situationPublishInstanceMapper.selectById(resourceId).getName());
//            PlanInfo planInfo = new PlanInfo();
//            planInfo.setSubPlanId(schemeId);
//            JSONObject dataOutput = new JSONObject();
//            dataOutput.put("dataOutput", Arrays.asList(dashboardName));
//            planInfo.setCommandData(dataOutput);
//            command.setSetPlanInfo(planInfo);
//        } else {
//            command.setResultDesc("可视化方案配置失败");
//            command.setCommandResult("fail");
//            PlanInfo planInfo = new PlanInfo();
//            planInfo.setSubPlanId(schemeId);
//            Param param = new Param();
//            param.setId(IDGeneratorUtil.get16Id());
//            param.setName("可视化方案配置信息错误");
//            param.setDataType("string");
//            param.setDataDescripe("查询不到对应的可视化方案");
//            JSONObject jsonObject1 = new JSONObject();
//            planInfo.setCommandData(jsonObject1.put("dataErrOutput", Arrays.asList(param)));
//            command.setSetPlanInfo(planInfo);
//        }
        if (resourceId != null) {
            command.setResultDesc("指令执行成功");
            command.setCommandResult("success");
            JSONObject object = new JSONObject();
            object.put("subPlanId", schemeId);
            object.put("subPlanStatusCode", "01");
            command.setGetPlanStatus(object);
        } else {
            command.setResultDesc("指令执行失败");
            command.setCommandResult("fail");
            JSONObject object = new JSONObject();
            object.put("subPlanId", schemeId);
            command.setGetPlanStatus(object);
        }
        return command;

    }

    @Override
    public Command deleteDashboard(JSONObject jsonObject) {
        String schemeId = jsonObject.getJSONObject("deletePlanInfo").getStr("subPlanId");
        String dashboardId = situationPublishInstanceMapper.selectXdInstanceBySchemeId(schemeId);
        Command command = new Command();
        command.setSrcSystemCode("05");
        command.setDstSystemCode("04");
        command.setCommand("orderResponse");
        command.setCommandType("start");
        command.setCommandSubtype("deletePlanInfo");
        if (dashboardId == null) {
            command.setCommandResult("fail");
            command.setResultDesc("方案删除失败");
            PlanInfo planInfo = new PlanInfo();
            planInfo.setSubPlanId(schemeId);
            command.setSetPlanInfo(planInfo);
            return command;
        }
        try {
            SituationPublishInstance instance = situationPublishInstanceMapper.selectById(dashboardId);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    situationPublishInstanceMapper.deleteBatchIds(Arrays.asList(instance.getResourceId()));
                    dashboardTemplateRefService.deleteDashboardTemplate(Arrays.asList(instance.getTemplateId()));
                    dashboardAttributeMapper.deleteBatchIds(Arrays.asList(instance.getAttributeId()));
                }
            });
        } catch (Exception e) {
            command.setCommandResult("fail");
            command.setResultDesc("方案删除失败");
            PlanInfo planInfo = new PlanInfo();
            planInfo.setSubPlanId(schemeId);
            command.setSetPlanInfo(planInfo);
            return command;
        }
        command.setCommandResult("success");
        command.setResultDesc("方案删除成功");
        PlanInfo planInfo = new PlanInfo();
        planInfo.setSubPlanId(schemeId);
        command.setSetPlanInfo(planInfo);
        return command;
    }

}
