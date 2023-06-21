package com.cyberpeace.bs.service.bo;

import java.util.List;
import java.util.TimerTask;

import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.bo.lc.ReleaseTaskReq;
import com.cyberpeace.bs.service.bo.simulator.VariableBo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SpringContextUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 行为模拟定时任务
 * 行为服务中真正执行的行为任务
 *
 * @author SN
 * @date 2023/05/08
 */
@Slf4j
public class BsTimerTask extends TimerTask {

    /**
     * 设备ip地址
     */
    private final String deviceIp;

    /**
	 * 配管设备id
	 */
    private final String deviceId;

	/**
	 * 运维靶标id
	 */
	private final String ywId;

    /**
     * 是否在实体设备上执行
     */
    private final Boolean isEntity;

    /**
     * 行为id
     */
    private final String behaviorId;

    /**
     * 仪表id
     */
    private final String simulatorId;

    /**
     * 变量
     */
    private final String variable;


    public BsTimerTask(UserSimulator simulator, String behaviorId, String variable) {
        this.deviceId = simulator.getDeviceId();
		this.ywId = simulator.getYwId();
        this.deviceIp = simulator.getDeviceIp();
        this.isEntity = simulator.getIsEntity();
        this.behaviorId = behaviorId;
        this.simulatorId = simulator.getResourceId();
        this.variable = variable;
    }

    @Override
    public void run() {
        try {
            log.info("timer task [{}] of user simulator [{}] starts with variable:[{}]", behaviorId,
                    simulatorId, variable);

            ReleaseTaskReq req = new ReleaseTaskReq();
			// 获取service
			LcService lcService = SpringContextUtil.getBeanByClass(LcService.class);
            // if executed in actual device
			if (Boolean.TRUE.equals(this.isEntity)) {
				// 实体设备的运维id就是lc的device id
				req.setDeviceId(this.ywId);
            } else {
				// 查询lc device 的id
				req.setDeviceId(lcService.getDeviceIdByUuid(this.deviceId));
            }
			// set ip
            req.setIp(deviceIp);
            // 设置行为id
            req.setFlowChart(behaviorId);
            // 解析行为变量
            List<VariableBo> variableBos = JsonUtil.toJsonObjectArray(variable, VariableBo.class);
            variableBos.forEach(variableBo -> req.addVariable(variableBo.getName(), variableBo.getValue()));
            // 启动行为
            lcService.startBehavior(req);

            UserSimulatorService userSimulatorService = SpringContextUtil
                    .getBeanByClass(UserSimulatorService.class);
            // 更新仪表记录的任务记录
            userSimulatorService.updateProcess(simulatorId, behaviorId);
        } catch (Exception e) {
            log.error("User simulator timer task failed: ", e);
        }
    }
}
