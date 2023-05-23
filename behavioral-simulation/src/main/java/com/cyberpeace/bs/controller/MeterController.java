package com.cyberpeace.bs.controller;

import com.cyberpeace.bs.service.MeterService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.demo.SingleBehaviorReq;
import com.cyberpeace.bs.service.bo.meter.SimulatorTimer;
import com.cyberpeace.bs.service.bo.simulator.SimulatorAddBo;
import com.cyberpeace.common.model.BaseRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 辅助测试相关接口
 *
 * @author SN
 * @date 2023/05/06
 */
@Api(tags = "行为模拟-辅助测试")
@RestController
@RequestMapping("/bs/meter")
public class MeterController {

    @Autowired
    MeterService meterService;

    @Autowired
    UserSimulatorService userSimulatorService;

    /**
     * 启动单个任务接口
     * 测试特定任务在特定仪表上执行情况
     *
     * @param req 请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("启动单个任务")
    @PostMapping("/start_task")
    public BaseRsp startSingleTask(@RequestBody @Validated SingleBehaviorReq req) {
        meterService.startSingleBehavior(req);
        return new BaseRsp();
    }

    /**
     * 停止单个任务
     * 中止正在进行的测试任务
     *
     * @param req 请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("停止单个行为")
    @DeleteMapping("/stop_task")
    public BaseRsp stopSingleTask(@RequestBody @Validated SingleBehaviorReq req) {
        meterService.stopSingleBehavior(req);
        return new BaseRsp();
    }

    /**
     * 获取定时任务执行器接口
     * 用于查看当前系统存在的行为定时任务
     *
     * @return {@code BaseRsp<List<SimulatorTimer>>}
     */
    @ApiOperation("查看定时任务")
    @GetMapping("/task")
    public BaseRsp<List<SimulatorTimer>> getTimers() {
        List<SimulatorTimer> res = userSimulatorService.showTimers();
        return new BaseRsp<>(res);
    }


    /**
     * 创建仪表
     * 测试接口，没有回调机制
     *
     * @param addBo 新增请求体
     * @return {@code BaseRsp}
     */
    @ApiOperation("创建仪表")
    @PostMapping("/create/simulator")
    public BaseRsp batchCreate(@RequestBody @Validated SimulatorAddBo addBo) {
        userSimulatorService.batchCreate(addBo);
        return new BaseRsp();
    }

}
