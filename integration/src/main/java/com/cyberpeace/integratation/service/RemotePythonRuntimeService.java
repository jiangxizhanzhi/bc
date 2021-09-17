package com.cyberpeace.integratation.service;

import com.cyberpeace.common.model.evaluation.TimeSequenceValue;

import java.util.Date;
import java.util.List;


public interface RemotePythonRuntimeService {

	/**
	 * 调用python，原始采集数据格式化
	 * @param sceneId 场景id
	 * @param hostId 主机id
	 * @param collectId 采集器id
	 * @param pythonScript 算法源码
	 * @return
	 */
	List<TimeSequenceValue> formatProbeData(String sceneId, String hostId, String collectId,
											String pythonScript, Date startTime, Date endTime, List<String> selfDefineValue);

	/**
	 * 执行模型检测算法
	 * @param formattedData 格式化数据
	 * @param selfDefineValue 脚本对应的自定义参数
	 * @param pythonScript 算法源码
	 * @return
	 */
	List<TimeSequenceValue> modelDetection(List<TimeSequenceValue> formattedData,
			List<String> selfDefineValue, String pythonScript);

	/**
	 * 变量项评分模型
	 * @param modelDetectionData 模型检测数据
	 * @param originData 原始格式化数据
	 * @param selfDefineValue 脚本对应的自定义参数
	 * @param pythonScript 算法源码
	 * @return
	 */
	Integer calculateEstimateVariable(List<TimeSequenceValue> modelDetectionData,
			List<TimeSequenceValue> originData, List<String> selfDefineValue, String pythonScript);

	/**
	 * 聚合算法
	 * @param variables 变量项
	 * @param selfDefineValue  脚本对应的自定义参数
	 * @param pythonScript 算法源码
	 * @return
	 */
	Integer aggregation(List<Integer> variables, List<String> selfDefineValue, String pythonScript);

	/**
	 * 执行通用脚本
	 * @param selfDefineValue
	 * @param pythonScript
	 * @return
	 */
	String runGeneralScript(List<String> selfDefineValue, String pythonScript);

	/**
	 * 根据工具id和自定义参数，运行工具脚本
	 * @param selfDefineValue 用户自定义参数
	 * @param toolRsp 工具响应参数
	 * @param actionId 工具动作id
	 * @return
	 */
	String runToolScript(List<String> selfDefineValue, List<String> toolRsp, String actionId);
}
