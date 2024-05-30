package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateBaseReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalCloudTemplateListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateEnumRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalCloudTemplateListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestTemplateListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalCloudTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalCloudTemplateListContext;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalCloudTemplateServiceImpl extends ServiceImpl<EvalCloudTemplateMapper, EvalCloudTemplate> implements EvalCloudTemplateService {

	@Autowired
	private EvalCloudTemplateMapper evalCloudTemplateMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalRuleTemplateMapper evalRuleTemplateMapper;

	@Autowired
	private EvalActionTemplateMapper evalActionTemplateMapper;

	@Autowired
	private EvalDimensionTemplateMapper evalDimensionTemplateMapper;

	@Autowired
	private EvalHostTemplateMapper evalHostTemplateMapper;

	@Autowired
	private EvalStepTemplateMapper evalStepTemplateMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Override
	public void addCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		EvalCloudTemplate oldEvalCloudTemplate = evalCloudTemplateMapper.selectById(evalCloudTemplateAddReq.getCloudTemplateId());
		if (oldEvalCloudTemplate != null) {
			throw new BusinessException(CrBusinessErrorCode.CLOUD_TEMPLATE_ID_EXIST);
		}
		EvalCloudTemplate evalCloudTemplate = BeanUtils.copy(evalCloudTemplateAddReq.getBaseInfo(), EvalCloudTemplate.class);
		evalCloudTemplate.setStepIdList(evalCloudTemplateAddReq.getStepIds());
		evalCloudTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalCloudTemplate.setResourceId(evalCloudTemplateAddReq.getCloudTemplateId());
		evalCloudTemplate.setTopoId(localSceneService.saveTopo(evalCloudTemplateAddReq.getSceneConfigData(),
				BusinessCode.RANGE_EVALUATION.getCode()));
		evalCloudTemplateMapper.insert(evalCloudTemplate);
	}

	@Override
	public void modifyCloudTemplate(EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		String topoId = evalCloudTemplateMapper.getTopoIdById(evalCloudTemplateAddReq.getCloudTemplateId());
		EvalCloudTemplate newEvalCloudTemplate = BeanUtils.copy(evalCloudTemplateAddReq.getBaseInfo(), EvalCloudTemplate.class);
		newEvalCloudTemplate.setStepIdList(evalCloudTemplateAddReq.getStepIds());
		newEvalCloudTemplate.setResourceId(evalCloudTemplateAddReq.getCloudTemplateId());
		newEvalCloudTemplate.setTopoId(topoId);
		localSceneService.saveTopo(evalCloudTemplateAddReq.getSceneConfigData(), topoId);
		evalCloudTemplateMapper.updateById(newEvalCloudTemplate);
	}

	@Override
	public void deleteCloudTemplate(List<String> ids) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				evalCloudTemplateMapper.deleteBatchIds(ids);
				evalRuleTemplateMapper.deleteByCloudTemplateIds(ids);
				evalActionTemplateMapper.deleteByCloudTemplateIds(ids);
				evalDimensionTemplateMapper.deleteByCloudTemplateIds(ids);
				evalHostTemplateMapper.deleteByCloudTemplateIds(ids);
				evalStepTemplateMapper.deleteByCloudTemplateIds(ids);

				List<String> topoIds = evalCloudTemplateMapper.getTopoIdsByIds(ids);
				remoteTopoService.batchDeleteTopo(topoIds);
			}
		});
	}

	@Override
	public List<EvalCloudTemplateListRsp> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq) {
		List<EvalCloudTemplateListContext> cloudList = evalCloudTemplateMapper.selectList(evalCloudTemplateListReq.getType(), evalCloudTemplateListReq.getPattern());
		List<String> createUserIds = cloudList.stream().map(EvalCloudTemplateListContext::getCreateUserId).distinct().collect(Collectors.toList());
		Map<String, String> userMap = remoteUserService.getIdNameMap(createUserIds);
		List<String> topoIds = cloudList.stream().map(EvalCloudTemplateListContext::getTopoId).collect(Collectors.toList());
		Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
		return cloudList.stream().map(evalCloudTemplateListContext -> {
			EvalCloudTemplateListRsp evalCloudTemplateListRsp = BeanUtils.copy(evalCloudTemplateListContext, EvalCloudTemplateListRsp.class);
			evalCloudTemplateListRsp.setCloudTemplateId(evalCloudTemplateListContext.getResourceId());
			evalCloudTemplateListRsp.setCreateUser(userMap.get(evalCloudTemplateListContext.getCreateUserId()));
			evalCloudTemplateListRsp.setType(EnumUtils.getEnumDescByCode(evalCloudTemplateListContext.getType(), EvalCloudTemplate.TestType.class));
			TopoDigest topoDigest = nodeCountMap.get(evalCloudTemplateListContext.getTopoId());
			if (topoDigest == null) {
				evalCloudTemplateListRsp.setNodeCount(0);
			} else {
				evalCloudTemplateListRsp.setNodeCount(topoDigest.getNodeCounts());
			}
			return evalCloudTemplateListRsp;
		}).collect(Collectors.toList());
	}

	@Override
	public EvalCloudTemplateEnumRsp getCloudTemplateEnum() {
		EvalCloudTemplateEnumRsp evalCloudTemplateEnumRsp = new EvalCloudTemplateEnumRsp();
		evalCloudTemplateEnumRsp.setCloudType(Arrays.stream(EvalCloudTemplate.CloudType.values())
													.map(cloudType -> new CodeNameRsp(cloudType.getCode(), cloudType.getDesc())).collect(Collectors.toList()));
		evalCloudTemplateEnumRsp.setTestType(Arrays.stream(EvalCloudTemplate.TestType.values())
												   .map(testType -> new CodeNameRsp(testType.getCode(), testType.getDesc())).collect(Collectors.toList()));
		return evalCloudTemplateEnumRsp;
	}

	@Override
	public EvalCloudTemplateAddReq getCloudTemplate(String resourceId) {
		EvalCloudTemplate evalCloudTemplate = evalCloudTemplateMapper.selectById(resourceId);
		EvalCloudTemplateBaseReq evalCloudTemplateBaseReq = BeanUtils.copy(evalCloudTemplate, EvalCloudTemplateBaseReq.class);
		EvalCloudTemplateAddReq evalCloudTemplateAddReq = new EvalCloudTemplateAddReq();
		evalCloudTemplateAddReq.setBaseInfo(evalCloudTemplateBaseReq);
		evalCloudTemplateAddReq.setCloudTemplateId(evalCloudTemplate.getResourceId());
		evalCloudTemplateAddReq.setStepIds(evalCloudTemplate.getStepIdList());
		evalCloudTemplateAddReq.setSceneConfigData(localSceneService.getTopoData(evalCloudTemplate.getTopoId()));
		return evalCloudTemplateAddReq;
	}

	@Override
	public ListRsp<EvalTestTemplateListRsp> templateList(EvalTestListReq evalTestListReq) {
		IPage<EvalCloudTemplate> templateIPage = evalCloudTemplateMapper.templateList(evalTestListReq.toPageParam(), evalTestListReq);
		List<String> topoIds = templateIPage.getRecords().stream().map(EvalCloudTemplate::getTopoId).collect(Collectors.toList());
		Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
		templateIPage.convert(template -> {
			EvalTestTemplateListRsp templateListRsp = BeanUtils.copy(template, EvalTestTemplateListRsp.class);
			templateListRsp.setTemplateId(template.getResourceId());
			templateListRsp.setTemplateName(template.getName());
			templateListRsp.setNodeCount(nodeCountMap.get(template.getTopoId()).getNodeCounts());
			return templateListRsp;
		});
		return PageUtil.getListResult(templateIPage);
	}
}
