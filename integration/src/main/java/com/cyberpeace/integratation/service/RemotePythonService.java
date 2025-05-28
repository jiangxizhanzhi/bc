/**
 * 这段代码定义了一个名为 `RemotePythonService` 的接口，该接口用于与远程Python服务进行交互。以下是对其主要功能和结构的简要分析： 
 * ### 接口名称 
 * - **RemotePythonService**: 表示这是一个与远程Python服务通信的接口。 
 * ### 方法 
 * 接口中定义了多个方法，每个方法都有其特定的功能和参数。以下是这些方法的详细说明： 
 * 1. **getPyHost()** 
 * - **描述**: 返回Python运行服务的地址。 
 * - **返回值**: `String` 
 * 2. **formatPerform()** 
 * - **描述**: 执行格式化算法。 
 * - **参数**: 
 * - `algorithmId`: 算法ID 
 * - `algorithmCode`: 算法脚本内容 
 * - `formatData`: 算法输入数据 
 * - `otherArguments`: 其他可配置参数 
 * - **返回值**: `List<String>`，格式化执行结果 
 * 3. **matchPerform()** 
 * - **描述**: 执行模型匹配算法。 
 * - **参数**: 
 * - `algorithmId`: 算法ID 
 * - `algorithmCode`: 算法脚本内容 
 * - `matchData`: 算法输入数据 
 * - `otherArguments`: 其他可配置参数 
 * - **返回值**: `List<Integer>`，模型匹配执行结果（列表内包含0或1） 
 * 4. **markPerform()** 
 * - **描述**: 执行评分算法。 
 * - **参数**: 
 * - `algorithmId`: 算法ID 
 * - `algorithmCode`: 算法脚本内容 
 * - `markData`: 算法输入数据 
 * - `otherArguments`: 其他可配置参数 
 * - **返回值**: `Integer`，评分算法执行结果（0到100之间的整数） 
 * 5. **indexPerform()** 
 * - **描述**: 执行聚合算法。 
 * - **参数**: 
 * - `algorithmId`: 算法ID 
 * - `algorithmCode`: 算法脚本内容 
 * - `data`: 算法输入数据（列表内元素均为0到100的整数） 
 * - `otherArguments`: 其他可配置参数 
 * - **返回值**: `Integer`，聚合算法执行结果（0到100之间的整数） 
 * 6. **indexAhpPerform()** 
 * - **描述**: 执行另一种形式的聚合算法。 
 * - **参数**: 
 * - `algorithmId`: 算法ID 
 * - `matrixData`: 矩阵内容 
 * - `algorithmCode`: 算法脚本内容 
 * - `data`: 算法输入数据（列表内元素均为0到100的整数） 
 * - `otherArguments`: 其他可配置参数 
 * - **返回值**: `Integer`，聚合算法执行结果（0到100之间的整数） 
 * 7. **runAlgorithm()** 
 * - **描述**: 执行一个通用的算法。 
 * - **参数**: 
 * - `algorithmId`: 算法ID 
 * - `algorithmCode`: 算法代号 
 * - `data`: 数据 
 * - `listData`: 列出数据 
 * - `otherArguments`: 其他参数 
 * - **返回值**: `Object`，算法执行结果 
 * ### 总结 
 * 这个接口主要用于调用远程Python服务的各种算法，并且根据不同的算法类型返回不同类型的结果。通过这些方法，可以实现格式化、模型匹配、评分、聚合等多种计算任务。接口的设计考虑到了灵活性和扩展性，允许传入多种类型的输入参数，并且返回结果可以根据需要进行调整。 
**/
package com.cyberpeace.integratation.service;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;

/**
 * 远程蟒Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface RemotePythonService {
	/**
	 * 获取Python运行服务地址
	 *
	 * @return Python运行服务地址信息
	 */
	String getPyHost();

	/**
	 * 靶场测评格式化算法执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法脚本内容
	 * @param formatData     算法列表入参
	 * @param otherArguments 算法可配置入参列表
	 * @return 格式化执行结果
	 */
	List<String> formatPerform(String algorithmId, String algorithmCode, FormatData formatData,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评模型匹配算法执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法脚本内容
	 * @param matchData      算法入参
	 * @param otherArguments 算法可配置入参列表
	 * @return 模型匹配执行结果, 列表内包含0、1元素
	 */
	List<Integer> matchPerform(String algorithmId, String algorithmCode, MatchData matchData,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评评分算法执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法脚本内容
	 * @param markData       算法入参
	 * @param otherArguments 算法可配置入参列表
	 * @return 评分算法执行结果, 为0~100整数
	 */
	Integer markPerform(String algorithmId, String algorithmCode, MarkData markData,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评聚合算法执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法脚本内容
	 * @param data           算法入参, 列表内元素均为0~100整数
	 * @param otherArguments 算法可配置入参列表
	 * @return 聚合算法执行结果, 为0~100整数
	 */
	Integer indexPerform(String algorithmId, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments);

	/**
	 * 靶场测评聚合算法执行
	 *
	 * @param algorithmId    算法id
	 * @param matrixData     矩阵内容
	 * @param algorithmCode  算法脚本内容
	 * @param data           算法入参, 列表内元素均为0~100整数
	 * @param otherArguments 算法可配置入参列表
	 * @return 聚合算法执行结果, 为0~100整数
	 */
	Integer indexAhpPerform(String algorithmId, String matrixData, String algorithmCode, List<Integer> data,
						 List<Serializable> otherArguments);

	/**
	 * run 算法
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param data           数据
	 * @param listData       列出数据
	 * @param otherArguments 其他参数
	 * @return {@code Object }
	 */
	Object runAlgorithm(String algorithmId, String algorithmCode, Object data, Object listData, List<Serializable> otherArguments);
}
