package com.cyberpeace.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.util.ConnectionUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.integratation.model.scene.FlowLogsPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.ws.MpEventType;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RbSituationJob {

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private RbSituationService rbSituationService;

	@Autowired
	private RbRoomScoreService rbRoomScoreService;

	Map<String, Date> dateMap = new HashMap<>();

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

	@Scheduled(fixedRate = 2000)
	public void checkRecord() {
		String mpSceneId = rbSituationService.getMpSceneId();
		String sceneId = rbSituationService.getSceneId();
		if(mpSceneId == null){
			return;
		}
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
		try{
			analysisFlowLogs(flowLogs, sceneId);
			score(flowLogs);
		}
		catch (Exception e){
			log.error("catch error:",  e);
		}
	}

	public void analysisFlowLogs(List<JSONObject> flowLogs, String sceneId) throws Exception {
		Connection connection = null;
		Channel channel = null;
		try	{
			// 1、获取到连接
			connection = ConnectionUtil.getConnection();
			// 2、从连接中创建通道，使用通道才能完成消息相关的操作
			channel = connection.createChannel();
			int count = 1;
			for(JSONObject flowLog: flowLogs){
				Date lastTime = dateMap.get(sceneId);
				Date time = TimeUtil.toDate1(flowLog.getString("time"));
				if (lastTime == null || lastTime.before(time)) {
					dateMap.put(sceneId, time);
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
				msgMap.put("scene", sceneId);
				msgMap.put("data", JSON.toJSON(rbSituationAttack));
				JSONObject msgObject = new JSONObject(msgMap);
				channel.basicPublish(RBSITUATIONEXCHANGE, sceneId, null,
						msgObject.toString().getBytes(StandardCharsets.UTF_8));
				count++;
				Thread.sleep(900);
			}

		} catch (InterruptedException e) {
			log.warn("Interrupted!", e);
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			log.error(ERRORLOG,	mqHost, mqPort, userName, password);
		}finally {
			if (channel != null) {
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}

	public void score(List<JSONObject> flowLogs) throws Exception {
		String sceneId = rbSituationService.getSceneId();
		JSONObject score = rbRoomScoreService.getAllRbScoreProgress(sceneId, RbRoleGroup.RED_BLUE.getCode());
		Integer redAttackCount = 0;
		Integer blueAttackedCount = 0;
		Map<String , String> nodeMap = rbSituationService.getNodeMap();
		for(JSONObject flowLog: flowLogs){
			String sourceNodeId = flowLog.getString("source_node_id");
			String nodeRole = nodeMap.get(sourceNodeId);
			if(nodeRole != null && nodeRole.equals("red")){
				redAttackCount++;
			}else if(nodeRole != null && nodeRole.equals("blue")){
				blueAttackedCount++;
			}
		}

		Map<String, Integer> data = new HashMap<>();
		data.put("red_attack_count", redAttackCount);
		data.put("blue_attacked_count", blueAttackedCount);
		data.put("red_score", score.getInteger("red_total_score"));
		data.put("blue_score", score.getInteger("blue_total_score"));
		data.put("member_score", 111);
		Map<String, Object> msg = new HashMap<>();
		msg.put("type", "mShowRankinglist");
		msg.put("scene", sceneId);
		msg.put("data", data);
		JSONObject msgObject = new JSONObject(msg);

		Connection connection = null;
		Channel channel = null;
		try {
			// 1、获取到连接
			connection = ConnectionUtil.getConnection();
			// 2、从连接中创建通道，使用通道才能完成消息相关的操作
			channel = connection.createChannel();
			channel.basicPublish(RBSITUATIONEXCHANGE, sceneId, null,
					msgObject.toString().getBytes());
		} catch (Exception e) {
			log.error(ERRORLOG,	mqHost, mqPort, userName, password);
		}finally {
			if (channel != null) {
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		}

	}

}
