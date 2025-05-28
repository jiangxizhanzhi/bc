/**
 * 这段代码定义了一个接口 `SystemUseStatusService`，它是基于 MyBatis-Plus 的通用 Service 接口 `IService` 的扩展。以下是代码的简要分析： 
 * 1. **接口继承**： 
 * - `SystemUseStatusService` 继承了 `IService<SystemUseStatus>`，这意味着它提供了 MyBatis-Plus 提供的基本 CRUD 操作功能。 
 * 2. **方法定义**： 
 * - `showAll()`：返回一个 `SystemUseStatusRes` 对象，可能是用于展示系统使用状态的完整信息。 
 * - `insert(String cpuUsage, String memoryUsage, String diskUsage)`：用于插入新的系统使用状态数据，参数包括 CPU 使用率、内存使用情况和磁盘使用情况。 
 * - `delete()`：用于删除系统使用状态数据，但具体实现未提供。 
 * 3. **注释与文档**： 
 * - 接口有详细的注释说明其用途和作者信息。 
 * - 每个方法都有明确的注释描述其功能和参数。 
 * 总体而言，这个接口设计清晰，涵盖了基本的 CRUD 操作以及一些特定业务逻辑的方法。 
**/
package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;

/**
 * 系统用状态Service
 *
 * @author SN
 * @date 2024/11/08
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
