package com.cyberpeace.evaluate.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cyberpeace.common.exception.evaluate.ItemStoppedException;
import com.cyberpeace.common.exception.evaluate.ResourceInvalidException;
import com.cyberpeace.common.exception.evaluate.TestItemAlreadyStartException;
import com.cyberpeace.common.model.evaluation.ToolActionModel;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.evaluate.dao.*;
import com.cyberpeace.evaluate.service.bo.TestIndexBO;
import com.cyberpeace.evaluate.service.bo.TestInstanceBO;
import com.cyberpeace.evaluate.service.bo.TestInstanceSimplfyBO;
import com.cyberpeace.evaluate.service.bo.TestItemBO;
import com.cyberpeace.integratation.service.CrService;
import com.cyberpeace.integratation.service.RemotePythonRuntimeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.evaluate.dao.entity.*;
import com.cyberpeace.evaluate.service.ValidationInstanceService;
import com.cyberpeace.evaluate.service.context.TestResultContext;
import com.cyberpeace.evaluate.service.context.ValidationItemTriggerContext;
import com.cyberpeace.evaluate.service.context.ValidationSubToolContext;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ValidationInstanceServiceImpl implements ValidationInstanceService {

	@Autowired
	private TestInstanceDao testInstanceDao;

	@Autowired
	private TestItemInputDao testItemInputDao;

	@Autowired
	private TestItemDao testItemDao;

	@Autowired
	private TestInfoDao testInfoDao;

	@Autowired
	private TestSubActionOutputDao testSubActionOutputDao;

	@Autowired
	private TestIndexOutDao testIndexOutDao;

	@Autowired
	private TestIndexDao testIndexDao;

	@Autowired
	private RemotePythonRuntimeService remotePythonRuntimeService;

	@Autowired
	private CrService crService;

	ExecutorService es = Executors.newSingleThreadExecutor();

	@Override
	public List<TestInstanceSimplfyBO> getAllInstance(String infoId) {
		List<TestInstance> instances = testInstanceDao.selectAllInstance(infoId);

		List<TestInstanceSimplfyBO> rsp = Lists.newArrayList();

		if (CollectionUtils.isNotEmpty(instances)) {
			instances.forEach(item -> {
				TestInstanceSimplfyBO bo = TestInstanceSimplfyBO.builder()
						.desc(item.getResultDesc()).instanceId(item.getInstanceId())
						.name(item.getResultName()).build();

				rsp.add(bo);
			});
		}
		return rsp;
	}

	@Override
	public void endValidationInstance(TestResultContext context) {
		TestInstance instance = testInstanceDao.selectByInstanceId(context.getInstanceId());
		TestInfo testInfo = testInfoDao.selectByInfoId(instance.getInfoId());

		context.setEndTime(new Date());
		context.setStartTime(instance.getStartTime());

		TestInstance toUpdateInstance = new TestInstance();
		toUpdateInstance.setEndTime(new Date());
		toUpdateInstance.setResultName(context.getName());
		toUpdateInstance.setResultDesc(context.getDesc());
		if (context.getStatus().intValue() == TestInstance.STATUS_FINISH) {
			toUpdateInstance.setStatus(TestInstance.STATUS_FINISH);
			toUpdateInstance.setResult(JSON.toJSONString(context));
		} else if (context.getStatus().intValue() == TestInstance.STATUS_ABORT) {
			toUpdateInstance.setStatus(TestInstance.STATUS_ABORT);
		}
		toUpdateInstance.setInstanceId(instance.getInstanceId());
		toUpdateInstance.setInfoId(testInfo.getInfoId());

		testInstanceDao.update(toUpdateInstance);

		// 逐个停止该测试实例所有正在运行的测试项
		stopAllRunningItem(context.getInstanceId());
	}

	@Override
	public TestInstanceBO getInstance(String infoId) {
		TestInstanceBO rsp = new TestInstanceBO();
		TestInfo testInfo = testInfoDao.selectByInfoId(infoId);
		if (null == testInfo) {
			return rsp;
		}
		rsp.setInfo(testInfo);
		// 默认原来的拓扑id就是场景id（不区分场景和拓扑）
		rsp.setSceneId(testInfo.getTopoId());

		List<TestItemBO> itemBOS = Lists.newArrayList();

		// 1、获取用户最近一次未完成的testInstance
		TestInstance testInstance = testInstanceDao.selectLastByStatus(infoId,
				TestInstance.STATUS_RUNNING);
		// 如果有运行实例则设置场景编号和开始时间
		if (null != testInstance) {
			rsp.setInstanceId(testInstance.getInstanceId());
			rsp.setStartTime(testInstance.getStartTime());
		}

		// 2、如果用户没有未完成的testInstance，返回结果中不填充用户的临时测试数据
		List<TestItem> testItems = testItemDao.selectBySchemeId(testInfo.getSchemeId());
		// 遍历该项目吓得测试项，填充参数
		testItems.forEach(testItem -> {
			TestItemBO testItemBO = new TestItemBO();
			List<TestIndexBO> testIndexBOS = Lists.newArrayList();

			// 返回每个测试项对应的动作模型，该模型包含动作下所有子动作
			// 只有在测试项关联了工具的情况下，才执行具体查询
			if (StringUtils.isNotBlank(testItem.getToolId())) {
				ToolActionModel actionModel = crService.getCrModel(testItem.getToolId(),
 						ToolActionModel.ACTION_TYPE_ACT, testItem.getActionId());
				testItemBO.setActionModel(actionModel);
			}

			BeanUtils.copyProperties(testItem, testItemBO);
			if (null != testInstance) {
				// 设置输入项
				TestItemInput testItemInput = testItemInputDao.selectByInstanceAndItemId(
						testInstance.getInstanceId(), testItem.getItemId());
				testItemBO.setItemData(testItemInput);
			}
			// 查询测试指标项
			List<TestIndex> testIndices = testIndexDao.selectByItemId(testItem.getItemId());
			testIndices.forEach(testIndex -> {
				// 构造返回测试指标项参数
				TestIndexBO testIndexBO = new TestIndexBO();
				BeanUtils.copyProperties(testIndex, testIndexBO);
				if (null != testInstance) {
					// 查询测试指标项输出结果
					TestIndexOut testIndexOut = testIndexOutDao.selectByInstanceAndIndexId(
							testInstance.getInstanceId(), testItem.getItemId(),
							testIndex.getIndexId());
					testIndexBO.setValue(null != testIndexOut ? testIndexOut.getResult() : null);
				}
				testIndexBOS.add(testIndexBO);
			});
			testItemBO.setIndices(testIndexBOS);
			itemBOS.add(testItemBO);
		});

		rsp.setItems(itemBOS);
		return rsp;
	}

	@Override
	public String startNewInstance(String testInfoId) {
		TestInstance instance = getTestInstance(testInfoId);

		testInstanceDao.add(instance);

		return instance.getInstanceId();
	}

	@Override
	public void updateInstanceItem(TestItemInput testItemInput) {
		testItemInputDao.update(testItemInput);
	}

	@Override
	public void triggerValidationItem(ValidationItemTriggerContext context) {
		// 1、根据输入的测试项id
		// 调用指定的工具动作id接口
		// 需要记录动作的返回结果，作为下一步的执行输入
		TestItem testItem = testItemDao.selectByItemId(context.getTestItemId());
		if (null == testItem) {
			throw new ResourceInvalidException("测试项id错误");
		}

		// 查看是否有运行中的示例
		TestItemInput testItemInputCheck = testItemInputDao
				.selectByInstanceAndItemId(context.getTestInstanceId(), context.getTestItemId());

		// 如果有已经启动的测试项，不能重复启动
		if (null != testItemInputCheck
				&& testItemInputCheck.getStatus().intValue() == ToolActionModel.SUB_ACTION_START) {
			throw new TestItemAlreadyStartException();
		}

		// 为了维护模型的统一性，根据前端输入填充动作模型
		ToolActionModel actionModel = crService.getCrModel(testItem.getToolId(),
				ToolActionModel.ACTION_TYPE_ACT, testItem.getActionId());
		context.setToolActionModel(actionModel);

		String beginActionId = context.getToolActionModel().getStartActionId();

		// 2、执行指定动作id的脚本
		// 当前默认执行子动作的开始操作
		String scriptResult = remotePythonRuntimeService.runToolScript(context.getInputParams(),
				Lists.newArrayList(), beginActionId);

		// 3、保存中间数据到（测试项输入表）
		TestItemInput testItemInput = new TestItemInput();
		testItemInput.setActionInput(ListUtil.list2String(context.getInputParams()));
		testItemInput.setActionResult(scriptResult);
		testItemInput.setInstanceId(context.getTestInstanceId());
		testItemInput.setItemId(context.getTestItemId());
		testItemInput.setStatus(ToolActionModel.SUB_ACTION_START);

		// 4、保存动作的执行结果
		TestSubActionOutput testSubActionOutput = new TestSubActionOutput();
		testSubActionOutput.setActionId(beginActionId);
		testSubActionOutput.setInstanceId(context.getTestInstanceId());
		testSubActionOutput.setItemId(context.getTestItemId());
		testSubActionOutput.setOutput(scriptResult);
		try {
			testSubActionOutputDao.add(testSubActionOutput);
		} catch (DuplicateKeyException e) {
			testSubActionOutputDao.update(testSubActionOutput);
		}

		try {
			testItemInputDao.add(testItemInput);
		} catch (DuplicateKeyException e) {
			testItemInputDao.update(testItemInput);
		}
	}

	@Override
	public String triggerTool(ValidationSubToolContext context) {
		String toolResult = runSubTool(context);

		// 如果是采集，保存本次运行结果
		if (context.getToolActionModel().getOperateType() == ToolActionModel.ACTION_TYPE_COLLECT) {
			TestIndexOut testIndexOut = new TestIndexOut();
			testIndexOut.setIndexId(context.getIndexId());
			testIndexOut.setInstanceId(context.getInstanceId());
			testIndexOut.setItemId(context.getItemId());
			testIndexOut.setResult(toolResult);

			updateTestIndex(testIndexOut);
		}

		return toolResult;
	}

	@Override
	public void updateTestIndex(TestIndexOut testIndexOut) {
		try {
			testIndexOutDao.add(testIndexOut);
		} catch (DuplicateKeyException e) {
			testIndexOutDao.update(testIndexOut);
		}
	}

	/**
	 * 获取当前动作接口关联的输入接口的值
	 * @param instanceId
	 * @param itemId
	 * @param toolActionModel
	 * @return
	 */
	private List<String> getActionRsp(String instanceId, String itemId, Integer subSequenceId,
			ToolActionModel toolActionModel) {
		List<String> preActionIds = toolActionModel.actionRspAsReq(subSequenceId);

		List<String> rsp = Lists.newArrayList();

		for (String preActionId : preActionIds) {
			TestSubActionOutput output = testSubActionOutputDao.select(instanceId, itemId,
					preActionId);
			if (null != output && StringUtils.isNotBlank(output.getOutput())) {
				String unescapeOutput = StringEscapeUtils.unescapeJava(output.getOutput());
				// rsp.add(output.getOutput());
				rsp.add(unescapeOutput);
			}
		}
		return rsp;
	}

	private TestInstance getTestInstance(String testInfoId) {
		TestInstance instance = new TestInstance();

		TestInfo testInfo = testInfoDao.selectByInfoId(testInfoId);

		instance.setStartTime(new Date());
		instance.setEndTime(new Date());
		instance.setInfoId(testInfoId);
		instance.setInstanceId(IDGeneratorUtil.getID());
		// 这个拓扑id，就是场景id
		instance.setSceneId(testInfo.getTopoId());
		instance.setStatus(TestInstance.STATUS_RUNNING);

		return instance;
	}

	private String runSubTool(ValidationSubToolContext context) {
		TestItem testItem = testItemDao.selectByItemId(context.getItemId());

		String toolId = testItem.getToolId();
		String actionId = testItem.getActionId();
		String itemId = testItem.getItemId();

		// 查看是否有运行中的示例
		TestItemInput testItemInputCheck = testItemInputDao
				.selectByInstanceAndItemId(context.getInstanceId(), context.getItemId());
		if (null != testItemInputCheck
				&& testItemInputCheck.getStatus().intValue() == ToolActionModel.SUB_ACTION_STOP) {
			throw new ItemStoppedException();
		}

		Integer actionType = ToolActionModel.ACTION_TYPE_ACT;
		// 子动作序列为空，说明当前为采集
		if (null == context.getSubSequenceId() || context.getSubSequenceId().intValue() == 0) {
			actionType = ToolActionModel.ACTION_TYPE_COLLECT;

			// 如果是采集操作，获取采集对应的动作id
			TestIndex testIndex = testIndexDao.selectByIndexId(context.getIndexId());
			actionId = testIndex.getCollectToolId();
		}

		// 填充上下文模型
		ToolActionModel toolActionModel = crService.getCrModel(toolId, actionType, actionId);
		context.setToolActionModel(toolActionModel);

		// 调用实际工具执行脚本
		List<String> tooRsp = getActionRsp(context.getInstanceId(), context.getItemId(),
				context.getSubSequenceId(), toolActionModel);
		String realActionId = toolActionModel.getSubActionId(context.getSubSequenceId());

		String actionRsp = remotePythonRuntimeService.runToolScript(Lists.newArrayList(), tooRsp,
				realActionId);

		// 非停止指令，保存子动作的结果
		if (!toolActionModel.stopAction(context.getSubSequenceId())) {
			// 保存子动作返回结果
			TestSubActionOutput testSubActionOutput = new TestSubActionOutput();

			testSubActionOutput
					.setActionId(toolActionModel.getSubActionId(context.getSubSequenceId()));
			testSubActionOutput.setInstanceId(context.getInstanceId());
			testSubActionOutput.setItemId(context.getItemId());
			testSubActionOutput.setOutput(actionRsp);
			try {
				testSubActionOutputDao.add(testSubActionOutput);
			} catch (DuplicateKeyException e) {
				testSubActionOutputDao.update(testSubActionOutput);
			}
		}

		// 停止指令
		if (toolActionModel.stopAction(context.getSubSequenceId())) {
			// 清理中间数据
			testSubActionOutputDao.deleteByInstanceAndItemId(context.getInstanceId(),
					context.getItemId());
			// 更新测试项状态
			TestItemInput toUpdate = new TestItemInput();
			toUpdate.setItemId(itemId);
			toUpdate.setInstanceId(context.getInstanceId());
			toUpdate.setStatus(ToolActionModel.SUB_ACTION_STOP);

			try {
				testItemInputDao.update(toUpdate);
			} catch (Exception e) {}

		}

		return actionRsp;
	}

	/**
	 * 停止所有的测试项
	 * @param instanceId
	 */
	private void stopAllRunningItem(String instanceId) {
		List<TestItemInput> testItemInputs = testItemInputDao.selectByInstance(instanceId);
		if (CollectionUtils.isEmpty(testItemInputs)) {
			return;
		}

		// 逐个停止正在运行的测试项
		es.submit(() -> {
			testItemInputs.forEach(testItemInput -> {
				if (testItemInput.getStatus().intValue() == ToolActionModel.SUB_ACTION_START) {
					ValidationSubToolContext context = new ValidationSubToolContext();
					context.setInstanceId(instanceId);
					context.setItemId(testItemInput.getItemId());
					context.setSubSequenceId(ToolActionModel.SUB_ACTION_STOP);
					try {
						runSubTool(context);
					} catch (Exception e) {
						// 尽力而为停止测试项，失败记录个日志就行了
						log.error("stopAllRunningItem errror context=[{}],", context, e);
					}
				}
			});
		});
	}

}
