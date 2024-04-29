package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.NameCount;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalPersonTestRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestStatisticsRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCloudTemplateMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalRecordMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestMapper;
import com.cyberpeace.cr.evaluation.service.*;
import com.cyberpeace.cr.evaluation.service.context.EvalInstanceSlimContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTestCategoryCount;
import com.cyberpeace.cr.evaluation.service.context.TestRecordContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteSupersetService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
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

	@Autowired
	private EvalTestInstanceMapper evalTestInstanceMapper;

	@Autowired
	private EvalTestInstanceService evalTestInstanceService;

	@Autowired
	private EvalInstanceStepService evalInstanceStepService;

	@Autowired
	private RemoteSupersetService remoteSupersetService;

	@Autowired
	private EvalRecordMapper evalRecordMapper;

	@Override
	public String createByTemplate(EvalTestAddReq evalTestAddReq) {
		EvalCloudTemplate evalCloudTemplate = evalCloudTemplateMapper.selectById(evalTestAddReq.getCloudTemplateId());
		EvalTest evalTest = BeanUtils.copy(evalCloudTemplate, EvalTest.class);
		evalTest.setAuthList(evalTestAddReq.getUserIds());
		String newId = IDGeneratorUtil.getID();
		evalTest.setResourceId(newId);
		evalTest.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalTest.setTopoId(localSceneService.copyTopo(evalCloudTemplate.getTopoId(), evalTestAddReq.getName()));
		evalTest.setTemplateName(evalCloudTemplate.getName());
		evalTest.setDashboardId(remoteSupersetService.createDashboard(evalTestAddReq.getName()));
		evalTest.setResourceId(null);
		evalTest.setId(null);


		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				Map<String, String> stepIdMap = evalStepService.createByTemplate(evalCloudTemplate.getStepIdList(), newId);
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
		//获取用户信息
		List<String> userIds = new ArrayList<>();
		for (EvalTest evalTest : evalTestIPage.getRecords()) {
			userIds.addAll(evalTest.getAuthList());
		}
		Map<String, UserInfo> nameIdMap = remoteUserService.getIdUserInfoMap(userIds);
		//获取节点数量信息
		List<String> topoIds = evalTestIPage.getRecords().stream().map(EvalTest::getTopoId).collect(Collectors.toList());
		Map<String, TopoDigest> nodeCountMap = remoteTopoService.batchGetTopoDigest(topoIds);
		//获取训练任务评估数据
		List<String> testIds = evalTestIPage.getRecords().stream().map(EvalTest::getResourceId).collect(Collectors.toList());
		List<TestRecordContext> recordList =  evalRecordMapper.getTestRecordByIds(testIds);
		Map<String, TestRecordContext> recordMap = recordList.stream().collect(Collectors.toMap(TestRecordContext::getTestId, e -> e));
		evalTestIPage.convert(test -> {
			EvalTestListRsp testRsp = BeanUtils.copy(test, EvalTestListRsp.class);
			testRsp.setTestId(test.getResourceId());
			List<UserInfo> userInfoList = new ArrayList<>();
			for (String id : test.getAuthList()) {
				userInfoList.add(nameIdMap.get(id));
			}
			testRsp.setUserInfoList(userInfoList);
			testRsp.setNodeCount(nodeCountMap.get(test.getTopoId()).getNodeCounts());
			if (StringUtils.isBlank(test.getCurrentInstanceId())) {
				testRsp.setStatus(1);
			} else {
				testRsp.setStatus(0);
			}
			TestRecordContext record = recordMap.get(test.getResourceId());
			if (record == null) {
				testRsp.setCount(0);
				testRsp.setScore(0);
			}else {
				testRsp.setCount(recordMap.get(test.getResourceId()).getCount());
				testRsp.setScore(recordMap.get(test.getResourceId()).getCount());
			}

			// TODO: 2024/3/22 调用中台接口获取服务类型、试验工具
			testRsp.setCloudServices(new ArrayList<>());
			testRsp.setToolCount(0);
			testRsp.setToolList(new ArrayList<>());
			return testRsp;
		});
		return PageUtil.getListResult(evalTestIPage);
	}

	@Override
	public EvalTest getEvalTest(String testId) {
		EvalTest evalTest = getById(testId);

		//校验试验是否存在
		if (evalTest == null) {
			log.error("EvalTest can't find, testId = [{}]", testId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		//校验权限
		String userId = UserInfoContext.getUserContext().getUserId();
		if (!evalTest.isFree2Operate() && !evalTest.getCreateUserId().equals(userId)
				&& !evalTest.getOperationAuthIds().contains(userId)) {
			log.error("Permission denied! userId = [{}]",
					UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

		return evalTest;
	}

	@Override
	public Boolean deleteTest(String testId) {
		List<String> instanceIds = evalTestInstanceMapper.selectIdsByTestIdAndStatus(testId, null);
		for (String instanceId : instanceIds) {
			evalTestInstanceService.deleteTestInstance(instanceId);
		}
		EvalTest evalTest = evalTestMapper.selectById(testId);
		if (StringUtils.isNotEmpty(evalTest.getTopoId())) {
			try {
				remoteTopoService.deleteById(evalTest.getTopoId());
			} catch (Exception e) {
				log.error("Evaluate test delete topo fail! testId=[{}], topoId=[{}], catch error:", testId, evalTest.getTopoId(), e);
			}
			try {
				remoteSupersetService.deleteDashboard(Arrays.asList(evalTest.getDashboardId()));
			} catch (Exception e) {
				log.error("Evaluate test delete dashboard fail! testId=[{}], dashboardId=[{}], catch error:", testId, evalTest.getDashboardId(), e);
			}
		}
		evalTestMapper.deleteById(testId);
		return true;
	}

	@Override
	public String getDashboardPermalink(String testId) {
		JSONObject dashboardDetail = remoteSupersetService.getDashboardDetail(testId);
		if (!dashboardDetail.getBoolean("published")) {
			throw new BusinessException(CrBusinessErrorCode.DASHBOARD_NOT_PUBLISH);
		}
		JSONObject permalink = remoteSupersetService.getDashboardPermalink(testId);
		return permalink.getString("url");
	}

	@Override
	public Boolean validateExistTask(String testId, EvalTestInstance.Status status) {
		List<String> instanceIds = evalTestInstanceMapper.selectIdsByTestIdAndStatus(testId, status.getCode());
		return !instanceIds.isEmpty();
	}

	@Override
	public String startScene(String testId) {

		EvalTest evalTest = getEvalTest(testId);

		EvalTestInstance evalTestInstance = new EvalTestInstance();
		evalTestInstance.setTestId(testId);
		evalTestInstance.setTestName(evalTest.getName());
		evalTestInstance.setCopyedTopoId(remoteTopoService.copyTopo(evalTest.getTopoId()));
		evalTestInstance.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		evalTestInstance.setResourceId(IDGeneratorUtil.getID());


		List<EvalInstanceStep> instanceSteps = new ArrayList<>();
		List<EvalStep> evalSteps = evalStepService.selectByTestId(testId);
		for (EvalStep evalStep : evalSteps) {
			EvalInstanceStep evalInstanceStep = new EvalInstanceStep();
			evalInstanceStep.setStage(evalStep.getStage());
			evalInstanceStep.setTestId(testId);
			evalInstanceStep.setStepId(evalStep.getResourceId());
			evalInstanceStep.setTestInstanceId(evalTestInstance.getResourceId());
			instanceSteps.add(evalInstanceStep);
		}

		// 构建本地scene对象
		LocalScene localScene = createLocalScene(evalTestInstance);

		//启动中台场景
		String sceneId = localSceneService.startScene(localScene);
		localScene.setMpSceneId(sceneId);
		localScene.setSceneStatus(SceneStatusEnum.START.getCode());
		Date startTime = new Date();
		localScene.setStartTime(startTime);
		evalTestInstance.setStartTime(startTime);
		evalTestInstance.setLocalSceneId(localScene.getResourceId());
		evalTestInstance.setMpSceneId(sceneId);

		// 更新模板信息
		evalTest.setCurrentInstanceId(evalTestInstance.getResourceId());

		try {
			// 更新本地数据库数据
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					localSceneService.save(localScene);
					evalTestInstanceService.save(evalTestInstance);
					evalInstanceStepService.saveBatch(instanceSteps);
					evalTestMapper.updateById(evalTest);
				}
			});
		} catch (Exception e) {
			// 本地保存数据错误，删除已启动的远程场景
			localSceneService.stopScene(localScene);
			log.error("Failed to save local data:", e);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}
		return evalTestInstance.getResourceId();
	}

	@Override
	public EvalTestStatisticsRsp testStatistics() {
		EvalTestStatisticsRsp evalTestStatisticsRsp = new EvalTestStatisticsRsp();
		List<NameCount> overview = new ArrayList<>();
		overview.add(new NameCount("验证模板", evalCloudTemplateMapper.count()));
		overview.add(new NameCount("验证任务", evalTestMapper.countByUserId(UserInfoContext.getUserContext().getUserId())));
		evalTestStatisticsRsp.setOverview(overview);
		List<NameCount> categoryCount = new ArrayList<>();
		List<EvalTestCategoryCount> categoryCounts = evalTestInstanceMapper.countByCategoryAndUserId(UserInfoContext.getUserContext().getUserId());
		Map<Integer, Integer> categoryCountMap = categoryCounts.stream()
															   .collect(Collectors.toMap(EvalTestCategoryCount::getType, EvalTestCategoryCount::getCount));
		for (EvalCloudTemplate.TestType type : EvalCloudTemplate.TestType.values()) {
			categoryCount.add(new NameCount(type.getDesc(), categoryCountMap.getOrDefault(type.getCode(), 0)));
		}
		evalTestStatisticsRsp.setCategoryCount(categoryCount);
		return evalTestStatisticsRsp;
	}

	@Override
	public ListRsp<EvalPersonTestRsp> personTestRspListRsp(PageReqBase pageReqBase) {
		IPage<EvalPersonTestRsp> testRspIPage = evalTestMapper.selectPersonTestPage(pageReqBase.toPageParam(), UserInfoContext.getUserContext().getUserId());
		testRspIPage.getRecords().forEach(test -> {
			test.setType(EnumUtils.getEnumDescByCode(Integer.parseInt(test.getType()), EvalCloudTemplate.TestType.class));
		});
		return PageUtil.getListResult(testRspIPage);
	}

	@Override
	public JSONObject getDeviceStatistics(String testId) {
		String localSceneId = evalTestMapper.selectLocalSceneIdByTestId(testId);
		if (StringUtils.isNotEmpty(localSceneId)) {
			return localSceneService.getNodeStatistics(localSceneId);
		} else {
			String topoId = evalTestMapper.selectTopoIdByTestId(testId);
			return localSceneService.getNodeStatisticsByTopoId(topoId);
		}
	}

	@Override
	public JSONArray getDeviceList(String testId, Integer category, String name) {
		String localSceneId = evalTestMapper.selectLocalSceneIdByTestId(testId);
		if (StringUtils.isNotEmpty(localSceneId)) {
			EvalTest evalTest = evalTestMapper.selectById(testId);
			return localSceneService.getAssetListByInstanceId(evalTest.getCurrentInstanceId(), category, name, true);
		} else {
			String topoId = evalTestMapper.selectTopoIdByTestId(testId);
			return localSceneService.getAssetListByTopoId(topoId, category, name);
		}
	}

	@Override
	public List<EvalInstanceSlimContext> listInstance(EvalTest evalTest, Boolean isFinished) {
		if (isFinished != null && isFinished) {
			return evalTestInstanceMapper.selectSlimInstanceListByTestIdAndStatus(
					evalTest.getResourceId(), EvalTestInstance.Status.FINISHED.getCode()
			);
		}

		List<EvalInstanceSlimContext> evalInstanceSlimContexts = evalTestInstanceMapper
				.selectSlimInstanceListByTestIdAndStatus(evalTest.getResourceId(), null);

		if (StringUtils.isEmpty(evalTest.getCurrentInstanceId())) {
			evalInstanceSlimContexts.add(new EvalInstanceSlimContext());
		}
		return evalInstanceSlimContexts;
	}

	private LocalScene createLocalScene(EvalTestInstance evalTestInstance) {
		LocalScene localScene = new LocalScene();
		localScene.setBusinessCode(BusinessCode.RANGE_EVALUATION.getCode());
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setBusinessInstanceId(evalTestInstance.getResourceId());
		localScene.setTopoId(evalTestInstance.getCopyedTopoId());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());
		localScene.setSceneName(evalTestInstance.getInstanceName());
		String appResourceId = evalTestInstance.getTestId();
		localScene.setMeta(JSON.toJSONString(LocalSceneMeta.builder().appResourceId(appResourceId).build()));
		return localScene;
	}
}
