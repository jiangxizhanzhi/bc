package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;

/**
 * 系统用状态Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface SystemUseStatusService extends IService<SystemUseStatus> {

	/**
	 * 显示全部
	 *
	 * @return {@code SystemUseStatusRes }
	 */
	SystemUseStatusRes showAll();

	/**
	 * 插入
	 *
	 * @param cpuUsage    CPU 使用率
	 * @param memoryUsage 内存使用情况
	 * @param diskUsage   磁盘使用情况
	 */
	void insert(String cpuUsage, String memoryUsage, String diskUsage);


	/**
	 * 删除
	 */
	void delete();
}
