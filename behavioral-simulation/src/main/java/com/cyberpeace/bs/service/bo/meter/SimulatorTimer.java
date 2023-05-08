package com.cyberpeace.bs.service.bo.meter;

import lombok.Data;
import org.apache.commons.compress.utils.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * 仪表定时器
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
public class SimulatorTimer implements Serializable {

    /**
     * 仪表id
     */
    private String simulatorId;

    /**
     * 用户模型
     */
    private List<TimerBo> userModels = Lists.newArrayList();

    public SimulatorTimer(String simulatorId) {
        this.simulatorId = simulatorId;
    }

    /**
     * 添加用户
     *
     * @param userModelId 用户模型id
     * @param userName    用户名称
     */
    public void addUser(String userModelId, String userName) {
        TimerBo timerBo = new TimerBo();
        timerBo.setUserModelId(userModelId);
        timerBo.setUserName(userName);
        this.userModels.add(timerBo);
    }
}

/**
 * 定时任务执行器
 *
 * @author SN
 * @date 2023/05/06
 */
@Data
class TimerBo implements Serializable {

    /**
     * 用户模型id
     */
    private String userModelId;

    /**
     * 用户名称
     */
    private String userName;
}
