package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceApplyListRsp;
import com.cyberpeace.cr.crbase.controller.rsp.RoomDeviceOccupyRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoomDeviceApply;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoomDeviceApplyMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrRoomDeviceApplyService;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.WsMessageBO;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTerminalService;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CrRoomDeviceApplyServiceImpl extends ServiceImpl<CrRoomDeviceApplyMapper, CrRoomDeviceApply> implements CrRoomDeviceApplyService {
    @Autowired
    private CrRoleService crRoleService;

    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private RemoteTerminalService remoteTerminalService;

    @Autowired
    private MyWebSocketHandler webSocketHandler;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private RemoteSceneService remoteSceneService;

    private CrRoomDeviceApply getObject(String resourceId) {
        CrRoomDeviceApply crRoomDeviceApply = getById(resourceId);
        if (crRoomDeviceApply == null) {
            throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
        }
        return crRoomDeviceApply;
    }

    private WebSocketMessageContext makeContext(String data, String businessId) {
        // 构建需要向页面推送的消息上下文
        CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
        identifier.setPath(WsPath.CR_SCENE);
        identifier.setBusiness_id(businessId);
        return WebSocketMessageContext.builder().data(data).messageIdentifier(identifier).build();
    }

    public void pushSceneData(CrRoomDeviceApply crRoomDeviceApply) {
        JSONObject data = new JSONObject();
        data.put("device_id", crRoomDeviceApply.getSceneDeviceId());
        data.put("device_origin_id", crRoomDeviceApply.getDeviceId());
        data.put("status", CrRoomDeviceApply.Status.APPLIED_LIST.contains(crRoomDeviceApply.getStatus())
                ? crRoomDeviceApply.getStatus()
                : null);
        WsMessageBO<JSONObject> payload = new WsMessageBO<>("apply_device", data);

        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(crRoomDeviceApply.getRoomId(), crRoomDeviceApply.getApplyUserId());
        String businessId = crRoomDeviceApply.isSingle()
                ? crRoomDeviceApply.getBusinessTemplateId() + "_" + crRole.getUserIdList().get(0)
                : crRoomDeviceApply.getRoomId();

        webSocketHandler.pushMessageToUsers(
                makeContext(JSON.toJSONString(payload, SerializerFeature.WriteMapNullValue),
                        businessId),
                crRole.getUserIdList()
        );
    }

    @Override
    public void pushSceneData(String deviceId, String deviceOriginId, String roomId, List<String> userIds) {
        JSONObject data = new JSONObject();
        data.put("device_id", deviceId);
        data.put("device_origin_id", deviceOriginId);
        data.put("status", null);
        WsMessageBO<JSONObject> payload = new WsMessageBO<>("apply_device", data);

        webSocketHandler.pushMessageToUsers(
                makeContext(JSON.toJSONString(payload, SerializerFeature.WriteMapNullValue), roomId),
                userIds
        );
    }

    private void pushElementData(CrRoomDeviceApply crRoomDeviceApply) {
        String businessId = crRoomDeviceApply.isSingle()
                ? crRoomDeviceApply.getBusinessTemplateId() + "_" + crRoomDeviceApply.getApplyUserId()
                : crRoomDeviceApply.getRoomId();

        LocalScene localScene = localSceneService.getByBusinessInstanceId(businessId);
        if (localScene == null) {
            return;
        }
        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(crRoomDeviceApply.getRoomId(), crRoomDeviceApply.getApplyUserId());

        JSONObject sceneData = localSceneService.getSceneData(localScene.getMpSceneId(),
                crRoomDeviceApply.getApplyUserId());

        filterApplyDevice(sceneData, crRole);

        JSONObject data = new JSONObject();

        JSONObject elementPorts = sceneData.getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("element_ports");
        JSONObject pluginElements = sceneData.getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements");
        JSONObject sceneElementPortPluginData = new JSONObject();
        JSONObject sceneElementPluginData = new JSONObject();
        JSONArray elements = sceneData.getJSONObject("topology").getJSONArray("elements");

        for (int i = 0; i < elements.size(); i++) {
            JSONObject element = elements.getJSONObject(i);
            if (element.getString("id").equals(crRoomDeviceApply.getSceneDeviceId())) {
                data.put("scene_element", element);
                JSONObject cpCloud = new JSONObject();
                JSONArray ports = element.getJSONArray("ports");
                for (int j = 0; j < ports.size(); j++) {
                    cpCloud.put(ports.getJSONObject(j).getString("id"), elementPorts.getJSONObject(ports.getJSONObject(j).getString("id")));
                }
                sceneElementPortPluginData.put("cpcloud", cpCloud);
                sceneElementPluginData.put("cpcloud", pluginElements.getJSONObject(crRoomDeviceApply.getSceneDeviceId()));

                break;
            }
        }

        data.put("cscene_id", localScene.getMpSceneId());
        data.put("scene_element_port_plugin_data", sceneElementPortPluginData);
        data.put("scene_element_plugin_data", sceneElementPluginData);
        data.put("scene_id", sceneData.getJSONObject("topology").getJSONObject("scene").getString("eid"));

        WsSceneReportBO reportBO = new WsSceneReportBO("", "scene_element", replaceAccessIp(data));


        WebSocketMessageContext messageContext = makeContext(JsonUtil.toJsonString(reportBO),
                businessId);
        webSocketHandler.pushMessageToUsers(messageContext, crRole.getUserIdList());
    }

    private void filterApplyDevice(JSONObject sceneData, CrRole crRole) {
        Map<String, CrRoomDeviceApply> crRoomDeviceApplyMap = lambdaQuery()
                .eq(CrRoomDeviceApply::getRoomId, crRole.getRoomId())
                .eq(CrRoomDeviceApply::getApplyRoleId, crRole.getResourceId())
                .in(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.APPLIED_LIST)
                .list()
                .stream().collect(Collectors.toMap(
                        CrRoomDeviceApply::getDeviceId,
                        crRoomDeviceApply -> crRoomDeviceApply
                ));

        JSONObject elements = sceneData.getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements");
        for (String imageId: elements.keySet()) {
            JSONObject elementData = elements.getJSONObject(imageId);

            if (elementData.get("image_type").equals("entity")) {
                CrRoomDeviceApply crRoomDeviceApply = crRoomDeviceApplyMap.get(elementData.getString("image_uuid"));
                sceneData.getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements")
                        .getJSONObject(imageId)
                        .put("apply_status", crRoomDeviceApply == null ? null : crRoomDeviceApply.getStatus());

                if (crRoomDeviceApply == null
                        || !crRoomDeviceApply.getStatus().equals(CrRoomDeviceApply.Status.AGREE.getCode())) {
                    JSONArray jsonArray = sceneData.getJSONObject("topology").getJSONArray("elements");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject elementInfo = jsonArray.getJSONObject(i);
                        if (elementInfo.getString("id").equals(imageId)) {
                            JSONArray ports = elementInfo.getJSONArray("ports");
                            for (int j = 0; j < ports.size(); j++) {
                                ports.getJSONObject(j).put("access_modes", Lists.newArrayList());
                                ports.getJSONObject(j).put("ip", null);
                                sceneData.getJSONObject("plugins")
                                        .getJSONObject("cpcloud")
                                        .getJSONObject("element_ports")
                                        .getJSONObject(ports.getJSONObject(j).getString("id"))
                                        .put("access_modes", Lists.newArrayList());
                            }
                            break;
                        }
                    }
                }

            }
        }
    }

    private JSONObject replaceAccessIp(JSONObject topoData) {
        try {
            JSONObject elementPorts = topoData.getJSONObject("scene_element_port_plugin_data").getJSONObject("cpcloud");
            remoteSceneService.replaceAccessIpExe(elementPorts);
        } catch (Exception e) {
            log.error("[dispatch] access ip replacement failed:", e);
        }
        return topoData;
    }

    @Override
    public Boolean apply(String roomId, String roomName, String deviceId, String localSceneId, Integer businessType, String businessTemplateId) {

        String userId = UserInfoContext.getUserContext().getUserId();
        CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
        if (crRole == null) {
            throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
        }

        if (!lambdaQuery()
                .eq(CrRoomDeviceApply::getRoomId, roomId)
                .eq(CrRoomDeviceApply::getDeviceId, deviceId)
                .eq(CrRoomDeviceApply::getApplyUserId, userId)
                .in(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.APPLIED_LIST)
                .list().isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.HAVE_APPLIED);
        }

        CrRoomDeviceApply crRoomDeviceApply = new CrRoomDeviceApply();

        crRoomDeviceApply.setRoomId(roomId);
        crRoomDeviceApply.setRoomName(roomName);
        crRoomDeviceApply.setDeviceId(deviceId);
        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.APPLYING.getCode());
        crRoomDeviceApply.setApplyUserId(userId);
        crRoomDeviceApply.setApplyRoleId(crRole.getResourceId());
        crRoomDeviceApply.setApplyTime(new Date());
        crRoomDeviceApply.setBusinessType(businessType);
        if (StringUtils.isNotEmpty(businessTemplateId)) {
            crRoomDeviceApply.setBusinessTemplateId(businessTemplateId);
        }

        // 获取场景中实装设备id
        LocalScene localScene = localSceneService.getById(localSceneId);
        JSONObject sceneData = localSceneService.getSceneData(localScene.getMpSceneId(), userId);

        JSONObject elements = sceneData.getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements");
        for (String imageId: elements.keySet()) {
            JSONObject elementData = elements.getJSONObject(imageId);
            if (elementData.getString("image_uuid").equals(crRoomDeviceApply.getDeviceId())) {
                crRoomDeviceApply.setSceneDeviceId(imageId);
                break;
            }
        }

        Boolean result = save(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.apply(crRoomDeviceApply.getDeviceId());
            pushElementData(crRoomDeviceApply);
            pushSceneData(crRoomDeviceApply);
        }
        return result;
    }

    @Override
    public RoomDeviceOccupyRsp occupy(String deviceId) {
        CrRoomDeviceApply crRoomDeviceApply = lambdaQuery()
                .eq(CrRoomDeviceApply::getDeviceId, deviceId)
                .eq(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.AGREE.getCode())
                .one();

        if (crRoomDeviceApply == null) {
            return null;
        }

        RoomDeviceOccupyRsp roomDeviceOccupyRsp = new RoomDeviceOccupyRsp();
        BeanUtils.copyProperties(crRoomDeviceApply, roomDeviceOccupyRsp);
        UserInfo userInfo = remoteUserService.getById(roomDeviceOccupyRsp.getApplyUserId());
        roomDeviceOccupyRsp.setApplyUsername(userInfo.getUserName());

        return roomDeviceOccupyRsp;
    }

    @Override
    public ListRsp<RoomDeviceApplyListRsp> list(Page page, String deviceId) {
        IPage<RoomDeviceApplyListRsp> crRoomDeviceApplyIPage = this.getBaseMapper().paging(page, deviceId);

        // 获取用户ID列表
        List<String> userIdList = crRoomDeviceApplyIPage.getRecords().stream().map(
                RoomDeviceApplyListRsp::getApplyUserId
        ).collect(Collectors.toList());

        Map<String, String> usernameMap = remoteUserService.getByIdList(userIdList).stream().collect(
                Collectors.toMap(UserInfo::getUserId, UserInfo::getUserName)
        );
        crRoomDeviceApplyIPage.getRecords().forEach(
                item -> item.setApplyUsername(usernameMap.get(item.getApplyUserId()))
        );


        return PageUtil.getListResult(crRoomDeviceApplyIPage);
    }

    @Override
    public Boolean allow(String applyId) {
        CrRoomDeviceApply crRoomDeviceApply = getObject(applyId);

        String deviceId = crRoomDeviceApply.getDeviceId();
        List<CrRoomDeviceApply> crRoomDeviceApplyList = lambdaQuery()
                .eq(CrRoomDeviceApply::getDeviceId, deviceId)
                .eq(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.AGREE.getCode())
                .list();

        // 单个设备仅供一个角色使用
        if (!crRoomDeviceApplyList.isEmpty()) {
            throw new BusinessException(CrBusinessErrorCode.DEVICE_OCCUPIED);
        }

        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.AGREE.getCode());
        crRoomDeviceApply.setAllowUserId(UserInfoContext.getUserContext().getUserId());
        crRoomDeviceApply.setAllowTime(new Date());

        Boolean result = updateById(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.allot(crRoomDeviceApply.getDeviceId());
            pushElementData(crRoomDeviceApply);
            pushSceneData(crRoomDeviceApply);
        }
        return result;
    }

    @Override
    public Boolean refuse(String applyId) {
        CrRoomDeviceApply crRoomDeviceApply = getObject(applyId);
        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.REFUSE.getCode());

        Boolean result = updateById(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.refuse(crRoomDeviceApply.getDeviceId());
            pushElementData(crRoomDeviceApply);
            pushSceneData(crRoomDeviceApply);
        }
        return result;
    }

    @Override
    public Boolean recycle(String roomId, String deviceId) {
        CrRoomDeviceApply crRoomDeviceApply = lambdaQuery()
                .eq(CrRoomDeviceApply::getRoomId, roomId)
                .eq(CrRoomDeviceApply::getDeviceId, deviceId)
                .eq(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.AGREE.getCode())
                .one();

        if (crRoomDeviceApply == null) {
            throw new BusinessException();
        }
        crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.RECYCLED.getCode());

        Boolean result = updateById(crRoomDeviceApply);
        if (result) {
            remoteTerminalService.recycle(crRoomDeviceApply.getDeviceId());
            pushElementData(crRoomDeviceApply);
            pushSceneData(crRoomDeviceApply);
        }
        return result;
    }

    @Override
    public void releaseApply(String roomId) {
        List<CrRoomDeviceApply> crRoomDeviceApplyList = lambdaQuery()
                .eq(CrRoomDeviceApply::getRoomId, roomId)
                .in(CrRoomDeviceApply::getStatus, CrRoomDeviceApply.Status.APPLIED_LIST)
                .list();
        if (crRoomDeviceApplyList.isEmpty()) {
            return;
        }

        crRoomDeviceApplyList.forEach(crRoomDeviceApply -> {
            if (crRoomDeviceApply.getStatus().equals(CrRoomDeviceApply.Status.AGREE.getCode())) {
                remoteTerminalService.recycle(crRoomDeviceApply.getDeviceId());
                crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.RECYCLED.getCode());
            } else if (crRoomDeviceApply.getStatus().equals(CrRoomDeviceApply.Status.APPLYING.getCode())) {
                remoteTerminalService.refuse(crRoomDeviceApply.getDeviceId());
                crRoomDeviceApply.setStatus(CrRoomDeviceApply.Status.REFUSE.getCode());
            }
        });

        updateBatchById(crRoomDeviceApplyList);
    }
}
