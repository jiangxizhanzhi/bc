/**
 * 这段代码是一个Spring服务实现类 `RemotePythonServiceImpl`，它实现了 `RemotePythonService` 接口。这个服务的主要功能是通过HTTP客户端调用远程Python服务，并处理返回的数据。以下是代码的主要部分及其功能分析： 
 * ### 主要组件和功能 
 * 1. **依赖注入与配置** 
 * - 使用 `@Service` 注解表明这是一个Spring服务类。 
 * - `@Slf4j` 注解提供了日志记录功能。 
 * - `@PostConstruct` 注解的方法 `init()` 在构造函数之后、其他方法之前初始化服务。 
 * 2. **属性** 
 * - `PY_URL`: 存储了远程Python服务的URL地址。 
 * 3. **初始化方法** 
 * - `init()`: 从系统属性中获取 `PY_HOST` 和 `PY_PORT`，并组合成完整的URL地址。 
 * 4. **公共方法** 
 * - `getPyHost()`: 返回远程Python服务的主机地址。 
 * - `formatPerform()`: 发送格式化数据请求，并返回字符串列表结果。 
 * - `matchPerform()`: 发送匹配数据请求，并返回整数列表结果。 
 * - `markPerform()`: 发送标记数据请求，并返回单个整数结果。 
 * - `indexPerform()`: 发送索引数据请求，并返回单个整数结果。 
 * - `indexAhpPerform()`: 发送带有矩阵数据的索引AHP请求，并返回单个整数结果。 
 * - `runAlgorithm()`: 发送通用算法请求，并返回对象结果。 
 * 5. **私有方法** 
 * - `buildRequestData()`: 构建请求数据的JSON对象。 
 * - `invoke()`: 发送HTTP POST请求，并处理响应。 
 * - `getFormatMatrixData()`: 将矩阵数据转换为格式化的二维字符串列表。 
 * ### 关键点总结 
 * - **HTTP通信**: 使用 `HttpClientUtil` 进行HTTP POST请求。 
 * - **数据处理**: 使用 `fastjson` 库处理JSON数据，包括构建请求和解析响应。 
 * - **异常处理**: 在调用远程服务时捕获异常，并抛出自定义异常 `RemoteServiceException`。 
 * - **日志记录**: 使用 `slf4j` 记录日志信息，便于调试和监控。 
 * ### 优点 
 * - **模块化设计**: 方法职责明确，易于维护和扩展。 
 * - **异常处理**: 统一处理远程服务调用可能引发的异常。 
 * - **日志记录**: 提供了详细的日志记录，有助于问题追踪。 
 * ### 改进建议 
 * - **异常处理**: 可以更详细地处理不同类型的异常，并提供更具描述性的错误信息。 
 * - **性能优化**: 对于频繁调用的服务，可以考虑使用缓存机制来提高性能。 
 * - **代码复用**: 部分代码（如日志记录）可以在框架级别进行统一管理，减少重复代码。 
 * 总体而言，这段代码实现了一个功能完善的远程Python服务调用接口，并具备良好的可维护性和扩展性。 
**/
package com.cyberpeace.integratation.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.model.evaluation.FormatData;
import com.cyberpeace.integratation.model.evaluation.MarkData;
import com.cyberpeace.integratation.model.evaluation.MatchData;
import com.cyberpeace.integratation.service.RemotePythonService;

import lombok.extern.slf4j.Slf4j;

/**
 * 远程蟒Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class RemotePythonServiceImpl implements RemotePythonService {

	/**
	 * py 网址
	 */
	private String PY_URL;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		String PY_HOST = SystemPropUtil.getProp("PY_HOST");
		String PY_PORT = SystemPropUtil.getProp("PY_PORT");
		PY_URL = "http://" + PY_HOST + ":" + PY_PORT;
	}

	/**
	 * 获取py主机
	 *
	 * @return {@code String }
	 */
	@Override
	public String getPyHost() {
		return PY_URL;
	}

	/**
	 * 生成请求数据
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param otherArguments 其他参数
	 * @return {@code JSONObject }
	 */
	private JSONObject buildRequestData(String algorithmId, String algorithmCode,
			List<Serializable> otherArguments) {
		// 构建传入数据
		JSONObject json = new JSONObject();
		json.put("algorithm_id", algorithmId);
		json.put("code", algorithmCode);
		json.put("other_args", otherArguments);

		return json;
	}

	/**
	 * 调用
	 *
	 * @param requestData 请求数据
	 * @return {@code String }
	 */
	private String invoke(JSONObject requestData) {
		String resp = HttpClientUtil.post(getPyHost() + "/algorithm/run/", requestData,
				String.class);
		if (resp == null) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}

		return resp;
	}

	/**
	 * format perform
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param formatData     设置数据格式
	 * @param otherArguments 其他参数
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> formatPerform(String algorithmId, String algorithmCode,
			FormatData formatData, List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("list_data", formatData.getData());

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		List<String> results = JsonUtil.toJsonObjectArray(resp, String.class);
		if (results.contains(null)) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}

		return results;
	}

	/**
	 * match perform
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param matchData      匹配数据
	 * @param otherArguments 其他参数
	 * @return {@code List<Integer> }
	 */
	@Override
	public List<Integer> matchPerform(String algorithmId, String algorithmCode, MatchData matchData,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", matchData);

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObjectArray(resp, Integer.class);
	}

	/**
	 * 标记执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param markData       标记数据
	 * @param otherArguments 其他参数
	 * @return {@code Integer }
	 */
	@Override
	public Integer markPerform(String algorithmId, String algorithmCode, MarkData markData,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", markData);

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

	/**
	 * 索引执行
	 *
	 * @param algorithmId    算法id
	 * @param algorithmCode  算法代号
	 * @param data           数据
	 * @param otherArguments 其他参数
	 * @return {@code Integer }
	 */
	@Override
	public Integer indexPerform(String algorithmId, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", data);

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

	/**
	 * 索引 AHP 执行
	 *
	 * @param algorithmId    算法id
	 * @param matrixData     矩阵数据
	 * @param algorithmCode  算法代号
	 * @param data           数据
	 * @param otherArguments 其他参数
	 * @return {@code Integer }
	 */
	@Override
	public Integer indexAhpPerform(String algorithmId, String matrixData, String algorithmCode, List<Integer> data,
			List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", data);
		json.put("matrix_data", getFormatMatrixData(matrixData, data));

		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return JsonUtil.toJsonObject(resp, Integer.class);
	}

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
	@Override
	public Object runAlgorithm(String algorithmId, String algorithmCode, Object data, Object listData, List<Serializable> otherArguments) {
		JSONObject json = buildRequestData(algorithmId, algorithmCode, otherArguments);
		json.put("data", data);
		json.put("list_data", listData);
		String resp;
		try {
			resp = invoke(json);
		} catch (RemoteServiceException e) {
			throw new RemoteServiceException(ErrorCode.ALGORITHM_RETURN_NULL);
		}
		return  JsonUtil.toJsonObject(resp, Object.class);
	}

	/**
	 * 获取格式矩阵数据
	 *
	 * @param matrixData 矩阵数据
	 * @param scores     分数
	 * @return {@code List<List<String>> }
	 */
	public List<List<String>> getFormatMatrixData(String matrixData, List<Integer> scores){
		JSONArray objects = JSONArray.parseArray(matrixData);
		List<List<String>> res = new ArrayList<>();
		if(objects.size() != scores.size()){
			throw new RemoteServiceException(ErrorCode.UNFIT_MATRIX_DATA);
		}
		for (int i = 0; i < objects.size(); i++) {
			JSONObject jsonObject = objects.getJSONObject(i);
			String data = jsonObject.get("data").toString();
			JSONObject dataJsonObj = JsonUtil.toJsonObject(data, JSONObject.class);
			Collection<Object> values = dataJsonObj.values();
			Iterator<Object> iterator = values.iterator();
			List<String> tmp = new ArrayList<>();
			while (iterator.hasNext()){
				Object next = iterator.next();
				if(null == next){
					continue;
				}
				String str = String.valueOf(next);
				if(StringUtils.isBlank(str)){
					continue;
				}
				tmp.add(str);
			}
			res.add(tmp);

		}
		return res;
	}
}
