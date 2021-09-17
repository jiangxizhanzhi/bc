package com.cyberpeace.evaluate.service;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.TestIndexOut;
import com.cyberpeace.evaluate.dao.entity.TestItemInput;
import com.cyberpeace.evaluate.service.bo.TestInstanceBO;
import com.cyberpeace.evaluate.service.bo.TestInstanceSimplfyBO;
import com.cyberpeace.evaluate.service.context.TestResultContext;
import com.cyberpeace.evaluate.service.context.ValidationItemTriggerContext;
import com.cyberpeace.evaluate.service.context.ValidationSubToolContext;

public interface ValidationInstanceService {

	String startNewInstance(String testInfoId);

	void triggerValidationItem(ValidationItemTriggerContext validationItemTriggerContext);

	/**
	 * 更新某个指定测试项的状态
	 * @param testItemInput
	 */
	void updateInstanceItem(TestItemInput testItemInput);

	/**
	 * 触发子动作执行
	 */
	String triggerTool(ValidationSubToolContext context);

	/**
	 * 保存或者更新指指标项数据
	 * @param testIndexOut
	 */
	void updateTestIndex(TestIndexOut testIndexOut);

	/**
	 * 进入评估
	 * @param infoId
	 * @return
	 */
	TestInstanceBO getInstance(String infoId);

	/**
	 * 保存测试结果。并结束当前测试实例
	 *
	 * @param context
	 */
	void endValidationInstance(TestResultContext context);

	/**
	 * 获取当前测试评估的所有实例
	 * @param infoId
	 * @return
	 */
	List<TestInstanceSimplfyBO> getAllInstance(String infoId);
}
