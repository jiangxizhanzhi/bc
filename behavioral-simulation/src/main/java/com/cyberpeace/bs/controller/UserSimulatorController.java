package com.cyberpeace.bs.controller;

import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.controller.req.IdReq;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.CommandBo;
import com.cyberpeace.bs.service.bo.CommandExecutor;
import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;
import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.bs.service.bo.statistics.StatisticsRsp;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 用户行为仿真仪表相关接口
 *
 * @author SN
 * @date 2023/05/06
 */
@Api(tags = "用户行为仪表，行为流量发生")
@RestController
@RequestMapping("/bs/simulator")
// @RequiresAuthentication
public class UserSimulatorController {

    @Autowired
    UserSimulatorService userSimulatorService;

    /**
     * 配管创建仪表接口
     * 接收配管系统的请求，创建用户行为仿真仪表
     * 拓扑中每个用户行为节点，都对应创建一个仪表
     *
     * @param commandBo 配管指令
     * @return {@code BaseRsp}
     */
    @ApiOperation("配管创建仪表")
    @PostMapping("/create")
    public BaseRsp batchCreate(@RequestBody @Validated CommandBo<SimulatorAddBo> commandBo) {
        SimulatorAddBo addBo = commandBo.getCommandBody();
        new CommandExecutor(commandBo.getTaskId(), commandBo.getReportUrl(), "创建仪表失败") {

            @Override
            protected void doBusiness() {
                userSimulatorService.batchCreate(addBo);
            }
        }.execute();
        return new BaseRsp();
    }

    /**
     * 修改单个行为仪表
     * 用户动态修改场景时，配管通过该接口修改单个仪表
     *
     * @param commandBo 配管指令
     * @return {@code BaseRsp}
     */
    @ApiOperation("更新单个仪表用户")
    @PatchMapping("/modify")
    public BaseRsp modifySimulator(@RequestBody @Validated CommandBo<SimulatorModifyBo> commandBo) {
        SimulatorModifyBo modifyBo = commandBo.getCommandBody();
        new CommandExecutor(commandBo.getTaskId(), commandBo.getReportUrl(), "更新仪表用户失败") {

            @Override
            protected void doBusiness() {
                userSimulatorService.modifySimulator(modifyBo);
            }
        }.execute();
        return new BaseRsp();
    }

    /**
     * 修改场景中包含的多个仪表
     * 用户动态修改场景时，配管通过该接口修改多个仪表
     *
     * @param commandBo 配管指令
     * @return {@code BaseRsp}
     */
    @ApiOperation("更新场景")
    @PatchMapping("/update")
    public BaseRsp modifyScene(@RequestBody @Validated CommandBo<SimulatorAddBo> commandBo) {
        SimulatorAddBo updateBo = commandBo.getCommandBody();
        new CommandExecutor(commandBo.getTaskId(), commandBo.getReportUrl(), "更新场景失败") {

            @Override
            protected void doBusiness() {
                userSimulatorService.updateBatch(updateBo);
            }
        }.execute();
        return new BaseRsp();
    }

    /**
     * 启动用户流量接口
     * 启动仪表的入口，对应仪表管理页面的“启动”按钮
     *
     * @param idReq id请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("启动用户流量")
    @PostMapping("/start")
    public BaseRsp start(@RequestBody @Validated IdReq idReq) {
        userSimulatorService.startSimulator(idReq.getResourceId());
        return new BaseRsp();
    }

    /**
     * 批量启动行为仪表
     *
     * @param req 请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("批量启动行为仪表")
    @PostMapping("/start_batch")
    public BaseRsp startBatch(@RequestBody @Validated IdListReq req) {
        userSimulatorService.batchStartSimulator(req.getIds());
        return new BaseRsp();
    }

    /**
     * 停止单个行为仪表接口
     *
     * @param idReq id请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("停止用户流量")
    @PostMapping("/stop")
    public BaseRsp stop(@RequestBody @Validated IdReq idReq) {
        userSimulatorService.stopSimulator(idReq.getResourceId());
        return new BaseRsp();
    }

    /**
     * 批量停止行为仪表接口
     *
     * @param req 请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("批量停止行为仪表")
    @PostMapping("/stop_batch")
    public BaseRsp stopBatch(@RequestBody @Validated IdListReq req) {
        userSimulatorService.batchStopSimulator(req.getIds());
        return new BaseRsp();
    }

    /**
     * 获取行为仪表数量，包括总数和已启动数
     *
     * @return {@code BaseRsp<StatisticsRsp>}
     */
    @ApiOperation("行为仪表数量统计")
    @GetMapping("/statistics")
    public BaseRsp<StatisticsRsp> getSimulatorCount() {
        Integer count = userSimulatorService.countSimulators();
        return new BaseRsp<>(new StatisticsRsp(count));
    }

    /**
     * 获取模拟任务数
     *
     * @return {@code BaseRsp<StatisticsRsp>}
     */
    @ApiOperation("行为实时仿真数量统计")
    @GetMapping("/task_count")
    public BaseRsp<StatisticsRsp> getSimulationTaskCount() {
        Integer count = userSimulatorService.countActiveSimulators();
        return new BaseRsp<>(new StatisticsRsp(count));
    }

    /**
     * 获取实时带宽统计数据
     * 返回数据为所有行为仪表的流量速率的代数和
     *
     * @return {@code BaseRsp<Integer>}
     */
    @ApiOperation("行为实时总带宽")
    @GetMapping("/bandwidth_count")
    public BaseRsp<Integer> getBandwidthStatistics() {
        Integer res = userSimulatorService.getBandwidthSum();
        return new BaseRsp<>(res);
    }

    /**
     * 获取仪表列表
     *
     * @param pageReq 分页请求体
     * @return {@code BaseRsp<ListRsp<BehaviorMeterBo>>}
     */
    @ApiOperation("行为仪表分页列表")
    @GetMapping("/flow_generators")
    public BaseRsp<ListRsp<BehaviorMeterBo>> getSimulatorList(MeterPageReq pageReq) {
        ListRsp<BehaviorMeterBo> res = userSimulatorService.pageBehaviorMeters(pageReq);
        return new BaseRsp<>(res);
    }

    /**
     * 获取场景名称列表
     * 用于前端页面的场景下拉菜单
     *
     * @return {@code BaseRsp<List<SceneIdName>>}
     */
    @ApiOperation("试验名称下拉单")
    @GetMapping("/scene_name")
    public BaseRsp<List<SceneIdName>> getSceneList() {
        List<SceneIdName> res = userSimulatorService.getSceneList();
        return new BaseRsp<>(res);
    }

    /**
     * 获取行为仪表详情
     *
     * @param resource_id 资源id
     * @return {@code BaseRsp<SimulatorDetail>}
     */
    @ApiOperation("行为仪表详情")
    @GetMapping("/detail")
    public BaseRsp<SimulatorDetail>
    getDetail(@ApiParam(value = "仪表resource_id", name = "resource_id") String resource_id) {
        SimulatorDetail detail = userSimulatorService.getDetail(resource_id);
        return new BaseRsp<>(detail);
    }

    /**
     * 关闭场景时删除场景相关仪表
     * 关闭场景时，由配管触发
     *
     * @param idListReq id列表请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("关闭场景时删除相关仪表")
    @DeleteMapping("/scene")
    public BaseRsp deleteSceneRelated(@RequestBody @Validated IdListReq idListReq) {
        userSimulatorService.deleteBySceneId(idListReq.getIds());
        return new BaseRsp();
    }

    /**
     * 删除节点
     * 用户动态编辑场景时，删除一个行为节点，删除该行为节点相关联的行为仪表
     *
     * @param idListReq id列表请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("编辑场景时删除一个节点")
    @DeleteMapping("/node")
    public BaseRsp deleteByNode(@RequestBody @Validated IdListReq idListReq) {
        userSimulatorService.deleteByNodeId(idListReq.getIds());
        return new BaseRsp();
    }

    /**
     * 获取行为仪表关联场景id列表
     * 用于和配管同步当前存在哪些活跃场景，避免已关闭的场景相关的仪表未被删除的情况
     *
     * @return {@code BaseRsp<List<String>>}
     */
    @ApiOperation("获取行为仪表关联场景id列表")
    @GetMapping("/scene_ids")
    public BaseRsp<List<String>> getRelatedSceneIds() {
        List<String> sceneIds = userSimulatorService.getSceneIdList();
        return new BaseRsp<>(sceneIds);
    }

    /**
     * 获取仪表的流量监控数据
     *
     * @param resource_id 资源id
     * @param duration    持续时间
     * @return {@code BaseRsp<MonitorDataRes>}
     */
    @ApiOperation("获取行为仪表监控数据")
    @GetMapping("/monitor_data")
    public BaseRsp<MonitorDataRes> getSimulatorMonitor(
            @ApiParam(value = "仪表resource_id", name = "resource_id") String resource_id,
            @ApiParam(value = "监控时长", name = "duration") Integer duration) {
        MonitorDataRes res = userSimulatorService.getMonitorData(resource_id, duration);
        return new BaseRsp(res);
    }

}
