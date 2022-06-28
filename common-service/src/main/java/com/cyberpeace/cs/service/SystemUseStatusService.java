package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemUseStatus;

import java.util.List;

public interface SystemUseStatusService extends IService<SystemUseStatus> {

	SystemUseStatusRes showAll();

	void insert(String cpuUsage, String memoryUsage, String diskUsage);


    void delete();
}
