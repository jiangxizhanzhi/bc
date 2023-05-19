package com.cyberpeace.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.dao.mapper.SystemPropertyMapper;
import com.cyberpeace.cs.service.SystemPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemPropertyServiceImpl extends ServiceImpl<SystemPropertyMapper, SystemProperty> implements SystemPropertyService {

	@Autowired
	private SystemPropertyMapper systemPropertyMapper;

	@Override
	public boolean getExportBuiltin() {
		SystemProperty systemProperty = systemPropertyMapper.selectByName(SystemProperty.NameEnum.EXPORT_BUILTIN.getDesc());
		return systemProperty != null && Boolean.parseBoolean(systemProperty.getContent());
	}
}
