package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.SystemProperty;

public interface SystemPropertyService extends IService<SystemProperty> {

	boolean getExportBuiltin();

	Integer getSystemPropertyInt(String name, Integer defaultValue);

	SystemProperty getSystemProperty(String name);
}
