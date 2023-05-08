package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import com.cyberpeace.cs.dao.mapper.SystemUseStatusMapper;
import com.cyberpeace.cs.service.SystemUseStatusService;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统用状态服务impl
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
@Slf4j
public class SystemUseStatusServiceImpl extends ServiceImpl<SystemUseStatusMapper, SystemUseStatus> implements SystemUseStatusService {

	@Autowired
	private SystemUseStatusMapper systemUseStatusMapper;

	/**
	 * 显示所有
	 *
	 * @return {@link SystemUseStatusRes}
	 */
	@Override
	public SystemUseStatusRes showAll() {
		SystemUseStatusRes val = new SystemUseStatusRes();
		List<SystemUseStatus> res = systemUseStatusMapper.selectList();
		List<String> xaxis = res.stream().map(SystemUseStatus::getAlterTime).map(TimeUtil::format7)
				.collect(Collectors.toList());
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
	 * @param cpuUsage    cpu使用率
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
