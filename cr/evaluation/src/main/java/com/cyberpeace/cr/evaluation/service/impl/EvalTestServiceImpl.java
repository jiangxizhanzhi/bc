package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestListRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCloudTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCloudTemplateMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestMapper;
import com.cyberpeace.cr.evaluation.service.EvalRuleService;
import com.cyberpeace.cr.evaluation.service.EvalStepService;
import com.cyberpeace.cr.evaluation.service.EvalTestService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalTestServiceImpl extends ServiceImpl<EvalTestMapper, EvalTest> implements EvalTestService {

	@Autowired
	private EvalTestMapper evalTestMapper;

	@Autowired
	private EvalCloudTemplateMapper evalCloudTemplateMapper;

	@Autowired
	private EvalRuleService evalRuleService;

	@Autowired
	private EvalStepService evalStepService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Override
	public String createByTemplate(EvalTestAddReq evalTestAddReq) {
		EvalCloudTemplate evalCloudTemplate = evalCloudTemplateMapper.selectById(evalTestAddReq.getCloudTemplateId());
		EvalTest evalTest = BeanUtils.copy(evalCloudTemplate, EvalTest.class);
		evalTest.setReadAuthList(evalTestAddReq.getUserIds());
		String newId = IDGeneratorUtil.getID();
		evalTest.setResourceId(newId);
		evalTest.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalTest.setTopoId(localSceneService.copyTopo(evalCloudTemplate.getTopoId(), evalTestAddReq.getName()));
		evalTest.setTemplateName(evalCloudTemplate.getName());


		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				Map<String, String> stepIdMap = evalStepService.createByTemplate(evalCloudTemplate.getStepIdList(), newId);
				evalTest.setStepList(new ArrayList<>(stepIdMap.values()));
				evalTest.setTemplateId(evalTestAddReq.getCloudTemplateId());
				evalRuleService.createByTemplate(evalTestAddReq.getCloudTemplateId(), newId, stepIdMap);
				evalTestMapper.insert(evalTest);
			}}
		);
		return newId;
	}

	@Override
	public ListRsp<EvalTestListRsp> testList(EvalTestListReq evalTestListReq) {
		IPage<EvalTest> evalTestIPage = evalTestMapper.testPage(evalTestListReq.toPageParam(), evalTestListReq, UserInfoContext.getUserContext().getUserId());
		List<String> userIds = new ArrayList<>();
		for (EvalTest evalTest : evalTestIPage.getRecords()) {
			userIds.addAll(evalTest.getReadAuthList());
		}
		Map<String, UserInfo> nameIdMap = remoteUserService.getIdUserInfoMap(userIds);
		List<String> topoIds = evalTestIPage.getRecords().stream().map(EvalTest::getTopoId).collect(Collectors.toList());
		Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
		evalTestIPage.convert(test -> {
			EvalTestListRsp testRsp = BeanUtils.copy(test, EvalTestListRsp.class);
			testRsp.setTestId(test.getResourceId());
			List<UserInfo> userInfoList = new ArrayList<>();
			for (String id : test.getReadAuthList()) {
				userInfoList.add(nameIdMap.get(id));
			}
			testRsp.setUserInfoList(userInfoList);
			testRsp.setNodeCount(nodeCountMap.get(test.getTopoId()).getNodeCounts());

			// TODO: 2024/3/22 调用中台接口获取服务类型、试验工具
			testRsp.setCloudServices(new ArrayList<>());
			testRsp.setToolCount(0);
			testRsp.setToolList(new ArrayList<>());
			return testRsp;
		});
		return PageUtil.getListResult(evalTestIPage);
	}
}
