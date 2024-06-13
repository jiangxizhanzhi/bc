package com.cyberpeace.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.integratation.model.scene.FlowLogsPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.ws.MpEventType;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

	// 该交换机由城市沙盘里面程序创建，监听队列城市沙盘关闭时会自动删除, 交换机创建后不会被删除
	private static final String RBSITUATIONEXCHANGE = "situation";

	private Integer errorCount = 0;
	private String exchangeUrl;

	private CloseableHttpClient httpClient;

	@PostConstruct
	public void init() {
		String mqWebPort = SystemPropUtil.getProp("MQ_WEB_PORT");
		String userName = SystemPropUtil.getProp("MQ_USERNAME");
		String password = SystemPropUtil.getProp("MQ_PASSWORD");
		String mqHost = SystemPropUtil.getProp("MQ_HOST");
		if(mqWebPort == null) {
			mqWebPort = "15672";
		}
//		String url = "http://%s:%s/api/exchanges/%2F/%s/bindings/source";
		exchangeUrl = "http://" + mqHost + ":" + mqWebPort + "/api/exchanges/%2F/" + RBSITUATIONEXCHANGE + "/bindings/source";

		BasicCredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

		httpClient = HttpClients.custom().setDefaultCredentialsProvider(credsProvider).build();
	}


	@Scheduled(fixedRate = 5000)
	public void checkRecord() {
		log.info("checkRecord job is start");
		String mpSceneId = rbSituationService.getMpSceneId();
		String sceneId = rbSituationService.getSceneId();
		if(mpSceneId == null){
			log.info("checkRecord job is end, mpSceneId is null");
			return;
		}

		if (errorCount > 10) {
			log.info("reset id, send rabbitmq message error more than 10 times");
			rbSituationService.reSetId();
			errorCount = 0;
			return;
		}

		// 创建HttpGet对象
		log.info("exchangeUrl is [{}]", exchangeUrl);
		HttpGet httpGet = new HttpGet(exchangeUrl);

		try {
			// 防止有交换机，没有队列时一直发送消息
			CloseableHttpResponse response =httpClient.execute(httpGet);
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");
			final JSONArray queues = JsonUtil.stringToArray(content);
			if (queues.isEmpty()) {
				errorCount = errorCount + 1;
				// 城市沙盘队列没有连上,检查config.txt配置是否正确
				log.error("city not create queues, please check city config.txt is right");
				return;
			}

		} catch (IOException e) {
			log.error("get exchange query is failed [{}]", e.getMessage());
			errorCount = errorCount + 1;
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

		try (Connection connection = ConnectionUtil.getConnection(); Channel channel = connection.createChannel()) {
			analysisFlowLogs(channel, flowLogs, sceneId);
			score(channel, flowLogs);
		} catch (Exception e) {
			errorCount = errorCount + 1;
			log.error("catch error: [{}]", e.getMessage());
		}

		log.info("checkRecord job is end");
	}

	public void analysisFlowLogs(Channel channel, List<JSONObject> flowLogs, String sceneId) throws Exception {
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
			try {
				channel.basicPublish(RBSITUATIONEXCHANGE, sceneId, null,
						msgObject.toString().getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				log.error("analysisFlowLogs channel basicPublish error");
				throw e;
			}
			count++;
			try {
				Thread.sleep(900);
			} catch (InterruptedException e) {
				log.error("thread sleep error", e);
				throw e;
			}
		}

	}

	public void score(Channel channel, List<JSONObject> flowLogs) throws IOException {
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

		try {
			channel.basicPublish(RBSITUATIONEXCHANGE, sceneId, null,
					msgObject.toString().getBytes());
		} catch (IOException e) {
			log.error("score channel basicPublish error");
			throw e;
		}


	}

}
