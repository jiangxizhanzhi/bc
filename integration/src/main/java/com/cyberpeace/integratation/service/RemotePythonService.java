package com.cyberpeace.integratation.service;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;

public interface RemotePythonService {
	/**
	 * 获取Python运行服务地址
	 * @return Python运行服务地址信息
	 */
	String getPyHost();

	/**
	 * 靶场测评格式化算法执行
	 * @param algorithmId 算法id
	 * @param algorithmCode 算法脚本内容
	 * @param formatData 算法列表入参
	 * @param otherArguments 算法可配置入参列表
	 * @return 格式化执行结果
	 */
	List<String> formatPerform(String algorithmId, String algorithmCode, FormatData formatData,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评模型匹配算法执行
	 * @param algorithmId 算法id
	 * @param algorithmCode 算法脚本内容
	 * @param matchData 算法入参
	 * @param otherArguments 算法可配置入参列表
	 * @return 模型匹配执行结果, 列表内包含0、1元素
	 */
	List<Integer> matchPerform(String algorithmId, String algorithmCode, MatchData matchData,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评评分算法执行
	 * @param algorithmId 算法id
	 * @param algorithmCode 算法脚本内容
	 * @param markData 算法入参
	 * @param otherArguments 算法可配置入参列表
	 * @return 评分算法执行结果, 为0~100整数
	 */
	Integer markPerform(String algorithmId, String algorithmCode, MarkData markData,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评聚合算法执行
	 * @param algorithmId 算法id
	 * @param algorithmCode 算法脚本内容
	 * @param data 算法入参, 列表内元素均为0~100整数
	 * @param otherArguments 算法可配置入参列表
	 * @return 聚合算法执行结果, 为0~100整数
	 */
	Integer indexPerform(String algorithmId, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评聚合算法执行
	 * @param algorithmId 算法id
	 * @param matrixData 矩阵内容
	 * @param algorithmCode 算法脚本内容
	 * @param data 算法入参, 列表内元素均为0~100整数
	 * @param otherArguments 算法可配置入参列表
	 * @return 聚合算法执行结果, 为0~100整数
	 */
	Integer indexAhpPerform(String algorithmId, String matrixData, String algorithmCode, List<Integer> data,
						 List<Serializable> otherArguments);
}
