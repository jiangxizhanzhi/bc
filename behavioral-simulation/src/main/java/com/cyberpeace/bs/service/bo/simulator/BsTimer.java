package com.cyberpeace.bs.service.bo.simulator;

import java.util.Timer;

/**
 * 定时任务，增加一个运行状态
 *
 * @author SN
 */
public class BsTimer extends Timer {

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 运行状态
     */
    private Boolean isRunning = true;

    /**
     * 编号
     */
    private Integer index;

    /**
     * 激活
     */
    public void activate() {
        this.isRunning = true;
    }

    /**
     * 禁用
     */
    public void deactivate() {
        this.isRunning = false;
    }

    /**
     * 获取运行状态
     *
     * @return {@code Boolean}
     */
    public Boolean ifActive() {
        return this.isRunning;
    }

    /**
     * 获取用户名称
     *
     * @return {@code String}
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
