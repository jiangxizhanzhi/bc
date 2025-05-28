/**
 * 该代码实现了一个名为 `SystemUseStatusServiceImpl` 的服务类，用于处理系统使用状态相关的业务逻辑。以下是代码的主要功能和结构分析： 
 * 1. **类的功能**： 
 * - 该类继承自 `ServiceImpl<SystemUseStatusMapper, SystemUseStatus>`，并实现了 `SystemUseStatusService` 接口。 
 * - 它提供了三个主要功能：显示所有系统使用状态、插入新的系统使用状态数据以及删除过期的数据。 
 * 2. **成员变量**： 
 * - `systemUseStatusMapper`：通过 `@Autowired` 注解注入，用于与数据库交互，执行查询、插入等操作。 
 * 3. **方法分析**： 
 * - `showAll()`：获取所有系统使用状态记录，并将其转换为前端需要的格式（如 `xAxis` 时间轴、CPU、磁盘和内存数据列表），最终返回给调用方。 
 * - `insert(String cpuUsage, String memoryUsage, String diskUsage)`：接收 CPU、内存和磁盘的使用率作为参数，创建一个新的 `SystemUseStatus` 对象并保存到数据库中。 
 * - `delete()`：调用 `deleteOverdueData()` 方法从数据库中删除过期的数据。 
 * 4. **日志记录**： 
 * - 使用了 Lombok 提供的 `@Slf4j` 注解，方便在需要时进行日志记录。 
 * 5. **依赖注入**： 
 * - Spring 的 `@Autowired` 注解确保了 `SystemUseStatusMapper` 的自动装配，使得类能够与数据库进行交互。 
 * 6. **时间格式化**： 
 * - 使用了 `TimeUtil.format7` 方法对时间戳进行格式化处理，以便生成适合展示的时间轴数据。 
 * 总体而言，这段代码是一个典型的 MyBatis-Plus 扩展服务实现类，封装了对系统使用状态数据的基本 CRUD 操作，同时具备一定的数据处理能力。 
**/
package com.cyberpeace.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import com.cyberpeace.cs.dao.mapper.SystemUseStatusMapper;
import com.cyberpeace.cs.service.SystemUseStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用状态Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
@Slf4j
public class SystemUseStatusServiceImpl extends ServiceImpl<SystemUseStatusMapper, SystemUseStatus> implements SystemUseStatusService {

	/**
	 * 系统用状态Mapper
	 */
	@Autowired
	private SystemUseStatusMapper systemUseStatusMapper;

	/**
	 * 显示全部
	 *
	 * @return {@code SystemUseStatusRes }
	 */
	@Override
	public SystemUseStatusRes showAll() {
		SystemUseStatusRes val = new SystemUseStatusRes();
		List<SystemUseStatus> res = systemUseStatusMapper.selectList();
		List<String> xaxis = res.stream().map(SystemUseStatus::getAlterTime).map(TimeUtil::format7).collect(Collectors.toList());
		List<String> cpu = res.stream().map(SystemUseStatus::getCpu).collect(Collectors.toList());
		List<String> disk = res.stream().map(SystemUseStatus::getDisk).collect(Collectors.toList());
		List<String> ram = res.stream().map(SystemUseStatus::getRam).collect(Collectors.toList());
		val.setCpudata(cpu);
		val.setXaxis(xaxis);
		val.setDiskdata(disk);
		val.setRamdata(ram);
		return val;
	}

	/**
	 * 插入
	 *
	 * @param cpuUsage    CPU 使用率
	 * @param memoryUsage 内存使用情况
	 * @param diskUsage   磁盘使用情况
	 */
	@Override
	public void insert(String cpuUsage, String memoryUsage, String diskUsage) {
		SystemUseStatus systemUseStatus = new SystemUseStatus();
		systemUseStatus.setCpu(cpuUsage);
		systemUseStatus.setDisk(diskUsage);
		systemUseStatus.setRam(memoryUsage);
		systemUseStatus.setAlterTime(new Date());
		systemUseStatusMapper.insert(systemUseStatus);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete() {
		systemUseStatusMapper.deleteOverdueData();
	}


}
