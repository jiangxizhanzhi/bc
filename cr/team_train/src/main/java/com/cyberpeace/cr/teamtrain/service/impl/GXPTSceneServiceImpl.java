package com.cyberpeace.cr.teamtrain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.GXPTSceneMapper;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cr.teamtrain.service.context.TeamSceneViewContext;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 共性平台方法-同步数据
 */
@Service
@Slf4j
public class GXPTSceneServiceImpl {

    @Autowired
    private GXPTSceneMapper gxptSceneMapper;

    @Autowired
    private TeamSceneService teamSceneService;

    @Autowired
    private MpEnvService mpEnvService;

    @PostConstruct
    public void init() {
        String mpHost = mpEnvService.getMpHost();
        MP_UPDATE_SCENE_URL = mpHost + "/api/mp/load/";
        System.out.println(MP_UPDATE_SCENE_URL);
    }

    private static final String ADD_SCENE_URL = "http://ip:port//testApi/zyScene/insert";// 共性平台添加场景接口
    private static final String ADD_TOPOLOGY_URL = "http://ip:port /testApi/zyScene/insertTopoV1";// 共性平台添加场景接口
    private static String MP_UPDATE_SCENE_URL;// mp修改场景信息接口,记录是否同步共性平台

    public void addScene(TeamTrainRoomReq teamTrainRoomReq, TeamRoom teamRoom) {
        String url = ADD_SCENE_URL;
        JSONObject request = new JSONObject();
        request.put("name", teamTrainRoomReq.getName());
        request.put("type", teamTrainRoomReq.getCategory());
        request.put("collectSolution", teamTrainRoomReq.getCategory());
        request.put("startTime", teamTrainRoomReq.getStartTime());
        request.put("endTime", teamTrainRoomReq.getEndTime());
        request.put("ntpdate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        request.put("description", teamTrainRoomReq.getDescription());
        request.put("ifTemplate", "1");
        request.put("template", "");
        request.put("id", "");
        JSONObject resultMap = null;
        try {
            resultMap = HttpClientUtil.post(url, request, JSONObject.class);
        } catch (Exception e) {
            log.error("调用共性平台添加场景接口失败", e);
        }
        // 根据resource_id查询团队训练房间并修改
        String returnInfo = resultMap != null ? String.valueOf(resultMap) : "";
        String returnData = resultMap != null ? String.valueOf(resultMap.get("data")) : "";
        String code = resultMap != null ? String.valueOf(resultMap.get("code")) : "";
        Boolean isSychronized = (code != null && "200".equals(code));
        gxptSceneMapper.updateTeamRoomByResourceId(teamRoom.getResourceId(), isSychronized.toString(), returnData, returnInfo);
        // 同步拓扑信息
        addTopo(teamTrainRoomReq, teamRoom);
    }

    public void addTopo(TeamTrainRoomReq teamTrainRoomReq, TeamRoom teamRoom) {
        TeamSceneViewContext teamSceneViewContext = teamSceneService.viewScene(teamRoom.getResourceId(), null);
        // 拿到拓扑数据
        JSONObject topoData = teamSceneViewContext.getTopoData();
        JSONObject topology = (JSONObject) topoData.get("topology");
        JSONObject plugins = (JSONObject) topoData.get("plugins");

        List<JSONObject> elements = new ArrayList<>();
        for (JSONObject element : (List<JSONObject>) topology.get("elements")) {
            String id = element.get("id").toString();
            JSONObject plugin = (JSONObject) (((JSONObject) ((JSONObject) plugins.get("cpcloud")).get("elements")).get(id));
            JSONObject data = new JSONObject();
            data.put("element", element);
            data.put("plugin", plugin);
            elements.add(data);
        }

        // todo
        JSONObject request = new JSONObject();

        // 回调mp,将场景和拓扑数据表更新(get方法,参数放在url中)
        JSONObject resultMap = null;
        String url = ADD_TOPOLOGY_URL;
        try {
            resultMap = HttpClientUtil.post(url, request, JSONObject.class);
        } catch (Exception e) {
            log.error("调用共性平台添加拓扑接口失败", e);
        }
        // 根据resource_id查询团队训练房间并修改
        String returnInfo = resultMap != null ? String.valueOf(resultMap) : "";
        String returnData = resultMap != null ? String.valueOf(resultMap.get("data")) : "";
        String code = resultMap != null ? String.valueOf(resultMap.get("code")) : "";
        Boolean isSychronized = (code != null && "200".equals(code));
        gxptSceneMapper.updateTeamRoomTopoByResourceId(teamRoom.getResourceId(), isSychronized.toString(), returnData, returnInfo);
    }
}
