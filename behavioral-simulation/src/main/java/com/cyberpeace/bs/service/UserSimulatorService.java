package com.cyberpeace.bs.service;

import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.bo.meter.SimulatorTimer;
import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;
import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.common.model.ListRsp;

import java.util.List;

/**
 * 用户仪表Service
 *
 * @author SN
 * @date 2023/05/08
 */
public interface UserSimulatorService {

    /**
     * 启动用户模拟
     *
     * @param resourceId 用户模拟id
     */
    void startSimulator(String resourceId);

    /**
     * 批量启动仪表
     *
     * @param simulatorIds 仪表id列表
     */
    void batchStartSimulator(List<String> simulatorIds);

    /**
     * 停止流量发生
     *
     * @param resourceId 前台用户流量id
     */
    void stopSimulator(String resourceId);

    /**
     * 批量停止行为仪表
     *
     * @param simulatorIds 仪表id列表
     */
    void batchStopSimulator(List<String> simulatorIds);

    /**
     * 发送执行行为请求之后，更新simulator进度
     *
     * @param simulatorId 行为仪表id
     * @param behaviorId  记录行为id
     */
    void updateProcess(String simulatorId, String behaviorId);

    /**
     * 配管批量创建仪表
     *
     * @param addBo 配管发来的请求体
     */
    void batchCreate(SimulatorAddBo addBo);

    /**
     * 统计当前仪表数量
     *
     * @return 仪表数量
     */
    Integer countSimulators();

    /**
     * 统计当前活跃行为仪表
     *
     * @return 当前活跃行为仪表数
     */
    Integer countActiveSimulators();

    /**
     * 分页列出行为模拟仪表
     *
     * @param pageReq 分页请求体
     * @return 行为仪表列表
     */
    ListRsp<BehaviorMeterBo> pageBehaviorMeters(MeterPageReq pageReq);

    /**
     * 获取仪表详情
     *
     * @param simulatorId 仪表id
     * @return 仪表详情
     */
    SimulatorDetail getDetail(String simulatorId);

    /**
     * 关闭场景时删除关联仪表
     *
     * @param sceneIdList 场景id
     */
    void deleteBySceneId(List<String> sceneIdList);

    /**
     * 获取仪表关联的全部场景
     *
     * @return 全部场景id和名称
     */
    List<SceneIdName> getSceneList();

    /**
     * 获取仪表，
     *
     * @param resourceId 仪表id
     * @return 仪表
     */
    UserSimulator getOrException(String resourceId);

    /**
     * 获取行为仪表监控数据
     *
     * @param resourceId 资源id
     * @param duration   持续时间
     * @return {@code MonitorDataRes}
     */
    MonitorDataRes getMonitorData(String resourceId, Integer duration);

    /**
     * 获取仪表关联的全部场景id
     *
     * @return 场景id
     */
    List<String> getSceneIdList();

    /**
     * 更新单个仪表包含的用户行为
     *
     * @param modifyBo 修改请求体
     */
    void modifySimulator(SimulatorModifyBo modifyBo);

    /**
     * 动态更新场景时，更新行为仪表
     *
     * @param updateBo 更新请求体
     */
    void updateBatch(SimulatorAddBo updateBo);

    /**
     * 展示现存的定时任务
     *
     * @return 现存的定时任务列表
     */
    List<SimulatorTimer> showTimers();

    /**
     * 获取实时总带宽
     *
     * @return 实时总带宽
     */
    Integer getBandwidthSum();

    /**
     * 动态更新场景时, 删除特定节点的仪表
     *
     * @param nodeIds 节点id列表
     */
    void deleteByNodeId(List<String> nodeIds);

    /**
     * 立即启动仪表包含的单个行为
     *
     * @param simulatorId 仪表id
     * @param userModelId 用户模型id
     * @param behaviorId  行为id
     */
    void startSingle(String simulatorId, String userModelId, String behaviorId);
}
