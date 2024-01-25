package com.cyberpeace.cs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
}
