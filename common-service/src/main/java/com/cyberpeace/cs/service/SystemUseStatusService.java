package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;

/**
 * 系统使用状态服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface SystemUseStatusService extends IService<SystemUseStatus> {

	/**
	 * 显示所有
	 *
	 * @return {@link SystemUseStatusRes}
	 */
	SystemUseStatusRes showAll();

	/**
	 * 插入
	 *
	 * @param cpuUsage    cpu使用率
	 * @param memoryUsage 内存使用情况
	 * @param diskUsage   磁盘使用情况
	 */
	void insert(String cpuUsage, String memoryUsage, String diskUsage);

	/**
	 * 删除
	 */
	void delete();
}
