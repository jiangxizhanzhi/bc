package com.cyberpeace.cr.hld.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.scene.*;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.dao.entity.HldRoomScene;
import com.cyberpeace.cr.hld.dao.mapper.HldRoomSceneMapper;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HldSceneServiceImpl implements HldSceneService {

    private static final Integer PAUSE_FLAG = 1;

    private static final Integer UNPAUSE_FLAG = 2;

    @Autowired
    private HldRoomService hldRoomService;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private SceneFilterService sceneFilterService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private HldRoomSceneMapper hldRoomSceneMapper;

    @Override
    public String startScene(String roomId, String userId) {

        // 1、用户权限校验
        hldRoomService.checkUserPermission(roomId, userId);

        HldRoom room = hldRoomService.getHldRoom(roomId);

        // 训练暂停状态不能启动场景
        if (room.getStatus().equals(HldRoom.StatusCode.PAUSE.getCode())) {
            throw new BusinessException(CrBusinessErrorCode.ALREADY_PAUSED);
        }

        // 2、如果已经有启动的场景，不能重复启动; 如果有脏数据，清除脏数据
        LocalScene localSceneExisted = localSceneService.getByBusinessInstanceId(roomId);
        if (localSceneExisted != null) {
            if (StringUtils.isBlank(localSceneExisted.getMpSceneId())) {
                clearLocal(room);
            } else {
                throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
            }
        }

        // 3、构建本地scene对象
        LocalScene localScene = createLocalScene(room, room.getCopyedTopoId());

        // 4、启动mp场景
        String sceneId = localSceneService.startScene(localScene);

        // 5、写入拓扑场景映射
        HldRoomScene hldRoomScene = new HldRoomScene();
        hldRoomScene.setHldRoomId(roomId);
        hldRoomScene.setMpSceneId(sceneId);
        hldRoomScene.setStartTime(new Date());

        try {
            // 5、更新本地数据库数据
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                    // 更新表数据
                    localScene.setMpSceneId(sceneId);
                    localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
                    localScene.setStartTime(new Date());
                    room.setLocalSceneId(localScene.getResourceId());
                    room.setStatus(HldRoom.StatusCode.RUNNING.getCode());

                    // 执行表更新
                    localSceneService.save(localScene);
                    hldRoomService.updateById(room);
                    hldRoomSceneMapper.insert(hldRoomScene);
                }
            });
            // 5.1 本地保存数据错误，删除已启动的远程场景
        } catch (Exception e) {
            localSceneService.stopScene(localScene);
            log.error("Failed to save local data:", e);
            throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
        }

        return sceneId;
    }

    @Override
    public SceneViewContext viewScene(String roomId, CrRole role) {
        SceneViewContext ret = new SceneViewContext();
        ret.setRoomId(roomId);
        HldRoom room = hldRoomService.getHldRoom(roomId);

        // 1. 获取用户可见节点
        CrRoleNodesData nodes = getNodes(room, role);

        // 2、尝试获取启动的远程场景
        JSONObject sceneData = getMpSceneData(room);
        if (sceneData != null) {
            sceneFilterService.filterVisibleTopo(sceneData, nodes);
            ret.setSceneData(sceneData);
            ret.setLocalSceneId(room.getLocalSceneId());
            ret.setMpSceneId(sceneData.getString(CrCommonConstants.MP_SCENE_ID_KEY));
        }

        // 3、 写入topo数据
        JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId())
                .getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
        sceneFilterService.filterVisibleTopo(topoData, nodes);
        ret.setTopoData(topoData);

        return ret;
    }

    @Override
    public Boolean stopScene(String roomId, Boolean checkPermission) {

        String userId = UserInfoContext.getUserContext().getUserId();

        if (checkPermission) {
            hldRoomService.checkUserPermission(roomId, userId);
        }

        HldRoom room = hldRoomService.getHldRoom(roomId);

        // 2、如果场景已经停止，返回成功
        if (StringUtils.isBlank(room.getLocalSceneId())) {
            return true;
        }

        Boolean stopResult = localSceneService.stopScene(room.getLocalSceneId());

        if (!stopResult) {
            return stopResult;
        }

        room.setStatus(HldRoom.StatusCode.ENDED.getCode());

        HldRoomScene hldRoomScene = getLatestHldRoomScene(room.getResourceId());

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 清除localScene
                clearLocal(room);

                if (hldRoomScene != null) {
                    hldRoomScene.setEndTime(new Date());
                    hldRoomSceneMapper.updateById(hldRoomScene);
                }
            }
        });

        return stopResult;
    }

    private HldRoomScene getLatestHldRoomScene(String roomId) {
        List<HldRoomScene> hldRoomScenes = hldRoomSceneMapper.selectByRoomId(roomId);
        HldRoomScene hldRoomScene=null;

        if(hldRoomScenes.size()>0){
            hldRoomScene=hldRoomScenes.get(0);
        }

        return hldRoomScene;
    }

    @Override
    public Boolean pauseScene(String roomId) {

        // 1. 校验场景存在和用户权限
        HldRoom room = hldRoomService.getHldRoom(roomId);
        checkBeforePauseOps(room, PAUSE_FLAG);

        // 2、 场景开启的状态下发送mp暂停请求
        if (localSceneService.ensureSceneStarted(room.getLocalSceneId())
                && !localSceneService.pauseScene(room.getLocalSceneId())) {
            throw new BusinessException(CrBusinessErrorCode.FAILED_TO_PAUSE_SCENE);
        }

        room.setStatus(HldRoom.StatusCode.PAUSE.getCode());
        hldRoomService.updateById(room);

        return true;
    }

    @Override
    public Boolean unpauseScene(String roomId) {
        // 1. 校验场景存在和用户权限
        HldRoom room = hldRoomService.getHldRoom(roomId);
        checkBeforePauseOps(room, UNPAUSE_FLAG);

        // 2、 如果mp场景存在，发送mp解除暂停请求
        if (localSceneService.ensureSceneStarted(room.getLocalSceneId())
                && !localSceneService.unpauseScene(room.getLocalSceneId())) {
            throw new BusinessException(CrBusinessErrorCode.FAILED_TO_UNPAUSE_SCENE);
        }

        room.setStatus(HldRoom.StatusCode.RECOVERY.getCode());
        hldRoomService.updateById(room);

        return true;
    }

    private void checkBeforePauseOps(HldRoom room, Integer flag) {

        // 权限校验
        String userId = UserInfoContext.getUserContext().getUserId();
        hldRoomService.checkUserPermission(room.getResourceId(), userId);

        // 校验当前状态
        if (room.isUnopened()) {
            throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
        }
        if (room.isEnded()) {
            throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
        }
        if (flag.equals(PAUSE_FLAG) && room.isPaused()) {
            throw new BusinessException(CrBusinessErrorCode.ALREADY_PAUSED);
        }
        if (flag.equals(UNPAUSE_FLAG) && !room.isPaused()) {
            throw new BusinessException(ErrorCode.NOT_PAUSED);
        }
    }

    @Override
    public List<Flow> getFlowList(String roomId) {

        // 1.创建变量
        HldRoom room = hldRoomService.getHldRoom(roomId);
        String userId = UserInfoContext.getUserContext().getUserId();
        List<Flow> flows = Lists.newArrayList();
        CpFlow cpflow;
        Map<String, String> elementsMap = Maps.newHashMap();

        // 2. 获取topo
        LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
        // 2.1 场景已启动
        if (localScene != null) {
            // 2.1.1 请求sceneData
            JSONObject sceneData = localSceneService.getSceneData(localScene, userId);
            // 2.1.2 转换JsonObject为SceneData对象
            SceneData data = sceneData.toJavaObject(SceneData.class);
            // 2.1.3 获取流量数据
            cpflow = data.getPlugins().getCpFlow();
            // 2.1.4 生成节点数据map
            List<SceneElement> elements = data.getTopology().getElements();
            for (SceneElement element : elements) {
                elementsMap.put(element.getId(), element.getName());
            }
        } else {
            // 2.2 场景未启动
            // 2.2.1 请求topoData
            JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId());
            TopoData data = topoData.getJSONObject(CrCommonConstants.TOPO_DATA_KEY)
                    .toJavaObject(TopoData.class);
            cpflow = data.getPlugins().getCpFlow();

            List<TopoElement> elements = data.getTopology().getElements();
            for (TopoElement element : elements) {
                elementsMap.put(element.getId(), element.getName());
            }
        }
        // 3. 没有流量，返回空列表
        if (cpflow == null) {
            return flows;
        }
        // 4. 获取每个节点的流量，整合成列表
        for (Map.Entry<String, ElementFlowList> entry : cpflow.getElements().entrySet()) {
            String nodeId = entry.getKey();
            String nodeName = elementsMap.get(nodeId);

            for (ElementFlow flow : entry.getValue().getFlows()) {
                Flow flowData = BeanUtils.copy(flow, Flow.class);
                // 4.1 添加节点数据
                flowData.setNodeId(nodeId);
                flowData.setNodeName(nodeName);
                flows.add(flowData);
            }
        }

        // 5. 返回流量数据列表
        return flows;
    }

    private JSONObject getMpSceneData(HldRoom room) {

        String localSceneId = room.getLocalSceneId();

        // localSceneId字段为空
        if (StringUtils.isBlank(localSceneId)) {
            return null;
        }

        // localScene不存在，localSceneId字段是脏数据
        LocalScene localScene = localSceneService.getById(localSceneId);
        if (null == localScene) {
            room.setLocalSceneId("");
            hldRoomService.updateById(room);
            return null;
        }

        // localScene 的 mpSceneId为空，localScene是脏数据
        String mpSceneId = localScene.getMpSceneId();
        if (StringUtils.isBlank(mpSceneId)) {
            clearLocal(room);
            return null;
        }

        // 获取用户id
        String currentUserId;
        try {
            currentUserId = UserInfoContext.getUserContext().getUserId();
        } catch (NullPointerException e) {
            currentUserId = "";
        }

        // 请求远程场景数据
        JSONObject sceneData = localSceneService.getSceneData(mpSceneId, currentUserId);
        // 场景数据为空，清除本地脏数据
        if (null == sceneData) {
            clearLocal(room);
            return null;
        }

        sceneData.put(CrCommonConstants.MP_SCENE_ID_KEY, mpSceneId);

        return sceneData;
    }

    private CrRoleNodesData getNodes(HldRoom hldRoom, CrRole role) {

        // 1、如果是学员角色，直接返回
        if (role.isHldStudent()) {
            return role.getNodeData();
        }

        return null;
    }

    @Override
    public void clearLocal(HldRoom room) {

        String localSceneId = room.getLocalSceneId();
        room.setLocalSceneId("");
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {

            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                hldRoomService.updateById(room);
                localSceneService.removeById(localSceneId);

            }
        });
    }

    private LocalScene createLocalScene(HldRoom room, String topoId) {

        LocalScene localScene = new LocalScene();

        localScene.setBusinessCode(LocalScene.BusinessCode.HLD.getCode());
        localScene.setUserId(UserInfoContext.getUserContext().getUserId());
        localScene.setBusinessInstanceId(room.getResourceId());
        localScene.setTopoId(topoId);
        localScene.setSceneName(room.getTaskName());
        localScene.setResourceId(IDGeneratorUtil.getID());
        localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
        localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
        localScene.setStartTime(new Date());
        localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.TEAM.getCode());
        localScene.setMeta(JSON.toJSONString(
                LocalSceneMeta.builder().appResourceId(room.getResourceId()).build()));

        return localScene;
    }

    @Override
    public JSONObject viewTopoNoAuth(String roomId) {
        HldRoom room = hldRoomService.getHldRoom(roomId);
        JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId());

        return topoData;
    }

    @Override
    public JSONObject viewSceneNoAuth(String roomId) {
        HldRoom room = hldRoomService.getHldRoom(roomId);

        return getMpSceneData(room);
    }

    @Override
    public List<HldRoomScene> selectByRoomId(String roomId) {
        return hldRoomSceneMapper.selectByRoomId(roomId);
    }

}
