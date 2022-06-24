package com.cyberpeace.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;
import com.cyberpeace.cs.dao.mapper.SystemUseStatusMapper;
import com.cyberpeace.cs.service.SystemUseStatusService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SystemUseStatusServiceImpl extends ServiceImpl<SystemUseStatusMapper, SystemUseStatus> implements SystemUseStatusService {

	@Autowired
	private SystemUseStatusMapper systemUseStatusMapper;

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

	@Override
	public void insert(String cpuUsage, String memoryUsage, String diskUsage) {
		SystemUseStatus systemUseStatus = new SystemUseStatus();
		systemUseStatus.setCpu(cpuUsage);
		systemUseStatus.setDisk(diskUsage);
		systemUseStatus.setRam(memoryUsage);
		systemUseStatus.setAlterTime(new Date());
		systemUseStatusMapper.insert(systemUseStatus);
	}

	@Override
	public void delete() {
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("alter_time"," <= date_sub(now(), interval 36 hour)");
		systemUseStatusMapper.deleteByMap(queryParams);
	}


}
