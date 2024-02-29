package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConnectionUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationServerRsp;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalSituationService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.FlowLogsPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.ws.MpEventType;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
public class EvalSituationServiceImpl implements EvalSituationService {

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	RabbitTemplate rabbitTemplate;

	private Map<String, Date> dateMap = new HashMap<>();

	private static final String RBSITUATIONEXCHANGE = "situation";

	private static final String ERRORLOG = "An error occurred in the Rabbit MQ connection, host:[{}],port:[{}],userName:[{}],password:[{}]";

	private String mqHost;
	private String mqPort;
	private String userName;
	private String password;

	@PostConstruct
	public void init() {
		mqHost = SystemPropUtil.getProp("MQ_HOST");
		mqPort = SystemPropUtil.getProp("MQ_PORT");
		userName = SystemPropUtil.getProp("MQ_USERNAME");
		password = SystemPropUtil.getProp("MQ_PASSWORD");
	}

	@Override
	public List<EvalSituationListRsp> getAllEvalSituation() {
		return evalTaskMapper.getAllEvalSituation();
	}

	@Override
	public List<EvalSituationServerRsp> getEvalServerByResourceId(String resourceId) {
		List<EvalSituationServerRsp> evalSituationServerRspList = new ArrayList<>();
		resourceId = resourceId.substring(0, resourceId.length() - 1);
		String mpSceneId = localSceneService.getMpSceneIdByBusinessInstanceId(resourceId);

		JSONObject scene = remoteSceneService.getById(mpSceneId, null);
		List<JSONObject> elements = scene.getJSONObject("scene").getJSONObject("topology")
										 .getObject("elements", List.class);
		for (JSONObject jsonObject : elements) {
			EvalSituationServerRsp evalSituationServerRsp = new EvalSituationServerRsp();
			evalSituationServerRsp.setServerId(jsonObject.getString("id"));
			evalSituationServerRsp.setServerName(jsonObject.getString("name"));
			evalSituationServerRspList.add(evalSituationServerRsp);
		}
		return evalSituationServerRspList;
	}

	@Override
	public void pushFlow(String resourceId) {
		String mpSceneId = localSceneService.getMpSceneIdByBusinessInstanceId(resourceId);
		FlowLogsPageContext flowLogsPageContext = new FlowLogsPageContext();
		flowLogsPageContext.setId(mpSceneId);
		flowLogsPageContext.setPerPage(1000);
		flowLogsPageContext.setPage(1);
		flowLogsPageContext.setSort("time");
		// 请求中台数据
		JSONObject jsonObject = remoteSceneService.getFlowLogs(flowLogsPageContext);
		List<JSONObject> flowLogs = JsonUtil
				.toJsonObjectArray(jsonObject.getJSONArray("list").toString(), JSONObject.class);
		flowLogs = Lists.reverse(flowLogs);
		try(Connection connection = ConnectionUtil.getConnection();
			Channel channel = connection.createChannel();
		){
			int count = 1;
			for(JSONObject flowLog: flowLogs){
				Date lastTime = dateMap.get(resourceId);
				Date time = TimeUtil.toDate1(flowLog.getString("time"));
				if (lastTime == null || lastTime.before(time)) {
					dateMap.put(resourceId, time);
				}else {
					continue;
				}
				if(count > 3){
					continue;
				}
				JSONObject rbSituationAttack = new JSONObject();
				rbSituationAttack.put("line_style",1);
				rbSituationAttack.put("src_action",1);
				rbSituationAttack.put("dst_action",1);
				rbSituationAttack.put("first_view",false);
				String enDesc = flowLog.getJSONObject("attack_info").getString("attack_type");
				MpEventType mpEventType = MpEventType.getEnumByEnDesc(enDesc, MpEventType.class);
				if (mpEventType == null) {
					mpEventType = MpEventType.UNKNOWN;
				}
				rbSituationAttack.put("attack_type",mpEventType.getMsg());
				rbSituationAttack.put("src_node", flowLog.getString("source_node_id"));
				rbSituationAttack.put("dst_node", flowLog.getString("target_node_id"));
				Map<String , Object> msgMap = new HashMap<>();
				msgMap.put("type", "mShowAttack");
				msgMap.put("scene", resourceId);
				msgMap.put("data", JSON.toJSON(rbSituationAttack));
				JSONObject msgObject = new JSONObject(msgMap);
				channel.basicPublish(RBSITUATIONEXCHANGE, resourceId, null,
						msgObject.toString().getBytes(StandardCharsets.UTF_8));
				count++;
				Thread.sleep(900);
			}
		}
		catch (Exception e){
			log.error("catch error:",  e);
			log.error(ERRORLOG,	mqHost, mqPort, userName, password);
		}
	}

}
