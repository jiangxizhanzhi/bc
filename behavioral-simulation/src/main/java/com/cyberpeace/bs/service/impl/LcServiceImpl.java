package com.cyberpeace.bs.service.impl;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.impl.TraceIdContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.bs.dao.mapper.DemoMapper;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.bs.service.bo.behavior.*;
import com.cyberpeace.bs.service.bo.demo.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.demo.TaskParam;
import com.cyberpeace.bs.service.bo.model.Behavior;
import com.cyberpeace.bs.utils.LcVariableModifyReq;
import com.cyberpeace.bs.utils.LcVariablePageReq;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.HttpclientContext;
import com.cyberpeace.common.model.HttpclientModel;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.mysql.cj.xdevapi.JsonArray;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LcServiceImpl implements LcService {

	@Autowired
	DemoMapper demoMapper;

	private static final String TokenPrefix = "Bearer ";

	private String BEHAVIOR_LIST_URL;

	private String TARGET_LIST_URL;
	/**
	 * 龙测请求对接地址
	 */
	private static String LC_HOST_PORT;
	private static String LOGIN_URL;
	private String VARIABLE_URL = "api/v1/data/find-by-page";
	private String VARIABLE_UPDATE_URL = "api/v1/data/update";

	private String RELEASE_TASK = "api/v1/save";

	private static <T> T post(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		// post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		post.setEntity(entity);
		setAuthorization(post);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(post, resultType);
	}

	private static <T> T doHttp(HttpUriRequest httpRequest, Class<T> resultType) {
		HttpResponse response = null;
		String httpResponse = null;
		try {
			// 1、调用远程服务
			response = getClient().execute(httpRequest);

			// 2、解析远程服务的返回值
			httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);

			// 3、记录调用日志
			// HttpclientContext.getContext().addLog(httpResponse);
		} catch (Exception e) {
			try {
				HttpclientContext.getContext().doRemoteException(e);
			} catch (Exception e1) {
				log.error("remote error logging Exception", e1);
			} finally {
				// HttpclientContext.removeContext();
				throw new RemoteServiceException();
			}
		}

		if (StringUtils.isBlank(httpResponse)) {
			HttpclientContext.getContext().addLog("httpResponse is null");
			// HttpclientContext.removeContext();
			throw new RemoteServiceException();
		}

		// 4、解析结果并返回
		return parseHttpResponse(httpResponse, resultType);
	}

	/**
	 * 序列化http返回结果
	 *
	 * @param httpResponse http返回
	 * @param resultType   需要序列化的类型
	 * @param <T>          返回结果类型
	 * @return
	 */
	private static <T> T parseHttpResponse(String httpResponse, Class<T> resultType) {

		try {
			LcResponse baseRsp = JSON.parseObject(httpResponse, LcResponse.class);

			if (!isSuccess(baseRsp)) {
				// 只要中台的错误码不是000000，都认为是有问题的
				log.error("url=[{}], result=[{}] error, code=[{}]",
						HttpclientContext.getContext().getUrl(), httpResponse, baseRsp.getCode());
				throw new RemoteServiceException(baseRsp.getMsg());
			}
			if (null == baseRsp.getData()) {
				return null;
			}
			if (baseRsp.getData().getClass() == String.class) {
				return null;
			}
			return JsonUtil.toJsonObject(baseRsp.getData().toString(), resultType);
		} catch (RemoteServiceException e) {
			throw e;
		} catch (Exception e) {
			// 遇到不能解析的返回结果，直接报错
			// log.error("parse httpResponse error, url=[{}], result=[{}], resultType=[{}]",
			// HttpclientContext.getContext().getUrl(), httpResponse, resultType);
			throw e;
		} finally {
			HttpclientContext.removeContext();
		}
	}

	/**
	 * 获取实际执行请求的httpclient对象。
	 * <p>
	 * 后期使用池化
	 *
	 * @return
	 */
	private static HttpClient getClient() {
		CloseableHttpClient httpClient = HttpClients.custom().build();

		return httpClient;
	}

	/**
	 * 判断请求码
	 * @param rsp
	 * @return
	 */
	private static boolean isSuccess(LcResponse rsp) {
		return rsp.getCode().equals("0");
	}

	private static void setAuthorization(HttpUriRequest request) {
		if (request.getURI().toString().endsWith(LOGIN_URL)) {
			return;
		}
		JSONObject body = new JSONObject();
		body.put("username", "root");
		body.put("password",
				"FHTUELtJcujRpaIVilvAPeDGNrX4sbgCVvGOFaTYcH57WCJfP1BUB158FW0xWXvcrdgfTO8xjFgPl8nUToMrJEKulMyrg7qtD1MLHLyYh7ucbukNQvxJUFNeHRm+SG0qb6D7z6oBhFZoB+w9TLOPsCxTnzCFWCEjviCSg6VJ2Kw=");
		LoginRsp rsp = post(LC_HOST_PORT + LOGIN_URL, body, LoginRsp.class);
		String token = TokenPrefix + rsp.getAccessToken();
		request.setHeader("Authorization", token);
	}

	/**
	 * 设置请求上下文
	 * @param url
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
		HttpclientContext.setContext(httpclientModel);
	}

	@PostConstruct
	public void init() {
		// LC_HOST_PORT = SystemPropUtil.getProp("LC_HOST_PORT");
		LC_HOST_PORT = "http://10.4.1.138/";
		// BEHAVIOR_LIST_URL = LC_HOST_PORT +
		// ConfigUtil.getContextProperty("lc.behavior.list");
		BEHAVIOR_LIST_URL = "api/v1/flowchart/find-by-page";
		// TARGET_LIST_URL = LC_HOST_PORT +
		// ConfigUtil.getContextProperty("lc.target.list");
		LOGIN_URL = "api/v1/user/login";
	}

	@Override
	public ListRsp<LcBehavior> getLcBehaviorList(BehaviorPageReq pageReq) {
		String targetUrl = LC_HOST_PORT + BEHAVIOR_LIST_URL;
		TaskParam taskParam = demoMapper.selectLatest();
		LcBehaviorListReqBody body = new LcBehaviorListReqBody();
		body.setProjectId(taskParam.getProjectId());
		body.setPage(pageReq.getPage());
		body.setSize(pageReq.getPerPage());
		LcBehaviorPageRes lcBehaviorPageRes = post(targetUrl, body, LcBehaviorPageRes.class);
		ListRsp<LcBehavior> res = new ListRsp<>();
		List<LcBehavior> list = Lists.newArrayList();
		for (LcBehaviorRspBo record : lcBehaviorPageRes.getRecords()) {
			LcBehavior lcBehavior = createLcBehavior(record);
			list.add(lcBehavior);
		}
		res.setList(list);
		res.setTotal(lcBehaviorPageRes.getTotal());
		res.setCurrentPage(pageReq.getPage());
		res.setLastPage(res.getTotal() / pageReq.getPerPage() + 1);
		return res;
	}

	private LcBehavior createLcBehavior(LcBehaviorRspBo rspBo) {
		LcBehavior lcBehavior = BeanUtils.copy(rspBo, LcBehavior.class);
		lcBehavior.setAccessOs(1);
		lcBehavior.setNetworkType(1);
		lcBehavior.setTimeCost(1);
		List<String> tags = Lists.newArrayList();
		tags.add("上网");
		lcBehavior.setTag(tags);
		lcBehavior.setStepCount(30);
		lcBehavior.setApplication("Chrome");
		return lcBehavior;
	}

	@Override
	public void startBehavior(String ip, String behaviorId, String variable) {
		// todo
	}

	@Override
	public void stopBehavior(String targetIp, String behaviorId) {
		// todo
	}

	@Override
	public List<TargetBo> getDeviceList(String search) {
		String targetUrl = TARGET_LIST_URL + "/?search=" + search;
		JsonArray res = HttpClientUtil.rawGet(targetUrl, JsonArray.class);
		List<TargetBo> ret = Lists.newArrayList();
		// todo

		return ret;
	}

	@Override
	public List<Behavior> getLcBehaviorListByIds(Collection<String> behaviorIds) {
		BehaviorPageReq pageReq = new BehaviorPageReq();
		pageReq.setPerPage(100);
		ListRsp<LcBehavior> listRsp = getLcBehaviorList(pageReq);
		List<LcBehavior> lcBehaviors = listRsp.getList();
		List<Behavior> res = Lists.newArrayList();
		lcBehaviors.forEach(lcBehavior -> {
			if (behaviorIds.contains(lcBehavior.getId())) {
				Behavior behavior = BeanUtils.copy(lcBehavior, Behavior.class);
				res.add(behavior);
			}
		});
		return res;
	}

	@Override
	public VariableListBo getVariableList(String behaviorId) {
		TaskParam taskParam = demoMapper.selectLatest();
		String targetUrl = LC_HOST_PORT + VARIABLE_URL;
		LcVariablePageRes res = post(targetUrl, new LcVariablePageReq(taskParam.getProjectId()),
				LcVariablePageRes.class);
		VariableListBo listBo = new VariableListBo();
		listBo.setBehaviorId(behaviorId);
		List<VariableBo> variableBos = Lists.newArrayList();
		res.getRecords().forEach(record -> {
			VariableBo variableBo = BeanUtils.copy(record, VariableBo.class);
			variableBo.setType(1);
			variableBos.add(variableBo);
		});
		listBo.setVariables(variableBos);
		return listBo;
	}

	@Override
	public void updateVariable(VariableModifyReq req) {
		String targetUrl = LC_HOST_PORT + VARIABLE_UPDATE_URL;
		TaskParam taskParam = demoMapper.selectLatest();
		for (VariableSimple variable : req.getVariables()) {
			LcVariableModifyReq body = new LcVariableModifyReq();
			body.setId(variable.getId());
			body.setName(variable.getName());
			body.setValue(variable.getValue());
			body.setEnvironmentId(taskParam.getEnvironmentId());
			body.setProjectId(taskParam.getProjectId());
			post(targetUrl, body, String.class);
		}
	}

	@Override
	public void startDemoBehavior(ReleaseTaskReq req) {
		String targetUrl = LC_HOST_PORT + RELEASE_TASK;
		post(targetUrl, req, String.class);
	}

}

@Data
class LcResponse<T> {

	public T data;

	public String code;

	public String msg;
}

@Data
class LoginRsp {

	private String accessToken;
}