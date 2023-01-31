package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;

import java.util.Map;


public interface RbCombatLogService extends IService<RbCombatLog> {

	void handlerLog(String syslog);

	Boolean isZrSyslog(String syslog);

	Boolean isSbSyslog(String syslog);

	Map<String, String> getInitDataFromZrLog(String syslog);
}
