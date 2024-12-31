package com.cyberpeace.job;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.evaluation.service.EvalStepService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LocalLoadSituationJob {

    @Autowired
    private EvalStepService evalStepService;

    @Autowired
    private RemoteSceneService remoteSceneService;


    //	设置2秒执行一次任务
    @Scheduled(fixedRate = 2000)
    public void localLoadSituationTask() {
//        查询要执行动效数据
        JSONArray data = remoteSceneService.getProcessExecutionRecords();
//        发送动效
        for (int i = 0; i < data.size(); i++) {
            JSONObject jsonObject = data.getJSONObject(i);
//            TODO 包装数据发送态势动效
            String scene = jsonObject.getString("scene");
            String loadNodeName = jsonObject.getString("load_node_name");
            String loadNode = jsonObject.getString("load_node");
            String destinationNode = jsonObject.getString("destination_node");
            String resourceId = jsonObject.getString("resource_id");

            evalStepService.buildAndSendSituationAttackData(scene, loadNodeName + "载荷执行", loadNode, destinationNode);

            evalStepService.packageAndSendAttackData(resourceId, loadNode, destinationNode);
        }

    }
}
