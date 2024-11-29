package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.ExcelDataHandler;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalSituationServerRsp;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTestInstanceMapper;
import com.cyberpeace.cr.evaluation.service.EvalSituationService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.FlowLogsPageContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.ws.MpEventType;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EvalSituationServiceImpl implements EvalSituationService {

    @Autowired
    private EvalTestInstanceMapper evalTestInstanceMapper;

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
        return evalTestInstanceMapper.getAllEvalSituation();
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
        resourceId = resourceId.substring(0, resourceId.length() - 1);
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
        try (Connection connection = ConnectionUtil.getConnection();
             Channel channel = connection.createChannel();
        ) {
            int count = 1;
            for (JSONObject flowLog : flowLogs) {
                Date lastTime = dateMap.get(resourceId);
                Date time = TimeUtil.toDate1(flowLog.getString("time"));
                if (lastTime == null || lastTime.before(time)) {
                    dateMap.put(resourceId, time);
                } else {
                    continue;
                }
                if (count > 3) {
                    continue;
                }
                JSONObject rbSituationAttack = new JSONObject();
                rbSituationAttack.put("line_style", 1);
                rbSituationAttack.put("src_action", 1);
                rbSituationAttack.put("dst_action", 1);
                rbSituationAttack.put("first_view", false);
                String enDesc = flowLog.getJSONObject("attack_info").getString("attack_type");
                MpEventType mpEventType = MpEventType.getEnumByEnDesc(enDesc, MpEventType.class);
                if (mpEventType == null) {
                    mpEventType = MpEventType.UNKNOWN;
                }
                rbSituationAttack.put("attack_type", mpEventType.getMsg());
                rbSituationAttack.put("src_node", flowLog.getString("source_node_id"));
                rbSituationAttack.put("dst_node", flowLog.getString("target_node_id"));
                Map<String, Object> msgMap = new HashMap<>();
                msgMap.put("type", "mShowAttack");
                msgMap.put("scene", resourceId);
                msgMap.put("data", JSON.toJSON(rbSituationAttack));
                JSONObject msgObject = new JSONObject(msgMap);
                channel.queueDeclare(resourceId, true, false, false, null);
                channel.basicPublish("", resourceId, null,
                        msgObject.toString().getBytes(StandardCharsets.UTF_8));
                count++;
                Thread.sleep(900);
            }
        } catch (Exception e) {
            log.error("catch error:", e);
            log.error(ERRORLOG, mqHost, mqPort, userName, password);
        }
    }

    @Override
    public void pushLoadResult(String resourceId, MultipartFile multipartFile) {
        String mpSceneId = localSceneService.getMpSceneIdByBusinessInstanceId(resourceId);
        JSONObject scene = remoteSceneService.getById(mpSceneId, null);
        Map<String,JSONObject> elementsMap = scene.getJSONObject("scene")
                .getJSONObject("plugins")
                .getJSONObject("cpcloud")
                .getObject("elements", Map.class);
        Set<Map.Entry<String, JSONObject>> entries = elementsMap.entrySet();
        HashMap<String, String> map = new HashMap<>();
        ArrayList<String> elementIds = new ArrayList<>();
        ArrayList<String> loadIds = new ArrayList<>();
        for (Map.Entry<String, JSONObject> entry : entries) {
            String elementId = entry.getKey();
            JSONObject element = entry.getValue();
            if(StringUtils.isNotBlank(element.getString("load_tool_id"))){
                map.put(element.getString("load_tool_id"),elementId);
                loadIds.add(elementId);
            }else {
                elementIds.add(elementId);
            }
        }
        Random random = new Random();

        String originalFileName = multipartFile.getOriginalFilename();
        String fileSuffix = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
        File file = null;
        try (Connection connection = ConnectionUtil.getConnection();
             Channel channel = connection.createChannel();
        ) {
            file = FileUtil.saveFile(multipartFile, ZipUtil.generatePath());

            if ("xls".equals(fileSuffix) || "xlsx".equals(fileSuffix)) {
                ExcelDataHandler excelDataHandler = new ExcelDataHandler(file.getPath(), 0);
                List<List<String>> fileCount = excelDataHandler.getSheetData(1);
                for (int i = 0; i < fileCount.size(); i++) {
                    List<String> countData = fileCount.get(i);
                    if (countData.get(9) == null || !"1005010101".equals(countData.get(9))) continue;
                    Date lastTime = dateMap.get(resourceId);
                    Date time = new Date();
                    if (lastTime == null || lastTime.before(time)) {
                        dateMap.put(resourceId, time);
                    } else {
                        continue;
                    }
                    JSONObject rbSituationAttack = new JSONObject();
                    rbSituationAttack.put("line_style", 1);
                    rbSituationAttack.put("src_action", 1);
                    rbSituationAttack.put("dst_action", 1);
                    rbSituationAttack.put("first_view", false);

                    MpEventType mpEventType = MpEventType.UNKNOWN;

                    rbSituationAttack.put("attack_type", mpEventType.getMsg());
                    rbSituationAttack.put("src_node", map.get(countData.get(0)));
                    if(elementIds.size()>0){
                        rbSituationAttack.put("dst_node", elementIds.get(random.nextInt(elementIds.size())));
                    }else {
                        rbSituationAttack.put("dst_node", loadIds.get(random.nextInt(loadIds.size())));
                    }
                    Map<String, Object> msgMap = new HashMap<>();
                    msgMap.put("type", "mShowAttack");
                    msgMap.put("scene", resourceId);
                    msgMap.put("data", JSON.toJSON(rbSituationAttack));
                    JSONObject msgObject = new JSONObject(msgMap);
                    channel.queueDeclare(resourceId, true, false, false, null);
                    channel.basicPublish("", resourceId, null,
                            msgObject.toString().getBytes(StandardCharsets.UTF_8));
                    Thread.sleep(900);
                }
            }
            file.delete();
        } catch (Exception e) {
            log.error("catch error:", e);
            log.error(ERRORLOG, mqHost, mqPort, userName, password);
        }
    }

}
