package com.cyberpeace.cs.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.DesEncryptUtils;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.dao.mapper.SystemPropertyMapper;
import com.cyberpeace.cs.service.SystemPropertyService;

@Service
public class SystemPropertyServiceImpl extends ServiceImpl<SystemPropertyMapper, SystemProperty> implements SystemPropertyService {

	@Autowired
	private SystemPropertyMapper systemPropertyMapper;

	@Override
	public boolean getExportBuiltin() {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(SystemProperty.NameEnum.EXPORT_BUILTIN.getDesc());
		return systemProperty != null && Boolean.parseBoolean(systemProperty.getContent());
	}

	@Override
	public Integer getSystemPropertyInt(String name, Integer defaultValue) {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(name);
		return systemProperty != null ? Integer.parseInt(systemProperty.getContent()) : defaultValue;
	}

	@Override
	public SystemProperty getSystemProperty(String name) {
		return systemPropertyMapper.selectByName(name);
	}

	@Override
	public Date getExpirationDate() {
		// 查询加密过的最后一天
		SystemProperty property = systemPropertyMapper.selectByName("expirationDate");
		// 解密
		String dateStr = DesEncryptUtils.decrypt(property.getContent());
		Date lastDay = TimeUtil.toDate2(dateStr);
		// 最后一天的第二天的00:00:00
		return TimeUtil.addDates(lastDay, 1);
	}
}
